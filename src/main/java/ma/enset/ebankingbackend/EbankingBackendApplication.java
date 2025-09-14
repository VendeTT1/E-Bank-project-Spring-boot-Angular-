package ma.enset.ebankingbackend;

import ma.enset.ebankingbackend.dtos.BankAccountDTO;
import ma.enset.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.enset.ebankingbackend.entities.*;
import ma.enset.ebankingbackend.enums.AccountStatus;
import ma.enset.ebankingbackend.enums.OperationType;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.repositories.AccountOperationRepository;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.ebankingbackend.repositories.CustomerRepository;
import ma.enset.ebankingbackend.services.BankAccountService;
import ma.enset.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane","Mohammed").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.getAllCustomers().forEach(customerDTO
                    ->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000, 9000, customerDTO.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*12000, 5.5, customerDTO.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i = 0; i < 10 ; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }
                    else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*12000, "credit");
                    bankAccountService.debit(accountId, 1000+Math.random()*9000, "debit");
                }

            }
        };
    };


   // @Bean
//    CommandLineRunner start(CustomerRepository customerRepository
//                            , BankAccountRepository bankAccountRepository
//                            , AccountOperationRepository accountOperationRepository) {
//        return args -> {
//            Stream.of("Hassan", "Yassine", "Aicha").forEach(
//                    name -> {
//                        Customer customer = new Customer();
//                        customer.setName(name);
//                        customer.setEmail(name + "@email.com");
//                        customerRepository.save(customer);
//                    }
//            );
//            customerRepository.findAll().forEach(
//                    customer -> {
//                        CurrentAccount currentAccount = new CurrentAccount();
//                        currentAccount.setId(UUID.randomUUID().toString());
//                        currentAccount.setCustomer(customer);
//                        currentAccount.setBalance(Math.random()*90000.0);
//                        currentAccount.setCreateDate(new Date());
//                        currentAccount.setStatus(AccountStatus.CREATED);
//                        currentAccount.setOverDraft(8000.0);
//                        bankAccountRepository.save(currentAccount);
//
//                        SavingAccount savingAccount = new SavingAccount();
//                        savingAccount.setId(UUID.randomUUID().toString());
//                        savingAccount.setCustomer(customer);
//                        savingAccount.setBalance(Math.random()*90000.0);
//                        savingAccount.setCreateDate(new Date());
//                        savingAccount.setStatus(AccountStatus.CREATED);
//                        savingAccount.setInterestRate(5.5);
//                        bankAccountRepository.save(savingAccount);
//                    }
//            );
//            bankAccountRepository.findAll().forEach(
//                    acc -> {
//                        for (int i = 0; i < 5; i++) {
//                            AccountOperation accountOperation = new AccountOperation();
//                            accountOperation.setOperationDate(new Date());
//                            accountOperation.setAmount(Math.random()*13000.0);
//                            accountOperation.setType(Math.random() > 0.5? OperationType.DEBIT: OperationType.CREDIT);
//                            accountOperation.setBankAccount(acc);
//                            accountOperationRepository.save(accountOperation);
//                        }
//                    }
//            );
//
//
//        };
//    }

}
