package io.spring.soap.client;

/*
 * Create a Java web service client via wsimport tool
 */
public class SOAPWsimportClient
{
    public static void main(String[] args)
    {

        SOAPImplService soapImplService = new SOAPImplService();
        SOAP soapServicePort = soapImplService.getSOAPImplPort();

        System.out.println(soapServicePort.getSOAPAsString("SOAP-wsimport-user"));

    }
}
