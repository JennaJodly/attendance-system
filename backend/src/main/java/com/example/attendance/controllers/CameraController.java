
package com.example.attendance.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/camera")
public class CameraController {

    @PostMapping("/photo")
    public String uploadPhoto(@RequestBody String base64Image) {
        // Decode and save image to disk or DB
        return "Photo saved";
    }
}
