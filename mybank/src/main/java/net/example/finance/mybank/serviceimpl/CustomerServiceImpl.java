package net.example.finance.mybank.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.CustomerObject;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.repository.AccountRepository;
import net.example.finance.mybank.model.repository.CustomerRepository;
import net.example.finance.mybank.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerObject deleteCustomerById(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber).get();
        
        if(customer == null)
            return null;

        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);
        CustomerObject customerObject = modelMapper.map(customer, CustomerObject.class);
                
        if (customer.getCustomerNumber().equals(customerNumber)) 
        {
            customerRepository.deleteById(customerNumber);

            if(accounts != null)
            {
                accountRepository.deleteAllInBatch(accounts);
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }
        }  

        return customerObject;
    }

    @Override
    public CustomerObject fetchCustomerById(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber).get();

        if(customer == null)
            return null;

        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);
        CustomerObject customerObject = modelMapper.map(customer, CustomerObject.class);

        if(accounts != null)
            {
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }

        return customerObject;
    }

    @Override
    public List<CustomerObject> fetchCustomerList() {
        List<Customer> list = customerRepository.findAll();
        
        List<CustomerObject> ListCustomerObject = Arrays.asList(modelMapper.map(list, CustomerObject[].class));

        for (CustomerObject customer : ListCustomerObject) 
        {
            List<Account> accounts = accountRepository.findByCustomerNumber(customer.getCustomerNumber());
            List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
            customer.setAccounts(accountsObject);
        }

        return ListCustomerObject;
    }

    @Override
    public CustomerObject partialUpdateCustomer(CustomerObject customerObject, Long customerNumber) {
        Customer customerDB = customerRepository.findById(customerNumber).get();
        
        if(customerDB == null)
            return null;

        Customer customer = modelMapper.map(customerObject, Customer.class);
        Customer customerAux = customerRepository.save(ServiceUtil.updateCustomerFields(customerDB,customer));

        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        customerObject = modelMapper.map(customerAux, CustomerObject.class);

        if(accounts != null)
            {
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }
  
		return customerObject;   
    }

    @Override
    public CustomerObject saveCustomer(CustomerObject customerObject) {
        Customer customer = customerRepository.save(modelMapper.map(customerObject, Customer.class));
        List<Account> accounts = accountRepository.findByCustomerNumber(customer.getCustomerNumber());
        customerObject = modelMapper.map(customer, CustomerObject.class);

        if(accounts != null)
            {
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }

		return customerObject;
    }

    @Override
    public CustomerObject updateCustomer(CustomerObject customerObject, Long customerNumber) {
        Customer customerDB = customerRepository.findById(customerNumber).get();
        
        if(customerDB == null)
            return null;

        Customer customer = modelMapper.map(customerObject, Customer.class);
        Customer customerAux = customerRepository.save(ServiceUtil.updateCustomerFields(customerDB,customer));

        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        customerObject = modelMapper.map(customerAux, CustomerObject.class);

        if(accounts != null)
            {
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }
  
		return customerObject;  
    }

    @Override
    public List<AccountObject> customerFetchAccountList(Long customerNumber) {
        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);
        return Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
    }

    @Override
    public AccountObject customerUpdateAccount(Long customerNumber, Long accNum, AccountObject accountObject) {
        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        Account accDB = new Account();

        for (Account accountAux : accounts) 
        {
            if(accountAux.getAccountNumber().equals(accNum))
                accDB = accountAux;
        }

        if(accDB.getAccountNumber() == null)
            return null;

        Account account = modelMapper.map(accountObject, Account.class);
        account.setCustomerNumber(customerNumber);
        accountRepository.save(ServiceUtil.updateAccountFields(accDB,account));
        
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject customerDeleteAccountById(Long customerNumber, Long accNum) {
        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        Account account = new Account();

        for (Account accountAux : accounts) {
            if(accountAux.getAccountNumber().equals(accNum))
                account = accountAux;
        }

        if(account.getAccountNumber() == null)
            return null;
        
        if (Objects.nonNull(account.getAccountNumber())
            && account.getAccountNumber().equals(account.getAccountNumber())) 
        {
            accountRepository.deleteById(account.getAccountNumber());
        }  
        
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject customerFetchAccountById(Long customerNumber, Long accNum) {
        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        Account account = new Account();

        for (Account accountAux : accounts) {
            if(accountAux.getAccountNumber().equals(accNum))
                account = accountAux;
        }

        if(account.getAccountNumber() == null)
            return null;
        
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject customerPartialUpdateAccount(Long customerNumber, Long accNum, AccountObject accountObject) {
        List<Account> accounts = accountRepository.findByCustomerNumber(customerNumber);

        Account accDB = new Account();

        for (Account accountAux : accounts) 
        {
            if(accountAux.getAccountNumber().equals(accNum))
                accDB = accountAux;
        }

        if(accDB.getAccountNumber() == null)
            return null;

        Account account = modelMapper.map(accountObject, Account.class);
        account.setCustomerNumber(customerNumber);
        accountRepository.save(ServiceUtil.updateAccountFields(accDB,account));
        
        return modelMapper.map(account, AccountObject.class);
    }

    @Override
    public AccountObject customerCreateAccount(Long customerNumber, AccountObject accountObject) {
        Customer customerDB = customerRepository.findById(customerNumber).get();
        Account account = modelMapper.map(accountObject, Account.class);
        account.setCustomerNumber(customerDB.getCustomerNumber());
        accountRepository.save(account);
        
        return modelMapper.map(account, AccountObject.class);
    }  
}
