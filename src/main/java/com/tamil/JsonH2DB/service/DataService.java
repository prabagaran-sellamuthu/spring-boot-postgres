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

    public ApplicationUser saveData(ApplicationUser data) {
        return dataRepository.save(data);
    }

    public ApplicationUser updateUserBooleans(Long id, Map<String, Boolean> updatedBooleans) {
        Optional<ApplicationUser> optionalUser = dataRepository.findById(id);
        if (optionalUser.isPresent()) {
            ApplicationUser user = optionalUser.get();
            Map<String, Object> additionalInfo = user.getAdditionalInfo();

            if (additionalInfo != null) {
                for (Map.Entry<String, Boolean> entry : updatedBooleans.entrySet()) {
                    additionalInfo.put(entry.getKey(), entry.getValue());
                }
                user.setAdditionalInfo(additionalInfo);
            } else {
                throw new RuntimeException("Additional info is missing.");
            }

            return dataRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}