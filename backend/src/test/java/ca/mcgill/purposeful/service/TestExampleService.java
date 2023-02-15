package ca.mcgill.purposeful.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.purposeful.dao.AppUserRepository;
import ca.mcgill.purposeful.model.AppUser;

/**
 * This is an example class to provide a template for all service tests
 * 
 * @Author Shidan Javaheri
 */
@ExtendWith(MockitoExtension.class)
public class TestExampleService {

    // mock all the repositories that the service uses (and any other dependancies
    // handled by spring)
    // i.e. mock everything that is @Autowired in the service
    @Mock
    private AppUserRepository exampleRepository;

    // inject mocks into the service you are testing
    @InjectMocks
    private LoginService exampleService;

    // create objects you will use in your test
    private AppUser user;

    // set the feilds in a before each
    @BeforeEach
    public void setup() {
        this.user = new AppUser();
        this.user.setEmail("regular.user.one@email.com");
    }

    /**
     * A postive outcome example test method
     * @Author Shidan Javaheri
     */
    @Test 
    public void poistiveOutcomeTest() { 

        // set up mocks. The number of mocks matches the number of calls to external dependancies in the service method

        // when this repository calls this method on this argument
        // then answer with this value
        // to return the same value use invocation.getArgument(0)
        
        // when(exampleRepository.save(any(AppUser.class))).thenAnswer((InvocationOnMock invocation ) -> this.user);

        // call the service method you are testing
        
        // Token result = exampleService.generateToken(this.user);

        // make assertions on the result
        assertEquals(1, 1);

        // make verifications that each repository was called the correct number of times
        // # of verifications matches # of mocks

        // verify(exampleRepository, times(1)).save(any(AppUser.class));
        
    }

    /**
     * A negative outcome example test method
     * @Author Shidan Javaheri
     */
    @Test
    public void negativeOutcomeTest() {

        // set up mocks. The number of mocks matches the number of calls to external dependancies in the service method

        // when this repository calls this method on this argument
        // then answer with this value
        // to return the same value use invocation.getArgument(0)
        
        // when(exampleRepository.save(any(AppUser.class))).thenAnswer((InvocationOnMock invocation ) -> this.user);

        // call the service method you are testing, and get the exception
        // service method is called within assert throws
       
        // GlobalException exception = assertThrows(GlobalException.class, () -> exampleService.generateToken(this.user));


        // make assertiosn on the exception
        // check the 1) Message and 2) status 
        assertEquals (1,1);


        // make verifications that each repository was called the correct number of times        
        // # of verifications matches # of mocks
        
        // verify(exampleRepository, times(1)).save(any(AppUser.class));        
    }
}
