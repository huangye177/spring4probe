package io.spring.datamongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/*
 * CustomerRepository extends the MongoRepository interface and plugs in the type of values 
 * and id it works with: Customer and String. Out-of-the-box, this interface comes with many operations, 
 * including standard CRUD operations (create-read-update-delete)
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}