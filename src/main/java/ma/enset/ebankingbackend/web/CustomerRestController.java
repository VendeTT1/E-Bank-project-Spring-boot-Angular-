package ma.enset.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.entities.Customer;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
//@RequestMapping("/cutsomers")
public class CustomerRestController {

    private BankAccountService bankAccountService;


    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return bankAccountService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/addCustomer")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/updateCustomer/{customerId}")
    public CustomerDTO updateCustomerDTO(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("deleteCustomer/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }

}
