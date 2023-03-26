package ca.mcgill.purposeful.controller;

import ca.mcgill.purposeful.dto.TopicDTO;
import ca.mcgill.purposeful.model.Topic;
import ca.mcgill.purposeful.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/** API for accessing the endpoints of Topic */
@RestController
@RequestMapping({"/api/topic", "/api/topic/"})
public class TopicController {

  @Autowired
  private TopicService topicService;

  /**
   * GET method to retrieve all topics in the database.
   *
   * @return topics in the database
   * @author Adam Kazma
   */
  @GetMapping()
  @PreAuthorize("hasAnyAuthority('User', 'Moderator', 'Owner')")
  public ResponseEntity<List<TopicDTO>> getAllTopics() {
    ArrayList<TopicDTO> topics = new ArrayList<>();
    for (Topic t : topicService.getAllTopics()) {
      topics.add(new TopicDTO(t));
    }
    return new ResponseEntity<List<TopicDTO>>(topics, HttpStatus.OK);
  }
}
