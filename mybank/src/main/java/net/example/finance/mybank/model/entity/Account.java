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


/**
 * Entity class for Account.
 * 
 * @author jesus.quintero
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "accounts")
@Entity
public class Account {

	/**
	 * Primary key for account
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Account number
	 */
	@Column(name = "account_number", unique = true)
	private String number;
	
	/**
	 * Account Type -> SAVING, CHECKING 
	 */
	@Column(name = "type", nullable = false)
	private AccountType type;
	
	/**
	 * Balance account
	 */
	@Column(name = "balance", nullable = false)
	private double balance;
	
	/**
	 * Indicates is overdraft
	 */
	@Column(name = "is_overdraft")
	private boolean isOverdraft;
	
	/**
	 * Overdraft amount
	 */
	@Column(name = "overdraft_amount")
	private double	overdraftAmount;
	
	/**
	 * Foreign key relation with customer
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
