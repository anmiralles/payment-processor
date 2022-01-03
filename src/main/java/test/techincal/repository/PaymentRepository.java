package test.techincal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import test.techincal.model.Payment;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
    public Payment findByPaymentId(String payment_id) {
        return find("payment_id", payment_id).firstResult();
    }
}
