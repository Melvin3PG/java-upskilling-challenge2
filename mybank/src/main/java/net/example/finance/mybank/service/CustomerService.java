package net.example.finance.mybank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.CustomerObject;

@Service
public interface CustomerService {
    CustomerObject saveCustomer(CustomerObject customerNumber);
    List<CustomerObject> fetchCustomerList();
    CustomerObject updateCustomer(CustomerObject customerObject, Long customerNumber);
    CustomerObject deleteCustomerById(Long customerNumber);
    CustomerObject fetchCustomerById(Long customerNumber);
    CustomerObject partialUpdateCustomer(CustomerObject cusotemerObject, Long customerNumber);
    List<AccountObject> customerFetchAccountList(Long customerNumber);
    AccountObject customerUpdateAccount(Long customerNumber, Long accNum, AccountObject accountObject);
    AccountObject customerDeleteAccountById(Long customerNumber, Long accNum);
    AccountObject customerFetchAccountById(Long customerNumber, Long accNum);
    AccountObject customerPartialUpdateAccount(Long customerNumber, Long accNum, AccountObject accountObject);
}
