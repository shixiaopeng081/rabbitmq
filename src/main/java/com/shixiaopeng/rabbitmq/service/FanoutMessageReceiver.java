package com.shixiaopeng.rabbitmq.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by shixi  on 2019/6/6
 */
@Component

@Slf4j
public class FanoutMessageReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "fanout.A")
    @RabbitHandler
    public void fanoutA(String hello,Channel channel, Message message) throws IOException {
        System.out.println("fanout.A  : " + hello);

        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = "fanout.B")
    @RabbitHandler
    public void fanoutB(String hello,Channel channel, Message message) throws IOException {
        System.out.println("fanout.B  : " + hello);

        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = "fanout.C")
    @RabbitHandler
    public void fanoutC(String hello,Channel channel, Message message) throws IOException {
        System.out.println("fanout.C  : " + hello);

        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
