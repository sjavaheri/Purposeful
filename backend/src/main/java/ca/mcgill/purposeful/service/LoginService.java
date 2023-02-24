package ca.mcgill.purposeful.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Service to allow a user to login to the application Generates a JWT token for the user
 */
@Service
public class LoginService {

  @Autowired
  private JwtEncoder jwtEncoder;


  /**
   * Method to generate the JWT for the user if they are authenticated
   *
   * @param authentication - the authentication object for the user, retrieved by the
   *                       LoginController
   * @return the JWT token for the user
   */

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    // create an empty string array list
    ArrayList<String> grantedAuthorities = new ArrayList<String>();
    // add all the granted authorities for the user
    for (GrantedAuthority granted : authentication.getAuthorities()) {
      grantedAuthorities.add(granted.getAuthority());
    }
    // build the JWT token
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self") // not important - identifies the issuer of the token
        .issuedAt(now) // issue time
        .expiresAt(now.plus(7, ChronoUnit.DAYS)) // how long the token is valid for
        .subject(authentication.getName()) // the username
        .claim("grantedAuthorities",
            grantedAuthorities) // adding a record in the json object for the authorities the user has
        .build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
