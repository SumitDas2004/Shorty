package com.shorty.shortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="url-shortener")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true, length = 1000)
    String longUrl;
    @Column(unique = true)
    String shortUrl;
}
