package net.example.finance.mybank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.CustomerObject;

@Service
public interface CustomerService {
    CustomerObject saveCustomer(CustomerObject customerNumber);
    List<CustomerObject> fetchCustomerList();
    CustomerObject updateCustomer(CustomerObject customerObject, Long customerNumber);
    CustomerObject deleteCustomerById(Long customerNumber);
    CustomerObject fetchCustomerById(Long customerNumber);
    CustomerObject partialUpdateCustomer(CustomerObject cusotemerObject, Long customerNumber);
}
