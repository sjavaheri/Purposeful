package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dao.RegularUserRepository;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.dto.SearchFilterDTO;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.model.RegularUser;
import ca.mcgill.purposeful.service.IdeaService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API for demonstrating how permissions work for access to endpoints
 */

@RestController
@RequestMapping({"/api/idea", "/api/idea/"})
public class IdeaController {

  @Autowired
  IdeaService ideaService;

  @Autowired
  RegularUserRepository regularUserRepository;

  @GetMapping("{id}")
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<IdeaDTO> getIdeaById(@PathVariable String id) {
    Idea idea = ideaService.getIdeaById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO(idea));
  }

  /**
   * Filter ideas by topics, domains, and techs. Results are ordered by date.
   *
   * @return A list of idea DTOs that matches the filters
   * @author Wassim Jabbour
   */
  @PostMapping
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<List<IdeaDTO>> filterIdeas(@RequestBody SearchFilterDTO searchFilterDTO) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(IdeaDTO.convertToDto(ideaService.getIdeasByAllCriteria(searchFilterDTO.getDomains(),
            searchFilterDTO.getTopics(), searchFilterDTO.getTechnologies())));
  }

  /**
   * This method creates an idea
   *
   * @return created idea
   * @throws Exception
   * @author Adam Kazma
   */
  @PostMapping(value = {"/create", "/create/"})
  @PreAuthorize("hasAuthority('User')")
  public IdeaDTO createIdea(@RequestParam("title") String title,
      @RequestParam("purpose") String purpose, @RequestParam("description") String description,
      @RequestParam("isPaid") boolean isPaid, @RequestParam("inProgress") boolean inProgress,
      @RequestParam("isPrivate") boolean isPrivate, @RequestParam("domains") List<String> domainIds,
      @RequestParam("techs") List<String> techIds, @RequestParam("topics") List<String> topicIds,
      @RequestParam("imgUrls") List<String> imgUrlIds, @RequestParam("iconUrl") String iconUrlId)
      throws Exception {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      throw new Exception("User not authenticated.");
    } else {
      System.out.println("||||||    " + auth.getName() + "     ||||||");
    }
    RegularUser user = regularUserRepository.findRegularUserByAppUserEmail(auth.getName());
    Idea createdIdea = ideaService.createIdea(title, purpose, description, isPaid, inProgress,
        isPrivate, domainIds, techIds, topicIds, imgUrlIds, iconUrlId, user);
    return new IdeaDTO(createdIdea);
  }

  /**
   * This method modifies an idea
   *
   * @return update idea
   * @throws Exception
   * @author Ramin Akhavan
   */
  @PutMapping(value = {"/idea/edit", "/idea/edit/"})
  public IdeaDTO modifyIdea(@RequestParam("id") String id, @RequestParam("title") String title,
      @RequestParam("purpose") String purpose, @RequestParam("descriptions") String descriptions,
      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME,
          pattern = "yyyy-mm-dd") Date date,
      @RequestParam("isPaid") boolean isPaid, @RequestParam("inProgress") boolean inProgress,
      @RequestParam("isPrivate") boolean isPrivate, @RequestParam("domains") List<String> domainIds,
      @RequestParam("techs") List<String> techIds, @RequestParam("topics") List<String> topicIds,
      @RequestParam("imgUrls") List<String> imgUrlIds, @RequestParam("iconUrl") String iconUrlId)
      throws Exception {
    Idea modifiedIdea = ideaService.modifyIdea(id, title, date, descriptions, purpose, isPaid,
        inProgress, isPrivate, domainIds, techIds, topicIds, imgUrlIds, iconUrlId);
    return new IdeaDTO(modifiedIdea);
  }

  /**
   * Remove an idea by its id
   *
   * @param id the idea's id
   * @return a response entity with a message instance and the HttpStatus
   * @author Athmane Benarous
   */
  @DeleteMapping({"/{id}", "/{id}/"})
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<String> removeIdea(@PathVariable String id) {
    // call service layer
    ideaService.removeIdeaById(id);
    // return response status with confirmation message
    return new ResponseEntity<String>("Idea successfully deleted", HttpStatus.OK);
  }
}
