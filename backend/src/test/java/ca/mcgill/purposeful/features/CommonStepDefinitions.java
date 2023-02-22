package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonStepDefinitions {

  @Autowired private DatabaseUtil databaseUtil;

  /**
   * Clear the database before every scenario test
   *
   * @author Wassim Jabbour
   */
  @Before
  public void setupAll() {
    databaseUtil.clearDatabase();
  }
}
