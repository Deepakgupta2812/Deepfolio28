package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Event;
import com.portfolio.deepak.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService eventService;

    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("activePage", "events");
        return "admin/events";
    }

    @GetMapping("/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("activePage", "events");
        return "admin/event-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        model.addAttribute("activePage", "events");
        return "admin/event-form";
    }

    @PostMapping("/save")
    public String saveEvent(
            @ModelAttribute Event event,
            @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Event image must be an image file.");
            }
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Event image must be smaller than 5 MB.");
            }
            event.setImageData("data:" + contentType + ";base64,"
                    + Base64.getEncoder().encodeToString(image.getBytes()));
        } else if (event.getId() != null) {
            event.setImageData(eventService.getEventById(event.getId()).getImageData());
        }
        eventService.saveEvent(event);
        return "redirect:/admin/events";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }
}
