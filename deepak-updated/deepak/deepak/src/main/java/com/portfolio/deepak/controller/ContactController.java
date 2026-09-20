package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Contact;
import com.portfolio.deepak.service.ContactService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private static final String CONTACT_ATTRIBUTE = "contact";

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public String submitContact(
            @Valid @ModelAttribute(CONTACT_ATTRIBUTE) Contact contact,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Previously this returned the "index" view directly, which
            // rendered the home page without any of its model data.
            // Flashing the errors back and redirecting keeps the page intact.
            redirectAttributes.addFlashAttribute(
                    BindingResult.MODEL_KEY_PREFIX + CONTACT_ATTRIBUTE, bindingResult);
            redirectAttributes.addFlashAttribute(CONTACT_ATTRIBUTE, contact);
            redirectAttributes.addFlashAttribute("contactError", true);
            return "redirect:/#contact";
        }

        contactService.saveContact(contact);
        redirectAttributes.addFlashAttribute("contactSuccess", true);

        return "redirect:/#contact";
    }
}
