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

import com.example.mvnprg.openapi.api.AccountsApi;
import com.example.mvnprg.openapi.model.AccountDetailResponse;
import com.example.mvnprg.openapi.model.AccountListResponse;
import com.example.mvnprg.openapi.model.AccountObject;
import com.example.mvnprg.openapi.model.Notification;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.AccountException;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.enums.ResponseCodes;
import net.example.finance.mybank.model.enums.Severity;
import net.example.finance.mybank.service.AccountService;

/**
 * Restful resource for handle account operations.
 * @author aline.perez
 *
 */
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountsApi {

	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	/**
	 * Account service layer.
	 */
	private final AccountService accountService;
	/**
	 * Model mapper instance.
	 */
	private final ModelMapper modelMapper;

	/**
	 * Return an account by its account number.
	 * @param accountNumber the account number
	 * @return Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> getAccountByAccountNumber(Long accountId, String xChannelId,
			String xCountryCode, String xApplCode, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to retreieve account with number:" + accountId);
			Account accountFound = accountService.getAccountById(accountId);
			AccountObject data = modelMapper.map(accountFound, AccountObject.class);
			notification = buildNotification(ResponseCodes.IA03.name(), ResponseCodes.IA03.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("The account was retrieved successfully, account [{}]", accountId);
			return ResponseEntity.ok(response);
		} catch (AccountException e) {
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to create a new account, not found {}", e.getLocalizedMessage());
				notification = buildNotification(ResponseCodes.EA05.name(), ResponseCodes.EA05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to retrieve an account with number:" + accountId);
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
				response.setData(null);
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Create an account.
	 * @param account model with the account information
	 * @return  Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> createAccount(String xChannelId, String xCountryCode, String xApplCode,
			@Valid AccountObject accountObject, String xB3Spanid, String xB3Traceid, String xUserContext,
			String xApiVersion) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to create new account");
			Account account = modelMapper.map(accountObject, Account.class);
			final Account accountCreated = accountService.createAccount(account);
			AccountObject data = modelMapper.map(accountCreated, AccountObject.class);
			notification = buildNotification(ResponseCodes.IA01.name(), ResponseCodes.IA01.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Succesful operation");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			LOGGER.error("Error while trying to create an account"+ e.getMessage());
			response.setData(null);
			if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to create an account");
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Delete the specified account.
	 * @param accountNumber the account number
	 * @return  Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> deleteAccount(Long accountId) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to delete account:" + accountId);
			Account accountFound = accountService.getAccountById(accountId);
			accountService.deleteAccount(accountId);
			AccountObject data = modelMapper.map(accountFound, AccountObject.class);
			notification = buildNotification(ResponseCodes.IA07.name(), ResponseCodes.IA07.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("The account was deleted successfully, account [{}]", accountId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to delete, account not found {}", e.getMessage());
				notification = buildNotification(ResponseCodes.EA05.name(), ResponseCodes.EA05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {				
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to delete an account:" + accountId);
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Return all existing accounts.
	 * @return Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<AccountListResponse> getAllAccounts(String xChannelId, String xCountryCode, String xApplCode,
			String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion) {
		Notification notification = null;
		AccountListResponse response = new AccountListResponse();
		try {
			LOGGER.info("Trying to retreieve all the accounts");
			List<AccountObject> data = accountService.getAll().stream().map(x -> modelMapper.map(x, AccountObject.class)).collect(Collectors.toList());
			notification = buildNotification(ResponseCodes.IA03.name(), ResponseCodes.IA03.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Account were retrieved successfully");
			return ResponseEntity.ok(response);
		} catch (AccountException e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to retrieve all the accounts, not found {}", e.getMessage());
				notification = buildNotification(ResponseCodes.EA05.name(), ResponseCodes.EA05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {				
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to retrieve all the accounts");
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}

	/**
	 * Update the specified account
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return  Response Entity with code, message and Account
	 * 
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> updateAccount(Long accountId, @Valid AccountObject accountObject) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to update an account:" + accountId);
			Account account = modelMapper.map(accountObject, Account.class);
			Account accountUpdated = accountService.updateAccount(account, accountId);
			AccountObject data = modelMapper.map(accountUpdated, AccountObject.class);
			notification = buildNotification(ResponseCodes.IA02.name(), ResponseCodes.IA02.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Update was completed successfully, account [{}]", accountId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to update an account, not found {}", e.getMessage());
				notification = buildNotification(ResponseCodes.EA05.name(), ResponseCodes.EA05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {	
				LOGGER.error("The request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to update account:" + accountId);
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.internalServerError().body(response);
			}
		}
	}
	
	/**
	 * Partial update for the specified account
	 * @param account model with the account information
	 * @param accountNumber the account number
	 * @return  Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<AccountDetailResponse> partialUpdateAccount(Long accountId,
			@Valid AccountObject accountObject) {
		Notification notification = null;
		AccountDetailResponse response = new AccountDetailResponse();
		try {
			LOGGER.info("Trying to update an account:" + accountId);
			Account account = modelMapper.map(accountObject, Account.class);
			Account accountUpdated = accountService.partialUpdateAccount(account, accountId);
			AccountObject data = modelMapper.map(accountUpdated, AccountObject.class);
			notification = buildNotification(ResponseCodes.IA02.name(), ResponseCodes.IA02.getValue(), "Account", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("Patial update was completed successfully, account [{}]", accountId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("Error while trying to apply a partial update an account, not found {}", e.getMessage());
				notification = buildNotification(ResponseCodes.EA05.name(), ResponseCodes.EA05.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {	
				LOGGER.error("Request body is malformed");
				notification = buildNotification(ResponseCodes.EA06.name(), ResponseCodes.EA06.getValue(), "Account", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else {
				LOGGER.error("Error while trying to apply a partial update an account:" + accountId);
				notification = buildNotification(ResponseCodes.EA04.name(), ResponseCodes.EA04.getValue(), "Account", Severity.ERROR.name());
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
