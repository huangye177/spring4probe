package io.spring.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/*
 * SOAP-based RPC style web service endpoint by using JAX-WS
 */
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface SOAP
{

    @WebMethod
    String getSOAPAsString(String name);

}