package com.kk.controller;

import com.kk.pojo.SimCard;
import com.kk.service.SimCardService;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/sms")
public class SimCardController {

    @Autowired
    private SimCardService simCardService;


    @GetMapping
    public String getCode(String telephone) throws InterruptedException, TimeoutException, MemcachedException {
        return simCardService.sendCode(telephone);
    }

    @PostMapping
    public Boolean checkCode(SimCard simCard) throws InterruptedException, TimeoutException, MemcachedException {
        return simCardService.checkCode(simCard);
    }
}
