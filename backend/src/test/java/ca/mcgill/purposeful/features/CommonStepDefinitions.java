package ca.mcgill.purposeful.features;

import ca.mcgill.purposeful.util.CucumberUtil;
import ca.mcgill.purposeful.util.DatabaseUtil;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonStepDefinitions {

  @Autowired private DatabaseUtil databaseUtil;

  @Autowired private CucumberUtil cucumberUtil;

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
