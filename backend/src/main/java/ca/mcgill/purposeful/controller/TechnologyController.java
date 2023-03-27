package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.TechDTO;
import ca.mcgill.purposeful.model.Technology;
import ca.mcgill.purposeful.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/** API for accessing the endpoints of Technology */
@RestController
@RequestMapping({"/api/tech", "/api/tech/"})
public class TechnologyController {

  @Autowired
  private TechnologyService technologyService;

  /**
   * GET method to retrieve all technologies in the database.
   *
   * @return techs in the database
   * @author Adam Kazma
   */
  @GetMapping()
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<List<TechDTO>> getAllTechs() {
    ArrayList<TechDTO> techs = new ArrayList<>();
    for (Technology t : technologyService.getAllTechs()) {
      techs.add(new TechDTO(t));
    }
    return new ResponseEntity<List<TechDTO>>(techs, HttpStatus.OK);
  }
}
