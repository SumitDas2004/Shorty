package com.shorty.shortener.dao;

import com.shorty.shortener.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLDao extends JpaRepository<URL, Integer> {
   Optional<URL> findByLongUrl(String longUrl);
   Optional<URL> findByShortUrl(String longUrl);
}
