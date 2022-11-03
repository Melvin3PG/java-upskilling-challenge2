package net.example.finance.mybank.model.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import net.example.finance.mybank.model.enums.AccountTypeEnum;
import net.example.finance.mybank.openapi.model.AccountObjectDto;

/**
 * Data transfer object for Account
 * 
 * @author jesus.quintero
 *
 */
@Data
public class AccountDto extends AccountObjectDto{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
