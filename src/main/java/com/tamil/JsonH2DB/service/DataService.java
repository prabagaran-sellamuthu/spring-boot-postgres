package com.tamil.JsonH2DB.service;

import com.tamil.JsonH2DB.entity.ApplicationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tamil.JsonH2DB.repository.DataRepository;

import java.util.*;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    // public ApplicationUser saveData(ApplicationUser data) {
    //     return dataRepository.save(data);
    // }

    public ApplicationUser saveData(Map<String, Object> requestBody) {
        ApplicationUser user = new ApplicationUser();
        user.setAdditionalInfo(requestBody);
        return dataRepository.save(user);
    }
    public ApplicationUser updateUserBooleans(Map<String, Object> requestBody) {
        Long id = ((Number) requestBody.get("id")).longValue();
        Optional<ApplicationUser> optionalUser = dataRepository.findById(id);
        if (optionalUser.isPresent()) {
            ApplicationUser user = optionalUser.get();
            Map<String, Object> existingDetails = user.getAdditionalInfo();
            Map<String, Object> updates = (Map<String, Object>) requestBody.get("additionalInfo");

            if (existingDetails != null) {
                mergeBooleans(existingDetails, updates);
                user.setAdditionalInfo(existingDetails);
            } else {
                throw new RuntimeException("Details field is missing.");
            }

            return dataRepository.save(user);
        } else {
            throw new RuntimeException("ApplicationUser not found");
        }
    }

    // private void mergeBooleans(Map<String, Object> original, Map<String, Object> updates) {
    //     for (Map.Entry<String, Object> entry : updates.entrySet()) {
    //         String key = entry.getKey();
    //         Object value = entry.getValue();

    //         if (value instanceof Map) {
    //             if (original.containsKey(key) && original.get(key) instanceof Map) {
    //                 mergeBooleans((Map<String, Object>) original.get(key), (Map<String, Object>) value);
    //             } else {
    //                 original.put(key, value);
    //             }
    //         } else if (value instanceof Boolean) {
    //             original.put(key, value);
    //         }
    //     }
    // }

    // public ApplicationUser updateUserDetails(Long id, Map<String, Object> requestBody) {
    //     Optional<ApplicationUser> optionalUser = dataRepository.findById(id);
    //     if (optionalUser.isPresent()) {
    //         ApplicationUser user = optionalUser.get();
    //         Map<String, Object> existingDetails = user.getAdditionalInfo();

    //         if (existingDetails != null) {
    //             mergeBooleans(existingDetails, requestBody);
    //             user.setAdditionalInfo(existingDetails);
    //         } else {
    //             throw new RuntimeException("Details field is missing.");
    //         }

    //         return dataRepository.save(user);
    //     } else {
    //         throw new RuntimeException("ApplicationUser not found");
    //     }
    // }

    private void mergeBooleans(Map<String, Object> original, Map<String, Object> updates) {
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                if (original.containsKey(key) && original.get(key) instanceof Map) {
                    mergeBooleans((Map<String, Object>) original.get(key), (Map<String, Object>) value);
                } else {
                    original.put(key, value);
                }
            } else if (value instanceof Boolean) {
                original.put(key, value);
            }
        }
    }

}