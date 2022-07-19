package com.kk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Value(value = "${a}")
    private Integer price;

    @GetMapping
    public String getById() {
        System.out.println("SpringBoot Run...");
        return "SpringBoot Run..." + price;
    }
}