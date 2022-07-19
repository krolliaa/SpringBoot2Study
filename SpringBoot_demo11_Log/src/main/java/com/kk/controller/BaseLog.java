package com.kk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseLog {
    public static Logger logger;

    public BaseLog() {
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
