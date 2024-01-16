package com.runthebank.usecases;

import com.runthebank.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class Notification {

    private final RestTemplate restTemplate;

    public void sendNotification(String  email, String message){
        ResponseEntity<Map> response  = restTemplate.getForEntity("https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3", Map.class);

    }
}
