package com.kk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    @GetMapping
    public String getById() {
        System.out.println("SpringBoot Run 04...");
        return "SpringBoot Run 04...";
    }
}