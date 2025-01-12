package com.enit.bigdata.visualisation;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.*;
import java.util.stream.*;
@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api/bourse") // Base path for this controller's endpoints
public class BourseController {

    private final BourseRepository bourseRepository;

    @Autowired
    public BourseController(BourseRepository bourseRepository) {
        this.bourseRepository = bourseRepository;
    }

    // Endpoint to fetch all BourseAction records
    @GetMapping("/actions")
    public List<BourseAction> getAllBourseActions() {
        return bourseRepository.findAll();
    }
    @GetMapping("/actions/latest")
     public List<BourseAction> getMostRecentActions() {
        // Retrieve all actions
        List<BourseAction> allActions = bourseRepository.findAll();

        // Group by actionNom and get the most recent action for each actionNom
        return allActions.stream()
                .collect(Collectors.groupingBy(BourseAction::getActionNom))  // Group by actionNom
                .values()
                .stream()
                .map(actions -> actions.stream()
                        .max(Comparator.comparing(BourseAction::getTimestamp))  // Get the most recent action by timestamp
                        .orElse(null))  // If no action, return null
                .filter(Objects::nonNull)  // Remove null values
                .collect(Collectors.toList());
    }
}

