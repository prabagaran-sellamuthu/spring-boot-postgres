package com.tamil.JsonH2DB.controller;

import com.tamil.JsonH2DB.entity.ApplicationUser;
import com.tamil.JsonH2DB.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/data")
    public ApplicationUser saveData(@RequestBody ApplicationUser data) {
        return dataService.saveData(data);
    }

    @PutMapping("/update/{id}")
    public ApplicationUser updateUserBooleans(@PathVariable Long id, @RequestBody Map<String, Map<String, Boolean>> requestBody) {
        Map<String, Boolean> additionalInfo = requestBody.get("additionalInfo");
        return dataService.updateUserBooleans(id, additionalInfo);
    }
}