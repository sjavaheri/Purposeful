package ca.mcgill.purposeful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.Idea;

/**
 * API for demonstrating how permissions work for access to endpoints
 */

@RestController
@RequestMapping({"/idea", "/idea/"})
public class IdeaController {

    /**
     * This methods gets all librarians for the database
     * 
     * @author Ramin Akhavan
     * @return update idea
     * @throws Exception
     */
//   @PutMapping
//   public IdeaDTO modifyIdea(@RequestParam String title, @RequestParam String purpose, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern="yyyy-mm-dd") date, ) throws Exception{
    
//   }
}