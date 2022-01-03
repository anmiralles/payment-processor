package test.techincal.service;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.jboss.logging.Logger;
import test.techincal.builder.PaymentBuilder;
import test.techincal.dto.PaymentDTO;
import test.techincal.model.ErrorType;
import test.techincal.model.Payment;
import test.techincal.repository.PaymentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOGGER = Logger.getLogger(PaymentService.class);

    private static final String PAYMENTS_ONLINE_TOPIC = "online";
    private static final String PAYMENTS_OFFLINE_TOPIC = "offline";

    @Inject
    PaymentBuilder paymentBuilder;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    PaymentValidatorService paymentValidatorService;

    @Inject
    LoggerService loggerService;

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        ObjectMapperSerde<PaymentDTO> paymentSerde = new ObjectMapperSerde<>(PaymentDTO.class);

        KStream<String, PaymentDTO> paymentsOnline = builder.stream(PAYMENTS_ONLINE_TOPIC,
                        Consumed.with(Serdes.String(), paymentSerde))
                .peek(this::validatePayment);

        builder.stream(PAYMENTS_OFFLINE_TOPIC, Consumed.with(Serdes.String(), paymentSerde))
                .merge(paymentsOnline)
                .foreach(this::processPayment);

        return builder.build();
    }

    private void validatePayment(String k, PaymentDTO paymentDTO) {

        var response = paymentValidatorService.validatePayment(paymentDTO);

        if (response != null && Response.Status.OK.getStatusCode() == response.statusCode()) {
            LOGGER.infof("Payment online validated: %s", paymentDTO);
        } else {
            loggerService.logPaymentError(paymentDTO,
                    ErrorType.network,
                    "Payment not validated: " + paymentDTO);
        }
    }

    @Transactional
    public void processPayment(String k, PaymentDTO paymentDTO) {
        Payment paymentExist;
        Payment payment = paymentBuilder.paymentDTOtoPayment(paymentDTO);

        if (payment != null) {
            payment.getAccount_id().setLast_payment_date(Timestamp.from(Instant.now()));
            paymentExist = paymentRepository.findByPaymentId(payment.getPayment_id());

            if (paymentExist == null) {
                paymentRepository.persist(payment);
                LOGGER.infof("Payment processed: %s", payment);
            } else {
                loggerService.logPaymentError(paymentDTO,
                        ErrorType.database,
                        "Payment already exists: " + paymentDTO);
            }
        }
    }
}