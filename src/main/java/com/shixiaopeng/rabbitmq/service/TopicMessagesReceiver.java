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
@RabbitListener(queues = "topic.messages")
@Slf4j
public class TopicMessagesReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void process(String hello,Channel channel, Message message) throws IOException {
        log.info("message ====={}", message);
        System.out.println("topic.messages  : " + hello);

        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
