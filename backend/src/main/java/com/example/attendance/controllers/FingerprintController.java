
package com.example.attendance.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fingerprint")
public class FingerprintController {

    @GetMapping("/scan")
    public String scanFingerprint() {
        // This should be replaced with real MFS100 integration
        return "MockFingerprint123";
    }
}
