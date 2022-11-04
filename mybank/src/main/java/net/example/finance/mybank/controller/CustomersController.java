package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.CustomersApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.CustomerDetailResponse;
import com.example.mvnprg.openapi.model.CustomerListResponse;
import com.example.mvnprg.openapi.model.CustomerObject;

import net.example.finance.mybank.service.CustomerService;

@RestController
public class CustomersController implements CustomersApi{
    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<CustomerDetailResponse> createCustomer(String xChannelId, String xCountryCode,
            String xApplCode, @Valid CustomerObject customerObject, String xB3Spanid, String xB3Traceid,
            String xUserContext, String xApiVersion) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        response.setData(customerService.saveCustomer(customerObject));
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> deleteCustomer(Long customerId) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        CustomerObject customer = customerService.deleteCustomerById(customerId);
        
        if(customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        response.setData(customer);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers(String xChannelId, String xCountryCode,
            String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
        CustomerListResponse response = new CustomerListResponse();	
        response.setData(customerService.fetchCustomerList());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> getCustomerByCustomerNumber(Long customerId, String xChannelId,
            String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
            String xApiVersion) {
        CustomerDetailResponse response = new CustomerDetailResponse();	
        CustomerObject customer = customerService.fetchCustomerById(customerId);
        
        if(customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        response.setData(customer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> partialUpdateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        CustomerObject customer = customerService.partialUpdateCustomer(customerObject, customerId);

        if(customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        response.setData(customer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> updateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        CustomerObject customer = customerService.updateCustomer(customerObject, customerId);

        if(customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        response.setData(customer);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDetailResponse> deleteAccountOfCustomer(Long customerId, Long accountId) {
        // TODO Auto-generated method stub
        return CustomersApi.super.deleteAccountOfCustomer(customerId, accountId);
    }

    @Override
    public ResponseEntity<AccountDetailResponse> getAccountByCustomerAndAccountNumber(Long customerId, Long accountId,
            String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid,
            String xUserContext, String xApiVersion) {
        // TODO Auto-generated method stub
        return CustomersApi.super.getAccountByCustomerAndAccountNumber(customerId, accountId, xChannelId, xCountryCode,
                xApplCode, xB3Spanid, xB3Traceid, xUserContext, xApiVersion);
    }

    @Override
    public ResponseEntity<AccountListResponse> getAllAccountsOfCustomer(Long customerId, String xChannelId,
            String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
            String xApiVersion) {
        // TODO Auto-generated method stub
        return CustomersApi.super.getAllAccountsOfCustomer(customerId, xChannelId, xCountryCode, xApplCode, xB3Spanid,
                xB3Traceid, xUserContext, xApiVersion);
    }

    @Override
    public ResponseEntity<AccountDetailResponse> partialUpdateAccountOfCustomer(Long customerId, Long accountId,
            @Valid AccountObject accountObject) {
        // TODO Auto-generated method stub
        return CustomersApi.super.partialUpdateAccountOfCustomer(customerId, accountId, accountObject);
    }

    @Override
    public ResponseEntity<AccountDetailResponse> updateAccountOfCustomer(Long customerId, Long accountId,
            @Valid AccountObject accountObject) {
        // TODO Auto-generated method stub
        return CustomersApi.super.updateAccountOfCustomer(customerId, accountId, accountObject);
    }
}
