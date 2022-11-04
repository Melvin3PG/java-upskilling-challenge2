package net.example.finance.mybank.controller;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvnprg.openapi.api.AuthenticationApi;
import com.example.mvnprg.openapi.model.LoginObject;
import com.example.mvnprg.openapi.model.Notification;
import com.example.mvnprg.openapi.model.UserDetailResponse;
import com.example.mvnprg.openapi.model.UserObject;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.exception.UserException;
import net.example.finance.mybank.model.entity.UserData;
import net.example.finance.mybank.model.enums.ResponseCodes;
import net.example.finance.mybank.model.enums.Severity;
import net.example.finance.mybank.service.UserService;


/**
 * Restful resource to handle authentication requests.
 * @author aline.perez
 *
 */
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	/**
	 * User service layer.
	 */
	private final UserService userService;
	/**
	 * Model mapper instance.
	 */
	private final ModelMapper modelMapper;

	/**
	 * Return an user by its user name and password.
	 * @param accountNumber the account number
	 * @return Response Entity with code, message and Account
	 */
	@Override
	public ResponseEntity<UserDetailResponse> login(String xChannelId, String xCountryCode, String xApplCode,
			String xB3Spanid, String xB3Traceid, String xUserContext, String xApiVersion,
			@Valid LoginObject loginObject) {
		Notification notification = null;
		UserDetailResponse response = new UserDetailResponse();
		try {
			LOGGER.info("Trying to authenticate user {}", loginObject.getUsername());
			final UserData userData = userService.getUserByUserNameAndPassword(loginObject.getUsername(), loginObject.getPassword());
			UserObject data = modelMapper.map(userData, UserObject.class);
			notification = buildNotification(ResponseCodes.IU01.name(), ResponseCodes.IU01.getValue(), "Authentication", Severity.INFO.name());
			response.setData(data);
			response.addNotificationsItem(notification);
			LOGGER.info("User authenticated {}", loginObject.getUsername());
			return ResponseEntity.ok(response);
		} catch (UserException e) {
			response.setData(null);
			if(e.getCause() instanceof NotFoundException) {
				LOGGER.error("User name or password [{}] was not found while trying to authenticate, {}", loginObject.getUsername(), e.getMessage());
				notification = buildNotification(ResponseCodes.EU02.name(), ResponseCodes.EU02.getValue(), "Authentication", Severity.WARNING.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			} else if(e.getCause() instanceof HttpMessageNotReadableException || e.getCause() instanceof MissingRequestHeaderException) {
				LOGGER.error("Malformed request body");
				notification = buildNotification(ResponseCodes.EU04.name(), ResponseCodes.EU04.getValue(), "Customer", Severity.ERROR.name());
				response.addNotificationsItem(notification);
				return ResponseEntity.badRequest().body(response);
			} else  {
				LOGGER.error("Something went wrong while trying to authenticate user {}", loginObject.getUsername());
				notification = buildNotification(ResponseCodes.EU03.name(), ResponseCodes.EU03.getValue(), "Authentication", Severity.ERROR.name());
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
