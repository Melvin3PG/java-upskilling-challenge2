package net.example.finance.mybank.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.enums.AccountType;

/**
 * Repository interface to handle DB operations.
 * @author aline.perez
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{
	
	/**
	 * This query deletes the account by account and customer IDs
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 */
	@Modifying
	@Query("delete from Account a where a.customerId = ?1 and a.accountNumber = ?2")
	void deleteAccountByAccountIdAndCustomerId(Long customerNumber, Long accountNumber);

	/**
	 * This query modifies the account by account and customer IDs
	 * @param accountType Account type (SAVING or CHECKING)
	 * @param balance Account balance
	 * @param overdraftAllowed Indicates if the account is overdraft
	 * @param overdraftAmount Total overdraft amount
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 */
	@Modifying
	@Query("update Account a set a.accountType = ?1, a.balance = ?2, a.overdraftAllowed = ?3, a.overdraftAmount = ?4 where a.customerId = ?5 and a.accountNumber = ?6")
	void updateAccountByAccountIdAndCustomerId(AccountType accountType, Float balance, Boolean overdraftAllowed, Float overdraftAmount, Long customerNumber, Long accountNumber);
	
	/**
	 * This query finds all the accounts related to the customer ID
	 * @param customerNumber customer identifier
	 * @return a List of Account object
	 */
	@Query("select a from Account a where a.customerId = ?1")
	List<Account>  getAllAccountsByCustomerId(Long customerNumber);
	
	/**
	 * This query gets an account by account and customer IDs
	 * @param customerNumber customer identifier
	 * @param accountNumber account identifier
	 * @return
	 */
	@Query("select a from Account a where a.customerId = ?1 and a.accountNumber = ?2")
	Account getAccountByAccountIdAndCustomerId(Long customerNumber, Long accountNumber);
}
