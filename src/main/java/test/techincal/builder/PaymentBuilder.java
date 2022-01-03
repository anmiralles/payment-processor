package test.techincal.builder;

import test.techincal.dto.PaymentDTO;
import test.techincal.model.Account;
import test.techincal.model.ErrorType;
import test.techincal.model.Payment;
import test.techincal.repository.AccountRepository;
import test.techincal.service.LoggerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named("PaymentBuilder")
public class PaymentBuilder {

    @Inject
    AccountRepository accountRepository;

    @Inject
    LoggerService loggerService;

    public Payment paymentDTOtoPayment(PaymentDTO paymentDTO) {

        Payment payment = null;
        Account account = accountRepository.findByAccountId(paymentDTO.account_id);

        if(account != null) {
            payment = new Payment();
            payment.setPayment_id(paymentDTO.payment_id);
            payment.setAccount_id(account);
            payment.setPayment_type(paymentDTO.payment_type.toString());
            payment.setAmount(paymentDTO.amount);
            payment.setCredit_card(paymentDTO.credit_card);
        } else {
            loggerService.logPaymentError(paymentDTO,
                    ErrorType.database,
                    "Account not found: " + paymentDTO.account_id);
        }

        return payment;
    }
}
