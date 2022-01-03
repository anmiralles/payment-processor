package test.techincal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.techincal.dto.PaymentDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class PaymentValidatorService {

    @Inject
    ObjectMapper objectMapper;

    public HttpResponse validatePayment(PaymentDTO paymentDTO) {

        HttpResponse response = null;

        try {
            String paymentToJson = objectMapper.writeValueAsString(paymentDTO);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9000/payment"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(paymentToJson))
                    .build();

            var client = HttpClient.newHttpClient();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }
}

