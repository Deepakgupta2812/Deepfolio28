package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Contact;
import com.portfolio.deepak.service.ContactService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public String submitContact(
            @Valid @ModelAttribute("contact") Contact contact,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        contactService.saveContact(contact);

        return "redirect:/?success";
    }
}