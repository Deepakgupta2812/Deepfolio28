package com.portfolio.deepak.controller;

import com.portfolio.deepak.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/messages")
public class AdminContactController {

    private final ContactService contactService;

    public AdminContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // Show all messages
    @GetMapping
    public String getAllMessages(Model model) {

        model.addAttribute(
                "contacts",
                contactService.getAllContacts()
        );
        model.addAttribute("activePage", "messages");
        return "admin/messages";
    }

    // View one message
    @GetMapping("/{id}")
    public String viewMessage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "contact",
                contactService.getContactById(id)
        );

        return "admin/message-details";
    }

    // Delete message
    @PostMapping("/delete/{id}")
    public String deleteMessage(
            @PathVariable Long id) {

        contactService.deleteContact(id);

        return "redirect:/admin/messages";
    }
}