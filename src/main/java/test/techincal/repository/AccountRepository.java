package test.techincal.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import test.techincal.model.Account;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public Account findByAccountId(Integer accountId){
        return find("account_id", accountId).firstResult();
    }
}
