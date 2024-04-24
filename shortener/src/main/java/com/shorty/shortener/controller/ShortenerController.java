package com.shorty.shortener.controller;

import com.shorty.shortener.model.ShortenRequestModel;
import com.shorty.shortener.service.ShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ShortenerController {
    @Autowired
    ShortenerService shortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody ShortenRequestModel req){
        try {
            String res = shortenerService.shorten(req.getUrl());
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("shortURL", res);

            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("Error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{path}")
    public String redirect(@PathVariable String path) throws IOException {
        return shortenerService.redirect(path);
    }
}
