package net.example.finance.mybank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.example.finance.mybank.model.enums.AccountType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "accounts")
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "account_number", unique = true)
	private String number;
	
	@Column(name = "type", nullable = false)
	private AccountType type;
	
	@Column(name = "balance", nullable = false)
	private double balance;
	
	@Column(name = "is_overdraft")
	private boolean isOverdraft;
	
	@Column(name = "overdraft_amount")
	private double	overdraftAmount;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
