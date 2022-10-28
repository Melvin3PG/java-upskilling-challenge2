package net.example.finance.mybank.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for base response.
 * 
 * @author jesus.quintero
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto {

	//Transaction ID to track in logs the request in complete flow process.
	private String TransactionId;
	
	//Response code
	private String code;
	
	//Message for clients
	private String message;
	
	//Object to return result object with information needed for clients
	private Object data;
}
