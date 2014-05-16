package io.spring.datamongodb;

import org.springframework.data.annotation.Id;


public class Customer {

	/*
	 * id fits the standard name for a MongoDB id so it doesn’t require any special annotation 
	 * to tag it for Spring Data MongoDB
	 */
    @Id
    private String id;

    private String firstName;
    private String lastName;

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}
