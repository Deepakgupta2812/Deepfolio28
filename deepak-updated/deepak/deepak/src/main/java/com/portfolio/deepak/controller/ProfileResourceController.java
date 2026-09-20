package com.portfolio.deepak.controller;

import com.portfolio.deepak.entity.Certificate;
import com.portfolio.deepak.entity.Event;
import com.portfolio.deepak.entity.Profile;
import com.portfolio.deepak.entity.Project;
import com.portfolio.deepak.service.CertificateService;
import com.portfolio.deepak.service.EventService;
import com.portfolio.deepak.service.ProfileService;
import com.portfolio.deepak.service.ProjectService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class ProfileResourceController {

    private final ProfileService profileService;
    private final CertificateService certificateService;
    private final EventService eventService;
    private final ProjectService projectService;

    public ProfileResourceController(ProfileService profileService,
                                     CertificateService certificateService,
                                     EventService eventService,
                                     ProjectService projectService) {
        this.profileService = profileService;
        this.certificateService = certificateService;
        this.eventService = eventService;
        this.projectService = projectService;
    }

    /** Opens the resume in the browser's PDF viewer. */
    @GetMapping("/resume")
    public ResponseEntity<ByteArrayResource> resume() {
        return resumeResponse("inline");
    }

    /** Forces a file download of the same resume. */
    @GetMapping("/resume/download")
    public ResponseEntity<ByteArrayResource> resumeDownload() {
        return resumeResponse("attachment");
    }

    private ResponseEntity<ByteArrayResource> resumeResponse(String disposition) {

        Profile profile = profileService.getProfile();

        if (profile.getResumeData() == null) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType = MediaType.parseMediaType(
                profile.getResumeContentType() == null
                        ? MediaType.APPLICATION_PDF_VALUE
                        : profile.getResumeContentType());

        String fileName = profile.getResumeFileName() == null
                ? "resume.pdf"
                : profile.getResumeFileName();

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        disposition + "; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(profile.getResumeData()));
    }

    @GetMapping("/image/profile")
    public ResponseEntity<byte[]> profileImage() {
        Profile profile = profileService.getProfile();
        return serveBase64Image(profile.getImageData());
    }

    @GetMapping("/image/certificate/{id}")
    public ResponseEntity<byte[]> certificateImage(@PathVariable Long id) {
        Certificate certificate = certificateService.getCertificateById(id);
        return serveBase64Image(certificate.getImageData());
    }

    @GetMapping("/image/event/{id}")
    public ResponseEntity<byte[]> eventImage(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return serveBase64Image(event.getImageData());
    }

    /**
     * Serves a project screenshot. Kept on the same endpoint style as the
     * existing certificate/event images so the markup can lazy-load them
     * instead of inlining huge base64 strings into the HTML.
     */
    @GetMapping("/image/project/{id}")
    public ResponseEntity<byte[]> projectImage(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return serveBase64Image(project.getImageData());
    }

    private ResponseEntity<byte[]> serveBase64Image(String dataUri) {
        if (dataUri == null || dataUri.isBlank()) {
            return ResponseEntity.notFound().build();
        }
        try {
            // format: data:<mediaType>;base64,<data>
            String meta = dataUri.substring(5, dataUri.indexOf(','));
            String mediaType = meta.replace(";base64", "");
            byte[] bytes = Base64.getDecoder().decode(dataUri.substring(dataUri.indexOf(',') + 1));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
