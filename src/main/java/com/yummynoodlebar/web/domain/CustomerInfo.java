package com.yummynoodlebar.web.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/*
 * The Command Object is a class that Spring will map the POST variables onto, parsing them into the given types on the class
 */
public class CustomerInfo implements Serializable
{

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String address1;

    @NotNull
    @NotEmpty
    private String postcode;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

}
