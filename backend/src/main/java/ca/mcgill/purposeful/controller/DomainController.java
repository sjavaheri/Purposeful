package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.DomainDTO;
import ca.mcgill.purposeful.model.Domain;
import ca.mcgill.purposeful.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/** API for accessing the endpoints of Domain */
@RestController
@RequestMapping({"/api/domain", "/api/domain/"})
public class DomainController {

  @Autowired
  private DomainService domainService;

  /**
   * GET method to retrieve all domains in the database.
   *
   * @return domains in the database
   * @author Adam Kazma
   */
  @GetMapping()
  @PreAuthorize("hasAuthority('User')")
  public ResponseEntity<List<DomainDTO>> getAllDomains() {
    ArrayList<DomainDTO> domains = new ArrayList<>();
    for (Domain d : domainService.getAllDomains()) {
      domains.add(new DomainDTO(d));
    }
    return new ResponseEntity<List<DomainDTO>>(domains, HttpStatus.OK);
  }
}
