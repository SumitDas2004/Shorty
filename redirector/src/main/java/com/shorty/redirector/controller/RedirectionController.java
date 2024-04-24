package com.shorty.redirector.controller;

import com.shorty.redirector.service.ShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RedirectionController {

    @Autowired
    ShortenerService shortenerService;

    @GetMapping("/{shortUrl}")
    void redirector(@PathVariable String shortUrl, HttpServletResponse reponse) throws IOException {
        reponse.sendRedirect(shortenerService.redirect(shortUrl));
    }
}
