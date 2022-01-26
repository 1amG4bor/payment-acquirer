package com.g4bor.payment.acquirer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String getTestText() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = dateFormatter.format(new Date());
        return "Test-text - generated: " + now;
    }

}
