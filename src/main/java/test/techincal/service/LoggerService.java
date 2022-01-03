package test.techincal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import test.techincal.dto.PaymentDTO;
import test.techincal.model.Error;
import test.techincal.model.ErrorType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class LoggerService {

    private static final Logger LOGGER = Logger.getLogger(LoggerService.class);

    @Inject
    ObjectMapper objectMapper;

    public HttpResponse logPaymentError(PaymentDTO paymentDTO, ErrorType errorType, String errorMessage) {

        HttpResponse response = null;

        try {
            Error error = new Error(paymentDTO.payment_id,
                    errorType.toString(),
                    errorMessage);

            LOGGER.errorf("Logging payment error: %s", error);

            String errorToJson = objectMapper.writeValueAsString(error);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9000/log"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(errorToJson))
                    .build();

            var client = HttpClient.newHttpClient();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}
