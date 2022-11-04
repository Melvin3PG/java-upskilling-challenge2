package net.example.finance.mybank.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.example.finance.mybank.model.entity.Customer;

/**
 * Repository interface to handle DB operations.
 * @author aline.perez
 *
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	
	
}
