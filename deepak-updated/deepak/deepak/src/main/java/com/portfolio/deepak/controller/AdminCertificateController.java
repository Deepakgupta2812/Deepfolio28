package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Certificate;
import com.portfolio.deepak.service.CertificateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/admin/certificates")
public class AdminCertificateController {

    private final CertificateService certificateService;

    public AdminCertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    // Show all certificates
    @GetMapping
    public String getAllCertificates(Model model) {

        model.addAttribute(
                "certificates",
                certificateService.getAllCertificates()

        );
        model.addAttribute("activePage", "certificates");
        return "admin/certificates";
    }

    // Show Add Certificate form
    @GetMapping("/add")
    public String showAddCertificateForm(Model model) {

        model.addAttribute("certificate", new Certificate());

        return "admin/certificate-form";
    }

    // Save new or updated certificate
    @PostMapping("/save")
    public String saveCertificate(
            @ModelAttribute Certificate certificate,
            @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {

        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Certificate image must be an image file.");
            }
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new IllegalArgumentException("Certificate image must be smaller than 5 MB.");
            }

            certificate.setImageData(
                    "data:" + contentType + ";base64,"
                            + Base64.getEncoder().encodeToString(image.getBytes())
            );
        } else if (certificate.getId() != null) {
            Certificate existing = certificateService.getCertificateById(certificate.getId());
            certificate.setImageData(existing.getImageData());
        }

        certificateService.saveCertificate(certificate);

        return "redirect:/admin/certificates";
    }

    // Show Edit Certificate form
    @GetMapping("/edit/{id}")
    public String showEditCertificateForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "certificate",
                certificateService.getCertificateById(id)
        );

        return "admin/certificate-form";
    }

    // Delete Certificate
    @PostMapping("/delete/{id}")
    public String deleteCertificate(@PathVariable Long id) {

        certificateService.deleteCertificate(id);

        return "redirect:/admin/certificates";
    }
}