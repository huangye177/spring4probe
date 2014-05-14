package io.spring.soap;

import javax.xml.ws.Endpoint;

/*
 * Run the endpoint publisher, and the "SOAP web service” is deployed in URL “http://localhost:9999/ws/hello"
 */
public class SOAPPublisher
{
    public static void main(String[] args)
    {
        Endpoint.publish("http://localhost:9999/ws/hello", new SOAPImpl());
    }
}
