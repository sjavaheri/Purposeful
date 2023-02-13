package ca.mcgill.purposeful.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.purposeful.configuration.Authority;
import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.model.AppUser;
import ca.mcgill.purposeful.model.RegularUser;

/**
 * This class tests the AppUser service
 */
@ExtendWith(MockitoExtension.class)
public class TestAppUserService {

  @Mock
  private AppUserRepository appUserRepository;

  @Mock
  private RegularUserRepository regularUserRepository;

  @InjectMocks
  private AppUserService appUserService;

  private static final String VALID_REGULARUSER_EMAIL_ONE = "regular.user.one@email.com";
  private static final String VALID_REGULARUSER_EMAIL_TWO = "regular.user.two@email.com";
  private static final String VALID_REGULARUSER_USERNAME_ONE = "regularUserOne";
  private static final String VALID_REGULARUSER_USERNAME_TWO = "regularUserTwo";

  private static final String VALID_PASSWORD = "Password1";

  private static final String INVALID_EMAIL = "invalid.email";
  private static final String INVALID_PASSQORD_ONE = "invalid";
  private static final String INVALID_PASSQORD_TWO = "invalidPassword";
  private static final String INVALID_PASSQORD_THREE = "invalidpassword1";
  private static final String INVALID_PASSQORD_FOUR = "INVALIDPASSWORD1";

  /**
   * Mocking the repositories
   * 
   * @author Siger Ma
   */
  @BeforeEach
  public void setMockOutput() {
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(appUserRepository.findAppUserByEmail(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(VALID_REGULARUSER_EMAIL_TWO)) {
            AppUser appUser = new AppUser();
            appUser.setEmail(VALID_REGULARUSER_EMAIL_TWO);
            appUser.setUsername(VALID_REGULARUSER_USERNAME_TWO);
            appUser.setPassword(VALID_PASSWORD);
            appUser.getAuthorities().add(Authority.User);
            return appUser;
          } else {
            return null;
          }
        });

    lenient().when(appUserRepository.findAppUserByUsername(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(VALID_REGULARUSER_USERNAME_TWO)) {
            AppUser appUser = new AppUser();
            appUser.setEmail(VALID_REGULARUSER_EMAIL_TWO);
            appUser.setUsername(VALID_REGULARUSER_USERNAME_TWO);
            appUser.setPassword(VALID_PASSWORD);
            appUser.getAuthorities().add(Authority.User);
            return appUser;
          } else {
            return null;
          }
        });

    lenient().when(appUserRepository.save(any(AppUser.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(regularUserRepository.save(any(RegularUser.class))).thenAnswer(returnParameterAsAnswer);
  }

  /**
   * Test the method that creates a new regular user
   * 
   * @author Siger Ma
   */
  @Test
  public void testCreateRegularUser() {
    // Create the regular user
    AppUser appUser = null;
    try {
      appUser = appUserService.registerRegularUser(VALID_REGULARUSER_EMAIL_ONE,
          VALID_REGULARUSER_USERNAME_ONE, VALID_PASSWORD);
    } catch (Exception e) {
      fail(e.getMessage());
    }

    // Check the regular user
    assertNotNull(appUser);
    assertEquals(VALID_REGULARUSER_EMAIL_ONE, appUser.getEmail());
    assertEquals(VALID_REGULARUSER_USERNAME_ONE, appUser.getUsername());
    assertEquals(VALID_PASSWORD, appUser.getPassword());
  }

  /**
   * Test the method that creates a new regular user with an empty email
   * 
   * @author Siger Ma
   */
  @Test
  public void testCreateRegularUserWithEmptyEmail() {
    // Create the regular user
    AppUser appUser = null;
    try {
      appUser = appUserService.registerRegularUser("", VALID_REGULARUSER_USERNAME_ONE,
          VALID_PASSWORD);
    } catch (Exception e) {
      assertNull(appUser);
      assertEquals("Please enter a valid email. Email cannot be left empty", e.getMessage());
    }
  }

  /**
   * Test the method that creates a new regular user with an empty username
   * 
   * @author Siger Ma
   */
  @Test
  public void testCreateRegularUserWithEmptyUsername() {
    // Create the regular user
    AppUser appUser = null;
    try {
      appUser = appUserService.registerRegularUser(VALID_REGULARUSER_EMAIL_ONE, "",
          VALID_PASSWORD);
    } catch (Exception e) {
      assertNull(appUser);
      assertEquals("Please enter a valid username. Username cannot be left empty", e.getMessage());
    }
  }

  /**
   * Test the method that creates a new regular user with an empty password
   * 
   * @author Siger Ma
   */
  @Test
  public void testCreateRegularUserWithEmptyPassword() {
    // Create the regular user
    AppUser appUser = null;
    try {
      appUser = appUserService.registerRegularUser(VALID_REGULARUSER_EMAIL_ONE,
          VALID_REGULARUSER_USERNAME_ONE, "");
    } catch (Exception e) {
      assertNull(appUser);
      assertEquals("Please enter a valid password. Password cannot be left empty", e.getMessage());
    }
  }
}
