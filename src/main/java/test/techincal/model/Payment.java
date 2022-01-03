package test.techincal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payments")
@RegisterForReflection
public class Payment {

    @Id
    private String payment_id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account_id;

    @NotNull
    private String payment_type;

    private String credit_card;

    @NotNull
    @Column(columnDefinition = "NUMERIC")
    private Integer amount;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Account getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Account account_id) {
        this.account_id = account_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payment_id='" + payment_id + '\'' +
                ", account_id=" + account_id +
                ", payment_type=" + payment_type +
                ", credit_card='" + credit_card + '\'' +
                ", amount=" + amount +
                '}';
    }
}

