package net.example.finance.mybank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
 * This class is used to throw exception when the record in 
 * database does not exists 
 * 
 * @author jesus.quintero
 *
 */
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Resource name that is being searched for
	 */
	private String resourceName;
	
	/**
	 * Resource attribute
	 */
	private String fieldName;
	
	/**
	 *	Attribute value
	 */
	private String fieldValue;

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("The %s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
