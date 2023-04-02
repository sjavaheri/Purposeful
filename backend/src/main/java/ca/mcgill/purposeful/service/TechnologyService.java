package ca.mcgill.purposeful.service;

import ca.mcgill.purposeful.dao.TechnologyRepository;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Technology;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

/** Service functions of the Reaction class */
@Service
public class TechnologyService {

  /*
   * CRUD repos
   */

  @Autowired TechnologyRepository technologyRepository;

  /**
   * Method to retrieve all existing technologies from the database.
   *
   * @return the techs in the database.
   * @author Adam Kazma
   */
  @Transactional
  public List<Technology> getAllTechs() {

    List<Technology> techs = (List<Technology>) technologyRepository.findAll();

    if (techs == null) {
      throw new GlobalException(
          HttpStatus.NOT_FOUND, "Could not fetch technologies from database.");
    }

    return techs;
  }
}
