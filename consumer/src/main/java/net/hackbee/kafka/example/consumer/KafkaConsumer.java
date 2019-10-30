package net.hackbee.kafka.example.consumer;

import net.hackbee.kafka.example.schema.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(id = "order-consumer", topics = "order")
    public void consumeMessage(Order order) {
        System.out.println("Got message: " + order);
    }

}
