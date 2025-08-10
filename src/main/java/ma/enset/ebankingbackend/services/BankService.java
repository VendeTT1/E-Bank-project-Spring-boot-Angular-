package ma.enset.ebankingbackend.services;

import ma.enset.ebankingbackend.entities.BankAccount;
import ma.enset.ebankingbackend.entities.CurrentAccount;
import ma.enset.ebankingbackend.entities.SavingAccount;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount =
                bankAccountRepository.findById("40dcfd02-c1df-452a-9e96-60df406f09b5").orElse(null);
        System.out.println("*************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getCreateDate());
        System.out.println(bankAccount.getStatus());
        System.out.println(bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        if (bankAccount instanceof CurrentAccount) {
            System.out.println("OverDraft -> "+((CurrentAccount)bankAccount).getOverDraft());
        }
        else if (bankAccount instanceof SavingAccount) {
            System.out.println("Rate ->"+((SavingAccount)bankAccount).getInterestRate());
        }
        bankAccount.getAccountOperations().forEach(
                op->{
                    System.out.println("===================================");
                    System.out.println(op.getType());
                    System.out.println(op.getOperationDate());
                    System.out.println(op.getAmount());
                }
        );
    }
}
