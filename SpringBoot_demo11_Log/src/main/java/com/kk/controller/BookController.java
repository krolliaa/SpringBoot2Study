package com.kk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping(value = "/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public String getBook() {
        logger.debug("debug...");
        logger.info("info...");
        logger.warn("warn...");
        logger.error("error...");
        return "SpringBoot Run...";
    }
}
