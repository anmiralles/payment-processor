package test.techincal.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Error {

    public String payment_id;
    public String error_type;
    public String error_description;

    public Error() {
    }

    public Error(String payment_id, String error_type, String error_description) {
        this.payment_id = payment_id;
        this.error_type = error_type;
        this.error_description = error_description;
    }

    @Override
    public String toString() {
        return "Error{" +
                "payment_id='" + payment_id + '\'' +
                ", error=" + error_type +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
