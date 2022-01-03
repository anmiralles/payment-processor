package test.techincal.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import test.techincal.model.PaymentType;

@RegisterForReflection
public class PaymentDTO {
    public String payment_id;
    public Integer account_id;
    public PaymentType payment_type;
    public String credit_card;
    public Integer amount;

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "payment_id='" + payment_id + '\'' +
                ", account_id=" + account_id +
                ", payment_type=" + payment_type +
                ", credit_card='" + credit_card + '\'' +
                ", amount=" + amount +
                '}';
    }
}
