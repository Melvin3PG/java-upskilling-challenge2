package net.example.finance.mybank.serviceimpl;

import java.util.Objects;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;

public class ServiceUtil {
    private ServiceUtil() {
    }

    public static Customer updateCustomerFields(Customer customerDB, Customer customer)
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

    public static Account updateAccountFields(Account accDB, Account account)
    {
        if (Objects.nonNull(account.getAccountNumber())
            && accDB.getAccountNumber() != 
            account.getAccountNumber()
            && account.getAccountNumber() != 0) {
            accDB.setAccountNumber(
                account.getAccountNumber());
        }
  
        if (Objects.nonNull(
                account.getAccountType())
            && !"".equals(
                account.getAccountType())) {
            accDB.setAccountType(
                account.getAccountType());
        }
  
        if (Objects.nonNull(account.getBalance())
            && accDB.getBalance() != 
                account.getBalance()
                && account.getBalance() != 0) {
            accDB.setBalance(
                account.getBalance());
        }

        if (Objects.nonNull(account.getOverdraftAmount())
            && accDB.getOverdraftAmount() !=
                account.getOverdraftAmount()
                && account.getOverdraftAmount() != 0) {
            accDB.setOverdraftAmount(
                account.getOverdraftAmount());
        }

        if (Objects.nonNull(
            account.getOverdraftAllowed()) 
            && !(accDB.getOverdraftAllowed().equals(account.getOverdraftAllowed())) 
            ) 
        {
            accDB.setOverdraftAllowed(
                account.getOverdraftAllowed());
        }

        if (Objects.nonNull(account.getCustomerId())
        && accDB.getCustomerId() != 
        account.getCustomerId()
        && account.getCustomerId() != 0) {
        accDB.setCustomerId(
            account.getCustomerId());   
        }

        if (Objects.nonNull(account.getUserId())
        && accDB.getUserId() != 
        account.getUserId()
        && account.getUserId() != 0) {
            accDB.setUserId(
                account.getUserId());
        }

        return accDB;
    }
}
