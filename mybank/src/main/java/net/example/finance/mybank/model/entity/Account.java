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
import net.example.finance.mybank.model.enums.AccountTypeEnum;


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
	 * Account number
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_number", unique = true)
	private Long accountNumber;
	
	/**
	 * Account Type -> SAVING, CHECKING 
	 */
	@Column(name = "account_type", nullable = false)
	private AccountTypeEnum accountType;
	
	/**
	 * Balance account
	 */
	@Column(name = "balance", nullable = false)
	private float balance;
	
	/**
	 * Indicates is overdraft
	 */
	@Column(name = "overdraft_allowed")
	private boolean overdraftAllowed;
	
	/**
	 * Overdraft amount
	 */
	@Column(name = "overdraft_amount")
	private float	overdraftAmount;
	
	/**
	 * Foreign key relation with customer
	 */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
