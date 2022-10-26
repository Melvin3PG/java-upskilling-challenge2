package net.example.finance.mybank.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class CustomerDto {

	private long id;
	
	private String name;
	
	private Date birthdate;
	
	private boolean isActive;

	private boolean isNotDebts;
	
	Set<AccountDto> accounts = new HashSet<>(); 
}
