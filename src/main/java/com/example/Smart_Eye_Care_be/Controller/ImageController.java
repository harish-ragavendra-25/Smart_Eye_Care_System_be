package com.example.Smart_Eye_Care_be.Controller;
import com.example.Smart_Eye_Care_be.Service.CloudinaryService;
import com.example.Smart_Eye_Care_be.Service.PythonPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private PythonPredictionService pythonPredictionService;

    @PostMapping("/upload-and-predict")
    public ResponseEntity<?> uploadAndPredict(@RequestPart("file") MultipartFile file) {

        try {
            // Step 1 — Upload to Cloudinary
            String imageUrl = cloudinaryService.upload(file);

            // Step 2 — Send Cloudinary URL to Python ML
            Map<String, Object> prediction = pythonPredictionService.getPrediction(imageUrl);

            // Step 3 — Build final response
            Map<String, Object> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            response.put("prediction", prediction);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
