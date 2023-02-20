package ca.mcgill.purposeful.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.util.Util;
import java.util.Base64;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

  @Autowired
  private TestRestTemplate client;


  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * Clear the database before all tests
   */
  @BeforeAll
  public static void clearDatabaseAndCreateAccounts(@Autowired Util util) { util.clearDatabase(); }

  /**
   * Clear the database after each test
   */
  @AfterEach
  public void clearDatabaseAfter(@Autowired Util util) {
    util.clearDatabase();
  }

  @Test
  public void testWithCustomHeader() {
    createAccount("owner.steve@gmail.com", "Owner", "Steve", "OwnerIsAwesome01", Authority.Owner);
//    createAccount("moderator.john@gmail.com", "Moderator", "John", "moderatorIsAwesome02", Authority.Moderator);
//    createAccount("user.jack@gmail.com", "User", "Jack", "userIsAwesome03", Authority.User);

    HttpEntity<String> requestEntity = new HttpEntity<>(basicAuthHeader("owner.steve@gmail.com", "1OwnerIsAwesome01"));
    HttpEntity<String> requestEntity2 = new HttpEntity<>("");

    ResponseEntity<String> response = client.exchange
        ("/login/", HttpMethod.POST, requestEntity, String.class);

//    String jwt = response.getBody().toString();
//    HttpEntity<String> requestEntity3 = new HttpEntity<>(bearerAuthHeader(jwt));
//    ResponseEntity<String> response2 = client.exchange
//        ("/demo/", HttpMethod.GET, requestEntity3, String.class);

//        ResponseEntity<String> response2 = client.withBasicAuth("owner.steve@gmail.com", "OwnerIsAwesome01").exchange("/demo",
//            HttpMethod.GET, requestEntity2, String.class);


//    ResponseEntity<String> response = client.withBasicAuth("owner.steve@gmail.com", "OwnerIsAwesome01").postForEntity(
//        "/login",requestEntity2, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

//    assertNotNull(response2);
//    assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response2.getStatusCode());
//    assertEquals("kj:", response2.getBody());
  }

  /**
   * Create and persist an appUser
   * @param email
   * @param firstname
   * @param lastname
   * @param password
   * @param authority
   * @author Sasha
   */
  private void createAccount(String email, String firstname, String lastname, String password, Authority authority) {

    var account = new AppUser();
    account.setEmail(email);
    account.setFirstname(firstname);
    account.setLastname(lastname);
    account.setPassword(passwordEncoder.encode(password));
    var authorities = new HashSet<Authority>();
    authorities.add(authority);
    account.setAuthorities(authorities);
    appUserRepository.save(account);
  }

  /**
   * Method to generate the HttpHeaders for the basic auth, i.e. when a user first authenticate
   * @param email
   * @param password
   * @return HttpHeaders required in the request
   */
  private HttpHeaders basicAuthHeader(String email, String password) {
    HttpHeaders headers = new HttpHeaders();
    String auth = email + ":" + password;
    var authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    headers.add( "Authorization", authHeader );
    return headers;
  }

  private HttpHeaders bearerAuthHeader(String jwtToken) {
    HttpHeaders headers = new HttpHeaders();
    var authHeader = "Bearer " + jwtToken;
    headers.add( "Authorization", authHeader );
    return headers;
  }

}
