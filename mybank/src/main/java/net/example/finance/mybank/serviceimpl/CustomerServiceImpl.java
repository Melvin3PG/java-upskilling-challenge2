package net.example.finance.mybank.serviceimpl;

import java.util.ArrayList;
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

        List<Account> accounts = accountRepository.findByCustomerId(customerNumber);
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

        List<Account> accounts = accountRepository.findByCustomerId(customerNumber);
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
            List<Account> accounts = accountRepository.findByCustomerId(customer.getCustomerNumber());
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
        Customer customerAux = customerRepository.save(updateFields(customerDB,customer));

        List<Account> accounts = accountRepository.findByCustomerId(customerNumber);

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
        List<Account> accounts = accountRepository.findByCustomerId(customer.getCustomerNumber());
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
        Customer customerAux = customerRepository.save(updateFields(customerDB,customer));

        List<Account> accounts = accountRepository.findByCustomerId(customerNumber);

        customerObject = modelMapper.map(customerAux, CustomerObject.class);

        if(accounts != null)
            {
                List<AccountObject> accountsObject = Arrays.asList(modelMapper.map(accounts, AccountObject[].class));
                customerObject.setAccounts(accountsObject);
            }
  
		return customerObject;  
    }

    private Customer updateFields(Customer customerDB, Customer customer)
    {
        if (Objects.nonNull(customer.getCustomerNumber())
            && customerDB.getCustomerNumber() != 
            customer.getCustomerNumber()
            && customer.getCustomerNumber() != 0) {
                customerDB.setCustomerNumber(
                customer.getCustomerNumber());
        }
  
        if (Objects.nonNull(
            customer.getCustomerType())
            && !"".equals(
                customer.getCustomerType())) {
                    customerDB.setCustomerType(
                customer.getCustomerType());
        }
  
        if (Objects.nonNull(
            customer.getActive()) 
            && !(customerDB.getActive().equals(customer.getActive())) 
            ) 
        {
            customerDB.setActive(
                customer.getActive());
        }

        if (Objects.nonNull(
            customer.getAtDate()) 
            && !(customerDB.getAtDate().equals(customer.getAtDate()))
            ) 
        {
            customerDB.setAtDate(
                customer.getAtDate());
        }

        return customerDB;
    }
    
}
