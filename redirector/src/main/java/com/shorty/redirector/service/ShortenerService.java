package com.shorty.redirector.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("SHORTENER")
public interface ShortenerService {
    @GetMapping("/{shortUrl}")
    String redirect(@PathVariable String shortUrl);
}
