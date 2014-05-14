package io.spring.soap;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/*
 * Create a Java web service client manually
 * 
 * test the deployed web service by accessing the generated WSDL (Web Service Definition Language) document 
 * via this URL “http://localhost:9999/ws/hello?wsdl”
 */
public class SOAPClient
{
    public static void main(String[] args) throws Exception
    {

        URL url = new URL("http://localhost:9999/ws/hello?wsdl");

        // 1st argument service URI, refer to wsdl document above
        // 2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soap.spring.io/", "SOAPImplService");

        Service service = Service.create(url, qname);

        SOAP soapService = service.getPort(SOAP.class);

        System.out.println(soapService.getSOAPAsString("SOAPUser"));

    }
}
