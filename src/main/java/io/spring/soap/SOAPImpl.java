package io.spring.soap;

import javax.jws.WebService;

//Service Implementation
@WebService(endpointInterface = "io.spring.soap.SOAP")
public class SOAPImpl implements SOAP
{

    @Override
    public String getSOAPAsString(String name)
    {
        return "* Hello World JAX-WS " + name;
    }

}