package ca.mcgill.purposeful.util;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import io.cucumber.datatable.DataTable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CucumberUtil {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  public static ArrayList<AppUser> unpackTableIntoUser(DataTable dataTable) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    // create an empty list of app users
    ArrayList<AppUser> appUsers = new ArrayList<AppUser>();

    for (var row : rows) {
      // get the values from the data table
      String firstname = row.get("firstname");
      String lastname = row.get("lastname");
      String email = row.get("email");
      String password = row.get("password");
      String authorities = row.get("authorities");
      Authority authority = Authority.valueOf(authorities);

      // create an instance of App User with these properties
      AppUser appUser = new AppUser();
      appUser.setFirstname(firstname);
      appUser.setLastname(lastname);
      appUser.setEmail(email);
      appUser.setPassword(password);

      // set the athorities of the app user
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      setOfAuthorities.add(authority);
      appUser.setAuthorities(setOfAuthorities);

      // add the app user to the list of app users
      appUsers.add(appUser);
    }
    return appUsers;
  }

  /**
   * This method creates and saves an appUsers from a data table
   * @param dataTable
   */
  public void createAndSaveAppUserFromTable(DataTable dataTable) {
    // get access to the data table
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      // create an instance of App User from the table values
      AppUser appUser = new AppUser();
      appUser.setFirstname(row.get("firstname"));
      appUser.setLastname(row.get("lastname"));
      appUser.setEmail(row.get("email"));
      appUser.setPassword(passwordEncoder.encode(row.get("password")));
      // set the athorities of the app user
      Set<Authority> setOfAuthorities = new HashSet<Authority>();
      Authority authority = Authority.valueOf(row.get("authorities"));
      setOfAuthorities.add(authority);
      // add the app user to the list of app users
      appUserRepository.save(appUser);
    }
  }

  /**
   * Method to generate the HttpHeaders for the basic auth, i.e. when a user first authenticate
   * @param email
   * @param password
   * @return HttpHeaders required in the request
   */
  public HttpHeaders basicAuthHeader(String email, String password) {
    HttpHeaders headers = new HttpHeaders();
    String auth = email + ":" + password;
    var authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    headers.add( "Authorization", authHeader );
    return headers;
  }

  /**
   * Method to generate the HttpHeaders for the bearer auth, i.e. when a user is already authenticated
   * @param jwtToken
   * @return HttpHeaders required in the request
   */
  public HttpHeaders bearerAuthHeader(String jwtToken) {
    HttpHeaders headers = new HttpHeaders();
    var authHeader = "Bearer " + jwtToken;
    headers.add( "Authorization", authHeader );
    return headers;
  }
}
