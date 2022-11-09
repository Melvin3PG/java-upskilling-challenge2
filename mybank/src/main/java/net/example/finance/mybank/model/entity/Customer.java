package net.example.finance.mybank.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.example.finance.mybank.openapi.model.CustomerObjectDto.CustomerTypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "customers" )
@Entity
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "customer_number")
	private long customerNumber;
	
	/*
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "birthdate")
	private Date birthdate;
	*/
		
	@Column(name = "customer_type")
	private CustomerTypeEnum customerType;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "at_date")
	private boolean atDate;
	
	@OneToMany(mappedBy = "customer", cascade ={CascadeType.ALL}, orphanRemoval = true)
	private List<Account> accounts = new ArrayList<>(); 
	
	
	public List<Account> getAccounts(){
		return accounts;
	}
	
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
		account.setCustomer(this);
	}
}
