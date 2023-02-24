package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.AppUserDto;
import ca.mcgill.purposeful.dto.IdeaDTO;
import ca.mcgill.purposeful.dto.IdeaRequestDTO;
import ca.mcgill.purposeful.dto.SearchFilterDTO;
import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Idea;
import ca.mcgill.purposeful.service.IdeaService;
import java.util.Date;
import java.util.List;

import ca.mcgill.purposeful.util.DtoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * API for demonstrating how permissions work for access to endpoints
 */

@RestController
@RequestMapping({"api/idea", "api/idea/"})
public class IdeaController {

  @Autowired
  IdeaService ideaService;

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
        .body(
            IdeaDTO.convertToDto(
                ideaService.getIdeasByAllCriteria(
                    searchFilterDTO.getDomains(),
                    searchFilterDTO.getTopics(),
                    searchFilterDTO.getTechnologies())));
  }

  /**
   * This method modifies an idea
   *
   * @return update idea
   * @throws Exception
   * @author Ramin Akhavan
   */
//  @PutMapping(value = {"/edit/{id}", "/edit/{id}/"})
//  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
//  public ResponseEntity<IdeaDTO> modifyIdea(@PathVariable String id,
//      @RequestParam(value = "title", required = false) String title,
//      @RequestParam(value = "purpose", required = false) String purpose,
//      @RequestParam(value = "descriptions", required = false) String descriptions,
//      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "yyyy-mm-dd") Date date,
//      @RequestParam(value = "isPaid", required = false) boolean isPaid,
//      @RequestParam(value = "inProgress", required = false) boolean inProgress,
//      @RequestParam(value = "isPrivate", required = false) boolean isPrivate,
//      @RequestParam(value = "domains", required = false) List<String> domainIds,
//      @RequestParam(value = "techs", required = false) List<String> techIds,
//      @RequestParam(value = "topics", required = false) List<String> topicIds,
//      @RequestParam(value = "imgUrls", required = false) List<String> imgUrlIds,
//      @RequestParam(value = "iconUrl", required = false) String iconUrlId) throws Exception {
//    Idea modifiedIdea = ideaService.modifyIdea(id, title, date, descriptions, purpose, isPaid,
//        inProgress, isPrivate,
//        domainIds, techIds, topicIds, imgUrlIds, iconUrlId);
//    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO(modifiedIdea));
//  }

  /**
   * This method modifies an idea
   *
   * @return update idea
   * @throws Exception
   * @author Ramin Akhavan
   */
  @PutMapping(value = {"/edit", "/edit/"}, consumes = "application/json", produces = "application/json")
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<IdeaRequestDTO> modifyIdea(@RequestBody IdeaRequestDTO ideaDTO) throws Exception {
    // Unpack the DTO
    if (ideaDTO == null) {
      throw new GlobalException(HttpStatus.BAD_REQUEST, "ideaDTO is null");
    }
    IdeaRequestDTO modifiedIdea = new IdeaRequestDTO(ideaService.modifyIdea(ideaDTO.getId(), ideaDTO.getTitle(), ideaDTO.getDate(), ideaDTO.getDescription(), ideaDTO.getPurpose(), ideaDTO.getIsPaid(),
            ideaDTO.getInProgress(), ideaDTO.getIsPrivate(), ideaDTO.getDomainIds(), ideaDTO.getTechIds(), ideaDTO.getTopicIds(), ideaDTO.getImgUrlIds(), ideaDTO.getIconUrlId()));
    return ResponseEntity.status(HttpStatus.OK).body(modifiedIdea);
//    return ResponseEntity.status(HttpStatus.OK).body(new IdeaDTO());

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
