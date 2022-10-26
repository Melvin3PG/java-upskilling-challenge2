package net.example.finance.mybank.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.finance.mybank.model.enums.AccountType;

/**
 * Entity class for account domain 
 * @author aline.perez
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7660205240542408705L;

	/** Account number */
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNumber;
	
	/** Account type (SAVING or CHECKING)*/
	@Enumerated(EnumType.STRING)
	//@NotNull(message= "Account type is mandatory")
	private AccountType accountType;
	
	/** Account balance */
	private Float balance;
	
	/** Indicates if the account is overdraft */
	private Boolean overdraft;
	
	/** Total overdraft amount*/
	private Float overdraftAmount;
	
}
