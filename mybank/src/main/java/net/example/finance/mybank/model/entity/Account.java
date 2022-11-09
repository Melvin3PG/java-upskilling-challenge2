package net.example.finance.mybank.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Account number
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "account_number")
	private Long accountNumber;
	
	/**
	 * Account Type -> SAVING, CHECKING 
	 */
	@Column(name = "account_type")
	private AccountTypeEnum accountType;
	
	/**
	 * Balance account
	 */
	@Column(name = "balance")
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
}
