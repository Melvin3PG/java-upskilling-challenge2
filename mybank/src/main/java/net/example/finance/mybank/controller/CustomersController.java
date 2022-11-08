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
        try
        {
            response.setData(customerService.saveCustomer(customerObject));
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> deleteCustomer(Long customerId) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        try
        {          
            response.setData(customerService.deleteCustomerById(customerId)); 
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers(String xChannelId, String xCountryCode,
            String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
        CustomerListResponse response = new CustomerListResponse();	
        try
        {
            response.setData(customerService.fetchCustomerList());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerListNofication(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> getCustomerByCustomerNumber(Long customerId, String xChannelId,
            String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
            String xApiVersion) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        try
        {  	
            response.setData(customerService.fetchCustomerById(customerId));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerNotification(ex, response),HttpStatus.BAD_REQUEST);
        }   
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> partialUpdateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        try
        {  
            response.setData(customerService.partialUpdateCustomer(customerObject, customerId));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerNotification(ex, response),HttpStatus.BAD_REQUEST);
        }  
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> updateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        try 
        {
            response.setData(customerService.updateCustomer(customerObject, customerId));
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchCustomerNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<AccountDetailResponse> deleteAccountOfCustomer(Long customerId, Long accountId) {
        AccountDetailResponse response = new AccountDetailResponse();
        try
        {            
            response.setData(customerService.customerDeleteAccountById(customerId,accountId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<AccountDetailResponse> getAccountByCustomerAndAccountNumber(Long customerId, Long accountId,
            String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid,
            String xUserContext, String xApiVersion) {
        AccountDetailResponse response = new AccountDetailResponse();
        try
        {         
            response.setData(customerService.customerFetchAccountById(customerId, accountId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex)
            {        
                return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
            } 
    }

    @Override
    public ResponseEntity<AccountListResponse> getAllAccountsOfCustomer(Long customerId, String xChannelId,
            String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
            String xApiVersion) {
        AccountListResponse response = new AccountListResponse();
        try
        {       
            response.setData(customerService.customerFetchAccountList(customerId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountListNofication(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<AccountDetailResponse> partialUpdateAccountOfCustomer(Long customerId, Long accountId,
            @Valid AccountObject accountObject) {
        AccountDetailResponse response = new AccountDetailResponse();
        try
        {
            response.setData(customerService.customerUpdateAccount(customerId, accountId, accountObject));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }

    @Override
    public ResponseEntity<AccountDetailResponse> updateAccountOfCustomer(Long customerId, Long accountId,
            @Valid AccountObject accountObject) {
        AccountDetailResponse response = new AccountDetailResponse();
        try
        {
            response.setData(customerService.customerUpdateAccount(customerId, accountId, accountObject));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception ex)
        {        
            return new ResponseEntity<>(ControllerUtil.CatchAccountNotification(ex, response),HttpStatus.BAD_REQUEST);
        } 
    }
}
