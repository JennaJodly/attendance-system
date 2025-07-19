
package com.example.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.attendance.entities.StateMstr;
import com.example.attendance.repositories.StateMstrRepository;
@RestController
@RequestMapping("/api/states")
public class StateMstrController {

    @Autowired
    private StateMstrRepository stateRepo;

  @GetMapping("/by-country-code/{countryCode}")
public List<StateMstr> getStatesByCountryCode(@PathVariable("countryCode") String countryCode) {
    return stateRepo.findByCountryCodeFlexible(countryCode);
}


}
