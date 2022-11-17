package net.example.finance.mybank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import net.example.finance.mybank.openapi.api.CustomersApi;
import net.example.finance.mybank.openapi.model.AccountDetailResponseDto;
import net.example.finance.mybank.openapi.model.AccountListResponseDto;
import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.openapi.model.CustomerDetailResponseDto;
import net.example.finance.mybank.openapi.model.CustomerListResponseDto;
import net.example.finance.mybank.openapi.model.CustomerObjectDto;
import net.example.finance.mybank.service.CustomerService;

/**
 * REST API to manage the customers.
 * 
 * @author jesus.quintero
 *
 */
@RestController
@Log4j2
public class CustomerController implements CustomersApi {

	CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	
	@Override
	public ResponseEntity<CustomerDetailResponseDto> createCustomer(String xChannelId, String xCountryCode,
			String xApplCode, @Valid CustomerObjectDto customerObjectDto, String xB3Spanid, String xB3Traceid,
			String xUserContext, String xApiVersion) {

		CustomerDetailResponseDto response = new CustomerDetailResponseDto();
		CustomerObjectDto newCustomer = service.createCustomer(customerObjectDto);

		if (null != newCustomer) {
			response.setData(newCustomer);
		}

		return ResponseEntity.ok(response);
	}

	
	@Override
	public ResponseEntity<AccountDetailResponseDto> deleteAccountOfCustomer(Long customerId, Long accountId) {
		AccountDetailResponseDto response = new AccountDetailResponseDto();

		service.deleteAccountOfCustomer(customerId, accountId);

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CustomerDetailResponseDto> deleteCustomer(Long customerId) {
		CustomerDetailResponseDto response = new CustomerDetailResponseDto();
		try {

			service.deleteCustomer(customerId);

		} catch (Exception ex) {
			throw ex;
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountDetailResponseDto> getAccountByCustomerAndAccountNumber(Long customerId,
			Long accountId, String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid,
			String xB3Traceid, String xUserContext, String xApiVersion) {
	
		AccountDetailResponseDto response = new AccountDetailResponseDto();

		AccountObjectDto accountDto = service.getAccountByCustomerAndAccountNumber(customerId, accountId);

		if (null != accountDto) {
			response.setData(accountDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountListResponseDto> getAllAccountsOfCustomer(Long customerId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		AccountListResponseDto response = new AccountListResponseDto();

		List<AccountObjectDto> listOfAccountDto = service.getAllAccountsOfCustomer(customerId);

		if (null != listOfAccountDto) {
			response.setData(listOfAccountDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CustomerListResponseDto> getAllCustomers(String xChannelId, String xCountryCode,
			String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		log.debug("Start retrieving customers.");

		CustomerListResponseDto response = new CustomerListResponseDto();
		List<CustomerObjectDto> customers = service.getAllCustomer();

		if (null != customers) {
			response.setData(customers);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CustomerDetailResponseDto> getCustomerByCustomerNumber(Long customerId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {

		CustomerDetailResponseDto response = new CustomerDetailResponseDto();

		CustomerObjectDto customerDto = service.getCustomerByCustomerNumber(customerId);

		if (null != customerDto) {
			response.setData(customerDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountDetailResponseDto> partialUpdateAccountOfCustomer(Long customerId, Long accountId,
			@Valid AccountObjectDto accountObjectDto) {

		AccountDetailResponseDto response = new AccountDetailResponseDto();

		AccountObjectDto accountDto = service.partialUpdateAccountOfCustomer(customerId, accountId, accountObjectDto);

		if (null != accountDto) {
			response.setData(accountDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CustomerDetailResponseDto> partialUpdateCustomer(Long customerId,
			@Valid CustomerObjectDto customerObjectDto) {
		
		CustomerDetailResponseDto response = new CustomerDetailResponseDto();

		CustomerObjectDto customerDto = service.partialUpdateCustomer(customerId, customerObjectDto);

		if (null != customerDto) {
			response.setData(customerDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<AccountDetailResponseDto> updateAccountOfCustomer(Long customerId, Long accountId,
			@Valid AccountObjectDto accountObjectDto) {
		AccountDetailResponseDto response = new AccountDetailResponseDto();

		AccountObjectDto accountDto = service.updateAccountOfCustomer(customerId,accountId, accountObjectDto);

		if (null != accountDto) {
			response.setData(accountDto);
		}

		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CustomerDetailResponseDto> updateCustomer(Long customerId,
			@Valid CustomerObjectDto customerObjectDto) {

		CustomerDetailResponseDto response = new CustomerDetailResponseDto();

		CustomerObjectDto customerDto = service.updateCustomer(customerId, customerObjectDto);

		if (null != customerDto) {
			response.setData(customerDto);
		}

		return ResponseEntity.ok(response);
	}
	
	
	@Override
	public ResponseEntity<AccountDetailResponseDto> createAccountByCustomer(Long customerId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion, @Valid AccountObjectDto accountObjectDto) {
		AccountDetailResponseDto response = new AccountDetailResponseDto();

		AccountObjectDto accountDto = service.createAccountByCustomer(customerId, accountObjectDto);

		if (null != accountDto) {
			response.setData(accountDto);
		}

		return ResponseEntity.ok(response);
	}

}
