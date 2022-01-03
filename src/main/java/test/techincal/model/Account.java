package test.techincal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.common.constraint.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
@RegisterForReflection
public class Account {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
        name = "sequence-generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accounts_account_id_seq"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
        }
    )
    private Integer account_id;

    private String name;

    @NotNull
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_payment_date;

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLast_payment_date() {
        return last_payment_date;
    }

    public void setLast_payment_date(Date last_payment_date) {
        this.last_payment_date = last_payment_date;
    }
}
