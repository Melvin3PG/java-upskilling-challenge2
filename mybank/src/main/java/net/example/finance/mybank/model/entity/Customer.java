package net.example.finance.mybank.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.example.finance.mybank.model.enums.CustomerType;

/**
 * Entity class for customer domain 
 * @author aline.perez
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 5614921716925427486L;

	/** Customer number */
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerNumber;
	
	/** Customer type [RETAIL OR CORPORATE] */
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	
	/** Flag that indicates if a customer is active */
	private Boolean active;
	
	/** Flag to indicate if the customer is at date */
	private Boolean atDate;
	
	/** List of accounts that belong to the customer */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId")
	private List<Account> accounts;
	
	
}
