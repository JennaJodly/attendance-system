package com.example.attendance.controllers;

import com.example.attendance.service.FingerprintService;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/fingerprint")
public class FingerprintController {

    private final FingerprintService service;

    public FingerprintController(FingerprintService service) {
        this.service = service;
    }

    // Capture fingerprint and return base64 string (supports optional parameters)
    @PostMapping("/capture")
public String captureImageWithParams(
    @RequestParam(defaultValue = "10000") int timeout,
    @RequestParam(defaultValue = "50") int quality,
    @RequestParam(defaultValue = "") String licstr,
    @RequestParam(defaultValue = "ISO") String templateFormat,
    @RequestParam(defaultValue = "0.75") double imageWSQRate
) {
    return service.captureFingerprintAsBase64(timeout, quality, licstr, templateFormat, imageWSQRate);
}

 
    // Optional: Match captured fingerprint with provided base64 (if needed)
    @PostMapping("/match")
    public boolean match(@RequestBody String base64Image) {
        byte[] existingImage = Base64.getDecoder().decode(base64Image);
        byte[] newImage = Base64.getDecoder().decode(service.captureFingerprintAsBase64(10000, 50, "", "ISO", 0.75));
        return service.matchTemplates(existingImage, newImage);
    }
}
