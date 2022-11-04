package net.example.finance.mybank.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.example.finance.mybank.exception.CustomerException;
import net.example.finance.mybank.exception.NotFoundException;
import net.example.finance.mybank.model.entity.Account;
import net.example.finance.mybank.model.entity.Customer;
import net.example.finance.mybank.model.repository.CustomerRepository;
import net.example.finance.mybank.service.CustomerService;

/**
 * Customer Service implementation class
 * @author aline.perez
 *
 */
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	
	/**
	 * Logger instance.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	/**
	 * Repository Interface to perform operation on database.
	 */
	private final CustomerRepository customerRepository;
	/**
	 * Model mapper instance
	 */
	private final ModelMapper modelMapper;
	
	/**
	 * Creates a new customer and saves it on DB.
	 * @param customer model with all the information to build an customer
	 * @return The created customer
	 * @throws CustomerException If the customer parameter is null or if something goes wrong.
	 */
	@Override
	public Customer createcustomer(Customer customer) throws CustomerException {
		if(!isValid(customer)) {
			LOGGER.error("Customer must not be null");
			throw new CustomerException(" Customer must not be null");
		}
		Customer customerInserted = null;
		try {
			LOGGER.debug("Trying to create new customer {}", customer);
			customerInserted = customerRepository.save(customer);
		} catch (Exception e) {
			LOGGER.error("Something went wrong while trying to create a new customer {}", customer, e);
			throw new CustomerException(e.getMessage(), e);
		}
		return customerInserted;
	}

	/**
	 * Modify customer data.
	 * @param customer model with the customer information
	 * @param customerNumber the customer number
	 * @return The updated customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public Customer updatecustomer(Customer customer, Long customerNumber) throws CustomerException {
		if(!isValid(customer)) {
			LOGGER.error("Customer must not be null");
			throw new CustomerException(" Customer must not be null");
		}
		if(Objects.isNull(customerNumber)) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		Customer customerUpdated = null;
		try {
			LOGGER.debug("Trying to update a customer {}", customer);
			Customer customerFound = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			customer.setCustomerNumber(customerFound.getCustomerNumber());
			customer.setAccounts(customerFound.getAccounts());
			customerUpdated = customerRepository.save(customer);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update a customer {}", customer, e);
			throw new CustomerException("Something went wrong while trying to update a customer {}", e);
		}
		return customerUpdated;
	}

	
	/**
	 * Delete an customer from database.
	 * @param customerNumber the customer number
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public void deletecustomer(Long customerNumber) throws CustomerException {
		if (customerNumber==null) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			LOGGER.debug("Trying to delete the account {}", customerNumber);
			customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			customerRepository.deleteById(customerNumber);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to delete the customer {}", customerNumber, e);
			throw new CustomerException("Something went wrong while trying to delete the customer", e);
		}
	}

	
	/**
	 * Retrieve customer that match with the given customer number
	 * @param customerNumber the customer number
	 * @return customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public Customer getcustomerById(Long customerNumber) throws CustomerException {
		Customer customerRetrieved = null;
		if(customerNumber==null) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			LOGGER.debug("Trying to retreieve the customer {}", customerNumber);
			customerRetrieved = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the customer {}", customerNumber, e);
			throw new CustomerException("Something went wrong while trying to retrieve the customer", e);
		}
		return customerRetrieved;
	}

	/**
	 * Retrieve all the customers
	 * @return an customer List
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public List<Customer> getAll() throws CustomerException {
		List<Customer> accounts = null;
		try {
			LOGGER.debug("Trying to retreieve the customer ");
			accounts = (List<Customer>) customerRepository.findAll();
			if(accounts.isEmpty())
				throw new NotFoundException();
		} catch (NotFoundException e) {
			LOGGER.error("No customers were found", e);
			throw new CustomerException("No customers were found}", e);
		} catch (DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve the customers", e);
			throw new CustomerException("Something went wrong while trying to retrieve the customers", e);
		}
		return accounts;
	}

	/**
	 * This method applies partial modifications
	 * @param customer model with the customer information
	 * @param customerNumber the customer number
	 * @return The updated customer
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public Customer partialUpdateCustomer(Customer customer, Long customerNumber) throws CustomerException {
		Customer customerUpdated = null;
		if(Objects.isNull(customerNumber)) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			LOGGER.debug("Trying a partial update for an customer {}", customerNumber);
			Customer customerFound = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			customer.setCustomerNumber(customerFound.getCustomerNumber());
			modelMapper.map(customer, customerFound);
			customerUpdated = customerRepository.save(customerFound);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update an customer {}", customerNumber, e);
			throw new CustomerException("Something went wrong while trying to update an customer", e);
		}
		return customerUpdated;
	}

	/**
	 * Retrieve customer accounts
	 * @param customerNumber the customer number
	 * @return The accounts from customer
	 * @throws CustomerException if the some parameter is null or if something goes wrong.
	 */
	@Override
	public List<Account> getAccountsByCustomerId(Long customerNumber) throws CustomerException {
		List<Account> accounts = null;
		if(Objects.isNull(customerNumber)) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			Customer customer = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			accounts = customer.getAccounts();
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve customer accounts {}", customerNumber, e);
			throw new CustomerException("Something went wrong while trying to update an customer", e);
		}
		return accounts;
	}
	

	/**
	 * Retrieves an account related with the account and customer IDs provided
	 * @param customerNumber the customer identifier
	 * @param accountId account identifier
	 * @return an Account object
	 * @throws CustomerException if the customer parameter is null or if something goes wrong.
	 */
	@Override
	public Account getAccountByCustomerAndAccountId(Long customerNumber, Long accountId) throws CustomerException {
		Account account = null;
		if(Objects.isNull(customerNumber)) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			Customer customer = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			account = customer.getAccounts().stream().filter(x -> x.getAccountNumber().equals(accountId)).findFirst().orElseThrow(NotFoundException::new);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to retrieve customer accounts {}", customerNumber, e);
			throw new CustomerException("Something went wrong while trying to update an customer", e);
		}
		return account;
	}

	/**
	 * Verify if the accountId provided exists for the customer
	 * @param customerNumber the customer identifier
	 * @param accountId account identifier
	 * @throws CustomerException if some parameter is null or if something goes wrong.
	 */
	@Override
	public void verifyAccountOfCustomer(Long customerNumber, Long accountId) throws CustomerException {
		if(Objects.isNull(customerNumber)) {
			LOGGER.error("Customer number must not be null");
			throw new CustomerException("Customer number must not be null");
		}
		try {
			LOGGER.debug("Trying to update customer's account: {}", accountId);
			Customer customerFound = customerRepository.findById(customerNumber).orElseThrow(NotFoundException::new);
			customerFound.getAccounts().stream().filter(x -> x.getAccountNumber().equals(accountId)).findFirst().orElseThrow(NotFoundException::new);
		} catch (NotFoundException | DataAccessException e) {
			LOGGER.error("Something went wrong while trying to update customer's account {}", accountId, e);
			throw new CustomerException("Something went wrong while trying to update a customer {}", e);
		}
	}
	
	/**
	 * Validate Customer object
	 * @param customer
	 * @return a Boolean to indicate if it is valid or not
	 */
	private Boolean isValid(final Customer customer) {
		if(null == customer.getActive() ||
				null == customer.getAtDate() ||
						null == customer.getCustomerType()) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}




}
