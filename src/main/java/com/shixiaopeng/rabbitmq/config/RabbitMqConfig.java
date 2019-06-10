package com.shixiaopeng.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@author shixiaopeng
 */

@SuppressWarnings("unused")
@Slf4j
@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }



    @Configuration
    public static class TopicRabbitConfig {

        final static String message = "topic.message";
        final static String messages = "topic.messages";

        @Bean
        public Queue queueMessage() {
            return new Queue(TopicRabbitConfig.message);
        }

        @Bean
        public Queue queueMessages() {
            return new Queue(TopicRabbitConfig.messages);
        }

        @Bean
        TopicExchange exchange() {
            return new TopicExchange("TEST.TOPIC");
        }

        @Bean
        Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
            return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
        }

        @Bean
        Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
            return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
        }
    }


    @Configuration
    public static class FanoutRabbitConfig {

        @Bean
        public Queue AMessage() {
            return new Queue("fanout.A");
        }

        @Bean
        public Queue BMessage() {
            return new Queue("fanout.B");
        }

        @Bean
        public Queue CMessage() {
            return new Queue("fanout.C");
        }

        @Bean
        FanoutExchange fanoutExchange() {
            return new FanoutExchange("fanoutExchange");
        }

        @Bean
        Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
            return BindingBuilder.bind(AMessage).to(fanoutExchange);
        }

        @Bean
        Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
            return BindingBuilder.bind(BMessage).to(fanoutExchange);
        }

        @Bean
        Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
            return BindingBuilder.bind(CMessage).to(fanoutExchange);
        }

    }

}