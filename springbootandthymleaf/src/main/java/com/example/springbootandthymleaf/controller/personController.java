package com.example.springbootandthymleaf.controller;

import com.example.springbootandthymleaf.form.personForm;
import com.example.springbootandthymleaf.model.person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.List;

@Controller

public class personController {

    private static List<person> persons = new ArrayList<person>();

    static {
        persons.add(new person("Bill", "Gates"));
        persons.add(new person("Steve", "Jobs"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/","/index"})
    public String index(Model model) {

        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/personList")
    public String listPerson(Model model) {

        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/addPerson")
    public String personForm(Model model) {

        personForm personForm = new personForm();
        model.addAttribute("personForm", personForm);
        return "addPerson";
    }

    @PostMapping("/addPerson")
    public String addPerson(Model model, @ModelAttribute("personForm") personForm personForm) {

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();
        if((firstName != null && firstName.length() > 1) && (lastName != null && lastName.length() > 2)) {
            person person = new person(firstName, lastName);
            persons.add(person);
            return "redirect:/personList";
        } else {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "addPerson";
    }
}
