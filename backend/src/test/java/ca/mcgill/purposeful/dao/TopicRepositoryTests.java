package ca.mcgill.ecse321.GSSS.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.GSSS.model.Address;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * To test the Address CRUD repository methods.
 *
 * @author Wassim Jabbour
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAddressPersistence {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private BusinessHourRepository businessHourRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ItemCategoryRepository itemCategoryRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private PurchaseRepository purchaseRepository;

  @Autowired
  private ShiftRepository shiftRepository;

  /**
   * Deletes all the database contents. Goes from the independent to the dependent classes to avoid
   * exceptions being thrown when deleting
   *
   * @author Wassim Jabbour
   */
  @AfterEach
  public void clearDatabase() {
    customerRepository.deleteAll();
    purchaseRepository.deleteAll();
    employeeRepository.deleteAll();
    shiftRepository.deleteAll();
    ownerRepository.deleteAll();
    addressRepository.deleteAll();
    itemRepository.deleteAll();
    itemCategoryRepository.deleteAll();
    businessHourRepository.deleteAll();
  }

  /**
   * To test the method findAddressById() for the address
   *
   * @author Wassim Jabbour
   */
  @Test
  public void testPersistAndLoadAddressById() {

    // Initializing the address to test
    String fullName = "Enzo Ferrari";
    String streetName = "Crescent";
    int streetNumber = 43;
    String city = "Montreal";
    String postalCode = "W4S 5I3";
    String id = UUID.randomUUID().toString();

    // Creating the address and setting the fields
    Address address = new Address();
    address.setFullName(fullName);
    address.setStreetName(streetName);
    address.setStreetNumber(streetNumber);
    address.setCity(city);
    address.setPostalCode(postalCode);
    address.setId(id);

    // Saving the address in memory
    addressRepository.save(address);

    // Setting the address to null
    address = null;

    // Finding the address by ID
    address = addressRepository.findAddressById(id);

    // Checking that this is the same address we saved earlier
    assertNotNull(address);
    assertEquals(address.getFullName(), fullName);
    assertEquals(address.getStreetName(), streetName);
    assertEquals(address.getStreetNumber(), streetNumber);
    assertEquals(address.getCity(), city);
    assertEquals(address.getPostalCode(), postalCode);
    assertEquals(address.getId(), id);

  }
  
  
  
  
  
  
 