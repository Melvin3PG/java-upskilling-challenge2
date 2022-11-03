package net.example.finance.mybank.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import net.example.finance.mybank.model.dto.PaginatedDataDto;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.enums.AccountTypeEnum;
import net.example.finance.mybank.openapi.model.AccountObjectDto;
import net.example.finance.mybank.service.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean 
	AccountService accountService;
	
	Account account1 = new Account(1000l, AccountTypeEnum.SAVING, 1000.0f, false, 0.0f, new Customer());
	Account account2 = new Account(2000l, AccountTypeEnum.CHECKING, 50000.0f, true, 1000.0f, new Customer());
	Account account3 = new Account(3000l, AccountTypeEnum.SAVING, 7500.0f, false, 0.0f, new Customer());
	
	List<Account> accounts;
	List<AccountObjectDto> accountsDto;
	
	@MockBean
	PaginatedDataDto<AccountObjectDto> paginated;
	
	@BeforeEach
	public void initializeData() {
		accounts = new ArrayList<>(Arrays.asList(account1, account2, account3));
	}
	
	@Test
	public void getAllRecordsSucces() throws Exception {
		
		Mockito.when(accountService.getAll(0, 3, "id", "asc")).thenReturn(paginated);
		
		mockMvc.perform(MockMvcRequestBuilders
					.get("/accounts")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());		
	}
}
