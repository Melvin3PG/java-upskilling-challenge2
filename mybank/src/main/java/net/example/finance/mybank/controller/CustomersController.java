package net.example.finance.mybank.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.CustomersApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.CustomerDetailResponse;
import com.example.mvnprg.openapi.model.CustomerListResponse;
import com.example.mvnprg.openapi.model.CustomerObject;

@RestController
public class CustomersController implements CustomersApi{

    @Override
    public ResponseEntity<CustomerDetailResponse> createCustomer(String xChannelId, String xCountryCode,
            String xApplCode, @Valid CustomerObject customerObject, String xB3Spanid, String xB3Traceid,
            String xUserContext, String xApiVersion) {
        // TODO Auto-generated method stub
        return CustomersApi.super.createCustomer(xChannelId, xCountryCode, xApplCode, customerObject, xB3Spanid, xB3Traceid,
                xUserContext, xApiVersion);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> deleteCustomer(Long customerId) {
        // TODO Auto-generated method stub
        return CustomersApi.super.deleteCustomer(customerId);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers(String xChannelId, String xCountryCode,
            String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
        // TODO Auto-generated method stub
        return CustomersApi.super.getAllCustomers(xChannelId, xCountryCode, xApplCode, xB3Spanid, xB3Traceid, xUserContext,
                xApiVersion);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> getCustomerByCustomerNumber(Long customerId, String xChannelId,
            String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
            String xApiVersion) {
        // TODO Auto-generated method stub
        return CustomersApi.super.getCustomerByCustomerNumber(customerId, xChannelId, xCountryCode, xApplCode, xB3Spanid,
                xB3Traceid, xUserContext, xApiVersion);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> partialUpdateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        // TODO Auto-generated method stub
        return CustomersApi.super.partialUpdateCustomer(customerId, customerObject);
    }

    @Override
    public ResponseEntity<CustomerDetailResponse> updateCustomer(Long customerId,
            @Valid CustomerObject customerObject) {
        // TODO Auto-generated method stub
        return CustomersApi.super.updateCustomer(customerId, customerObject);
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
