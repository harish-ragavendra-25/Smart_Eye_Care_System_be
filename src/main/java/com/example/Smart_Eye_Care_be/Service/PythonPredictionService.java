package com.example.Smart_Eye_Care_be.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PythonPredictionService {

    @Value("${python.ml.url}")
    private String pythonMlUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getPrediction(String imageUrl) {

        String api = pythonMlUrl + "?url=" + imageUrl;

        return restTemplate.getForObject(api, Map.class);
    }
}
