package ca.mcgill.purposeful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import ca.mcgill.purposeful.exception.GlobalException;
import ca.mcgill.purposeful.model.*;
import ca.mcgill.purposeful.service.IdeaService;
import ca.mcgill.purposeful.dto.*;
import java.util.Date;


/**
 * API for demonstrating how permissions work for access to endpoints
 */

@RestController
public class IdeaController {

    @Autowired
    private IdeaService service;
}