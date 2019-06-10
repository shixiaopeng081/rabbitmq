package com.shixiaopeng.rabbitmq.web;

import com.shixiaopeng.rabbitmq.service.HelloSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shixi  on 2019/6/6
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class RabbitMqHelloController {

    @Autowired
    private HelloSender helloSender;

    @GetMapping("/hello")
    public void hello() throws Exception {
        helloSender.send();
    }


    @GetMapping("/topic")
    public void topic() throws Exception {
        helloSender.sendMessage();
        helloSender.sendMessages();
    }

    @GetMapping("/fanout")
    public void fanout() throws Exception {
        helloSender.sendFanout();
    }
}
