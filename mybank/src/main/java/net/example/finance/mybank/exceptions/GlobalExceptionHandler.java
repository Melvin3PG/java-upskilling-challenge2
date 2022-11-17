package net.example.finance.mybank.exceptions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;
import net.example.finance.mybank.constants.TransactionCodes;
import net.example.finance.mybank.model.dto.BaseResponseDto;
import net.example.finance.mybank.openapi.model.NotificationDto;

/**
 * This class is used to handle the different exceptions during execution.
 * 
 * @author jesus.quintero
 *
 */
@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	//handling  global exception
	
	/**
	 * Handle unexpected exception during execution.
	 * 
	 * @param exception			
	 * @param webRequest		
	 * 
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<NotificationDto> handleGlobalException(Exception exception,
													WebRequest webRequest){
		NotificationDto notification = new NotificationDto();
		notification.setCode(TransactionCodes.ERROR.getCode());
		notification.setUuid(webRequest.getHeader("TRX-ID"));
		notification.setMessage(exception.getMessage());
		notification.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC));
		notification.setSeverity("ERROR");
		log.error(String.format("Error occurring during transaction. Error: %s.", exception.getMessage()));
		return new ResponseEntity<>(notification, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//handling request parameters validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
											HttpHeaders headers, 		
											HttpStatus status, 
											WebRequest webRequest) {
		
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			
			errors.put(fieldName, message);
		});
		
		BaseResponseDto response = new BaseResponseDto(LocalDateTime.now(),
										webRequest.getHeader("TRX-ID"), 
										//HttpStatus.BAD_REQUEST.name(),
										TransactionCodes.NOT_VALID.getCode(),
										exception.getMessage(), errors);
		
		log.error(String.format("Error occurring during transaction. Error: %s.", exception.getMessage()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	//handling specific exceptions
	/**
	 * Handle the ResourceNotFoundException when the resource is not available in the service.
	 * 
	 * @param exception
	 * @param webRequest
	 * 
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<NotificationDto> handleResourceNotFoundException(ResourceNotFoundException exception,
												WebRequest webRequest){
		
		NotificationDto notification = new NotificationDto();
		notification.setCode(TransactionCodes.NOT_VALID.getCode());
		notification.setUuid(webRequest.getHeader("TRX-ID"));
		notification.setMessage(exception.getMessage());
		notification.setTimestamp(LocalDateTime.now().atOffset(ZoneOffset.UTC));
		notification.setSeverity("WARNING");
		
		log.error(String.format("Error occurring during transaction. Error: %s.", exception.getMessage()));
		return new ResponseEntity<>(notification, HttpStatus.NOT_FOUND);
	}
}
