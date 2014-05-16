package io.spring.datajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/*
 * CustomerRepository extends the CrudRepository interface. 
 * 
 * The type of entity and ID that it works with,Customer and Long, are specified in the generic parameters on CrudRepository. 
 * 
 * By extending CrudRepository, CustomerRepository inherits several methods for working with Customer persistence, 
 * including methods for saving, deleting, and finding Customer entities
 * 
 * Spring Data JPA creates an implementation on the fly when you run the application!
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}