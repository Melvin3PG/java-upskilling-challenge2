package net.example.finance.mybank.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.finance.mybank.model.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDetails {

	private String messageCode;
	private String messageDescription;
	private List<Account> account;
	
}
