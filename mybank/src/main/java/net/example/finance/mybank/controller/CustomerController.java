package net.example.finance.mybank.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.CustomersApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.CustomerDetailResponse;
import com.example.mvnprg.openapi.model.CustomerListResponse;
import com.example.mvnprg.openapi.model.CustomerObject;
import com.example.mvnprg.openapi.model.Notification;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.enums.ResponseCodes;
import net.example.finance.mybank.model.enums.Severity;
import net.example.finance.mybank.service.AccountService;
import net.example.finance.mybank.service.CustomerService;

/**
 * Restful resource for handle account operations.
 * @author aline.perez
 *
 */
@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {
	
	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
	/**
	 * Customer service layer.
	 */
	private final CustomerService customerService;
	/**
	 * Account service layer.
	 */
	private final AccountService accountService;
	/**
	 * Model mapper instance.
	 */
	private final ModelMapper modelMapper;
	
	/**
	 * Create a new customer
	 * @param customerObject DTO with the customer information
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerDetailResponse> createCustomer(String xChannelId, String xCountryCode,
			String xApplCode, @Valid CustomerObject customerObject, String xB3Spanid, String xB3Traceid,
			String xUserContext, String xApiVersion) {
		Notification notification = null;
		CustomerDetailResponse response = new CustomerDetailResponse();
		try {
			LOGGER.info("Trying to create a new customer");
			Customer customer = modelMapper.map(customerObject, Customer.class);
			Customer customerCreated = customerService.createcustomer(customer);
			CustomerObject data = modelMapper.map(customerCreated, CustomerObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC01.name(), ResponseCodes.IC01.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer was created successfully");
			return  ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			LOGGER.error("Error while trying to create an customer {}", e.getMessage());
			response.setData(null);
			if(e.getCause() instanceof HttpMessageNotReadableException || e instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to create customer", e.getMessage());
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Delete the specified account related to a customer
	 * @param customerId customer identifier
	 * @param accountId account identifier
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> deleteAccountOfCustomer(Long customerId, Long accountId) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to delete customer's account with number {}", accountId);
			customerService.verifyAccountOfCustomer(customerId, accountId);
			Account account = accountService.getAccountById(accountId);
			AccountObject data = modelMapper.map(account, AccountObject.class);
			accountService.deleteByAccountIdAndCustomerId(customerId, accountId);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IA07.name(), ResponseCodes.IA07.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer's account was deleted successfully, account [{}]", accountId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to delete customer's account with number {}, {}", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC07.name(), ResponseCodes.EC07.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to delete customer's account with number {}, {}", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Delete the specified customer
	 * @param customerId customer identifier
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerDetailResponse> deleteCustomer(Long customerId) {
		Notification notification = null;
		CustomerDetailResponse response = new CustomerDetailResponse();
		try {
			LOGGER.info("Trying to delete a customer");
			Customer customerFound = customerService.getcustomerById(customerId);
			customerService.deletecustomer(customerId);
			CustomerObject data = modelMapper.map(customerFound, CustomerObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC04.name(), ResponseCodes.IC04.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer was deleted successfully, customer [{}]", customerId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			LOGGER.error("Error while trying to delete a customer{}", e);
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to delete customer with number {}, not found}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to delete customer with number {}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Get the specified account related to a customer
	 * @param customerId customer identifier
	 * @param accountId account identifier
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByCustomerAndAccountNumber(Long customerId, Long accountId,
			String xChannelId, String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid,
			String xUserContext, String xApiVersion) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to get customer's account with number {}", accountId);
			Account account = accountService.getByAccountIdAndCustomerId(customerId, accountId);
			AccountObject data = modelMapper.map(account, AccountObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC03.name(), ResponseCodes.IC03.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer's account was retrieved successfully, customer number: [{}], account number [{}]", customerId, accountId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to get customer with number {}, not found}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC07.name(), ResponseCodes.EC07.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to retrieve customer's account with number {}, {}", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Get all the accounts related to a customer
	 * @param customerId customer identifier
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountListResponse> getAllAccountsOfCustomer(Long customerId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		Notification notification = null;
		AccountListResponse response = new AccountListResponse();
		try {
			LOGGER.info("Trying to get all customer accounts");
			List<AccountObject> data = accountService.getAllAccountsByCustomerId(customerId).stream()
										.map(a -> modelMapper.map(a, AccountObject.class))
										.collect(Collectors.toList());
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC03.name(), ResponseCodes.IC03.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer's accounts was retrieved successfully, customer number: [{}]", customerId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to get all customer accounts, customer with number {}, not found}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to retrieve all customer accounts"+ e.getMessage());
				response.setData(null);
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Get all the customers
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerListResponse> getAllCustomers(String xChannelId, String xCountryCode,
			String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		Notification notification = null;
		CustomerListResponse response = new CustomerListResponse();
		try {
			LOGGER.info("Trying to get all customers");
			List<CustomerObject> data = customerService.getAll().stream().map(x -> modelMapper.map(x, CustomerObject.class)).collect(Collectors.toList());
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC03.name(), ResponseCodes.IC03.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customers were retrieved successfully");
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to get all customers {}, not found", e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to retrieve all customers"+ e.getMessage());
				response.setData(null);
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	
	/**
	 * Get the specified customer by its ID
	 * @param customerId customer identifier
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerDetailResponse> getCustomerByCustomerNumber(Long customerId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		Notification notification = null;
		CustomerDetailResponse response = new CustomerDetailResponse();
		try {
			LOGGER.info("Trying to get customer with number {}", customerId);
			final Customer customer = customerService.getcustomerById(customerId);
			final CustomerObject data = modelMapper.map(customer, CustomerObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC03.name(), ResponseCodes.IC03.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer was retrieved successfully, customer number: [{}]", customerId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to get customer with number {}, not found}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Partial update for a specified account related to a customer
	 * @param customerId customer identifier
	 * @param accountId account identifier
	 * @param accountObject account DTO
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> partialUpdateAccountOfCustomer(Long customerId, Long accountId,
			@Valid AccountObject accountObject) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to apply a partial update to a customer's account: {}",  accountId);
			Account account = modelMapper.map(accountObject, Account.class);
			customerService.verifyAccountOfCustomer(customerId, accountId);
			Account accountUpdated = accountService.partialUpdateAccount(account, accountId);
			AccountObject data = modelMapper.map(accountUpdated, AccountObject.class);
			notification = buildNotification(ResponseCodes.IC02.name(), ResponseCodes.IC02.getValue(), "Customer", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Partial update for customer's account was completed successfully, customer number: [{}], account number [{}]", customerId, accountId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to apply a partial update to a customer's account: {}, not found ", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC07.name(), ResponseCodes.EC07.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			}  else {
				LOGGER.error("Error while trying to update customer's account:" + accountId);
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	
	/**
	 * Partial update for a customer
	 * @param customerId customer identifier
	 * @param customerObject customer DTO
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerDetailResponse> partialUpdateCustomer(Long customerId,
			@Valid CustomerObject customerObject) {
		Notification notification = null;
		CustomerDetailResponse response = new CustomerDetailResponse();
		try {
			LOGGER.info("Trying to apply a partial update to a customer:" + customerId);
			Customer customer = modelMapper.map(customerObject, Customer.class);
			Customer customerUpdated = customerService.partialUpdateCustomer(customer, customerId);
			CustomerObject data = modelMapper.map(customerUpdated, CustomerObject.class);
			notification = buildNotification(ResponseCodes.IC03.name(), ResponseCodes.IC03.getValue(), "Customer", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Partial update for customer was completed successfully, customer number: [{}]", customerId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to apply a partial update to a customer: {}, not found {}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to update customer:" + customerId);
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Update a specified account related to a customer
	 * @param customerId customer identifier
	 * @param accountId account identifier
	 * @param accountObject account DTO
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> updateAccountOfCustomer(Long customerId, Long accountId,
			@Valid AccountObject accountObject) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to update customer's account with number {}", accountId);
			Account account = modelMapper.map(accountObject, Account.class);
			Account accountUpdated = accountService.updateByAccountIdAndCustomerId(account, customerId, accountId);
			AccountObject data = modelMapper.map(accountUpdated, AccountObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IC02.name(), ResponseCodes.IC02.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer's account was updated successfully, customer number: [{}], account number [{}]", customerId, accountId);
			return  ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to update customer's account with number {}, not found {}", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC07.name(), ResponseCodes.EC07.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to update customer's account with number {}, {}", accountId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	
	/**
	 * Update a specified customer
	 * @param customerId customer identifier
	 * @param customerObject customer DTO
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<CustomerDetailResponse> updateCustomer(Long customerId,
			@Valid CustomerObject customerObject) {
		Notification notification = null;
		CustomerDetailResponse response = new CustomerDetailResponse();
		try {
			LOGGER.info("Trying to update customer:" + customerId);
			Customer customer = modelMapper.map(customerObject, Customer.class);
			Customer customerUpdated = customerService.updatecustomer(customer, customerId);
			CustomerObject data = modelMapper.map(customerUpdated, CustomerObject.class);
			notification = buildNotification(ResponseCodes.IC02.name(), ResponseCodes.IC02.getValue(), "Customer", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("The customer was updated successfully, customer number: [{}]", customerId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to update customer's account with number {}, not found {}", customerId, e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EC06.name(), ResponseCodes.EC06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response); 
			} else {
				LOGGER.error("Error while trying to update customer:" + customerId);
				notification = buildNotification(ResponseCodes.EC04.name(), ResponseCodes.EC04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Create an account for a specified customer
	 * @param customerId customer identifier
	 * @param accountObject account DTO
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> addAccountToCustomer(Long customerId,
			@Valid AccountObject accountObject) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to create a new account for customer {}", customerId);
			//call to customer service to validate if customer exists
			customerService.getcustomerById(customerId);
			Account account = modelMapper.map(accountObject, Account.class);
			account.setCustomerId(customerId);
			Account accountCreated = accountService.createAccount(account);
			AccountObject data = modelMapper.map(accountCreated, AccountObject.class);
			response.setData(data);
			notification = buildNotification(ResponseCodes.IA01.name(), ResponseCodes.IA01.getValue(), "Customer", Severity.INFO.name());
			response.addNotificationsItem(notification);
			LOGGER.info("The customer's account was created successfully");
			return  ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			LOGGER.error("Error while trying to create new customer's account {}", e.getMessage());
			response.setData(null);
			if(e.getCause() instanceof HttpMessageNotReadableException || e instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to create customer's account, customer does not exist", e.getMessage());
				notification = buildNotification(ResponseCodes.EC05.name(), ResponseCodes.EC05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}  else {
				LOGGER.error("Error while trying to create new customer's account", e.getMessage());
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}
	
	
	
	/**
	 * Notification builder method
	 * @param code Notification code
	 * @param message Description of the response.
	 * @param fieldName 
	 * @param severity value from {@link Severity}
	 * @return notification {@link Notification}
	 */
	private Notification buildNotification(String code, String message, String fieldName, String severity) {
		Notification notification = new Notification();
		notification.setCode(code);
		notification.setMessage(message);
		notification.setFieldName(fieldName);
		notification.setUuid(UUID.randomUUID().toString());
		notification.setTimestamp(OffsetDateTime.now());
		notification.setSeverity(severity);
		
		return notification;
	}



	
}
