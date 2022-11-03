package net.example.finance.mybank.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.CustomerObject;

import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.repository.AccountRepository;
import net.example.finance.mybank.model.repository.CustomerRepository;
import net.example.finance.mybank.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerObject deleteCustomerById(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber).get();
        
        if (Objects.nonNull(customer.getCustomerNumber())
            && customer.getCustomerNumber().equals(customerNumber)) 
        {
            customerRepository.deleteById(customerNumber);
        }  
        
        return modelMapper.map(customer, CustomerObject.class);
    }

    @Override
    public CustomerObject fetchCustomerById(Long customerNumber) {
        Customer customer = customerRepository.findById(customerNumber).get();
        return modelMapper.map(customer, CustomerObject.class);
    }

    @Override
    public List<CustomerObject> fetchCustomerList() {
        List<Customer> list = customerRepository.findAll();
        return Arrays.asList(modelMapper.map(list, CustomerObject[].class));
    }

    @Override
    public CustomerObject partialUpdateCustomer(CustomerObject customerObject, Long customerNumber) {
        Customer customerDB
            = customerRepository.findById(customerNumber)
                  .get();
        Customer customer = modelMapper.map(customerObject, Customer.class);
  
        customer = customerRepository.save(updateFields(customerDB,customer));
		return modelMapper.map(customer, CustomerObject.class);
    }

    @Override
    public CustomerObject saveCustomer(CustomerObject customerObject) {
        Customer customer = customerRepository.save(modelMapper.map(customerObject, Customer.class));
		return modelMapper.map(customer, CustomerObject.class);
    }

    @Override
    public CustomerObject updateCustomer(CustomerObject customerObject, Long customerNumber) {
        Customer customerDB
            = customerRepository.findById(customerNumber)
                  .get();
        Customer customer = modelMapper.map(customerObject, Customer.class);
  
        customer = customerRepository.save(updateFields(customerDB,customer));
		return modelMapper.map(customer, CustomerObject.class);
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
  
        if (!(customerDB.getActive().equals(customer.getActive()))) 
        {
            customerDB.setActive(
                customer.getActive());
        }

        if (!(customerDB.getAtDate().equals(customer.getAtDate()))) 
        {
            customerDB.setAtDate(
                customer.getAtDate());
        }

        return customerDB;
    }
    
}
