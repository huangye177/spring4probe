package io.spring.consumerest;

import org.springframework.web.client.RestTemplate;

public class ConsumeRESTApp
{
    public static void main(String args[])
    {
        System.out.println("ConsumeRESTApp loading...");
        RestTemplate restTemplate = new RestTemplate();

        // System.setProperty("http.keepAlive", "false");

        // restTemplate.setRequestFactory(new
        // HttpComponentsClientHttpRequestFactory());

        Page page = restTemplate.getForObject("http://graph.facebook.com/gopivotal", Page.class);
        System.out.println("Name:    " + page.getName());
        System.out.println("About:   " + page.getAbout());
        System.out.println("Phone:   " + page.getPhone());
        System.out.println("Website: " + page.getWebsite());
    }
}
