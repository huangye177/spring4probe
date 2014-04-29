package rest.yummynoodlebar.rest.functional;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Recommended alternative testing class/method:
 * CreateNewOrderIntegrationTest.thatCreateOrderUsesHttpBasicCreated
 * 
 * @deprecated
 */
@Deprecated
public class OrderTests
{
    /*
     * Recommended alternative testing class/method:
     * CreateNewOrderIntegrationTest.thatCreateOrderUsesHttpBasicCreated
     * 
     * @deprecated
     */
    // @Test
    // public void thatOrdersCanBeAddedAndQueried()
    // {
    // HttpEntity<String> requestEntity = new HttpEntity<String>(
    // RestDataFixture.standardOrderJSON(),
    // getHeaders("http" + ":" + "http"));
    //
    // System.out.println("* " + requestEntity.toString());
    //
    // RestTemplate template = new RestTemplate();
    //
    // ResponseEntity<Order> entity =
    // template.postForEntity("http://localhost:8080/aggregators/orders",
    // requestEntity, Order.class);
    //
    // String path = entity.getHeaders().getLocation().getPath();
    //
    // assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    // assertTrue(path.startsWith("/aggregators/orders/"));
    // Order order = entity.getBody();
    //
    // System.out.println("The Order ID is " + order.getKey());
    // System.out.println("The Location is " +
    // entity.getHeaders().getLocation());
    //
    // // assertEquals(2, order.getItems().size());
    // }

    /*
     * Recommended alternative testing class/method:
     * CreateNewOrderIntegrationTest.thatCreateOrderUsesHttpBasicCreated
     * 
     * @deprecated
     */
    // @Test
    // public void thatOrdersCannotBeAddedAndQueriedWithBadUser()
    // {
    //
    // HttpEntity<String> requestEntity = new HttpEntity<String>(
    // RestDataFixture.standardOrderJSON(),
    // getHeaders("http" + ":" + "BADPASSWORD"));
    //
    // RestTemplate template = new RestTemplate();
    // try
    // {
    // ResponseEntity<Order> entity = template.postForEntity(
    // "http://localhost:8080/aggregators/orders",
    // requestEntity, Order.class);
    //
    // fail("Request Passed incorrectly with status " + entity.getStatusCode());
    // }
    // catch (HttpClientErrorException ex)
    // {
    // assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
    // }
    // }

    static HttpHeaders getHeaders(String auth)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
        // headers.add("Authorization", "Basic " + new
        // String(encodedAuthorisation));

        byte[] encodedAuthorisation = org.springframework.security.crypto.codec.Base64.encode(auth.getBytes(Charset.forName("UTF-8")));
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

        return headers;
    }
}
