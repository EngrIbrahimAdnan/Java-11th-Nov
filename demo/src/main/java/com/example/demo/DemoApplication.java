package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class DemoApplication {
    private List<Contacts> allContacts =  new ArrayList();
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // wrapper used to convert object into string
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RestController
    public class restClass {
        @PostMapping("/addContact")
        String addContact(@RequestBody Contacts newContact) {
            for (Contacts contact: allContacts){
                if (contact.getEmail().equals(newContact.getEmail())){
                    return "Contact already exists with this email!";
                }
            }
            allContacts.add(newContact);
            return "Contact added successfully!";
        }

        @GetMapping("/getContactDetails")
        String getContact (@RequestParam(required = false) String name) throws JsonProcessingException {
            for(Contacts foundContact: allContacts){
                if (foundContact.getName().equals(name)){
                    // converts "foundContact" object into string
                    return objectMapper.writeValueAsString(foundContact);
                }
            }
            return "Contact not found";
        }
    }
}


