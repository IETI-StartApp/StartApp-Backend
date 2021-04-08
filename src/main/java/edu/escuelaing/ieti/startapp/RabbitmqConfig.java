package edu.escuelaing.ieti.startapp;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitmqConfig {

    public static final String QUEUE_NAME = "Tickets";
    public static final String QUEUE_EXCHANGE = "Exchange";
    public static final String QUEUE_ROUTING_KEY = "RoutingKey";

    @Bean
    public Queue queue(){

        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange exchange(){

        return new TopicExchange(QUEUE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_ROUTING_KEY);
    }
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


}
