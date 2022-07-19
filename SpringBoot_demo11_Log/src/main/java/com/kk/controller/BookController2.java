package com.kk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
@Slf4j
public class BookController2 extends BaseLog {

    @GetMapping
    public String getBook() {
        log.debug("debug...");
        log.info("info...");
        log.warn("warn...");
        log.error("error...");
        return "SpringBoot Run...";
    }
}
