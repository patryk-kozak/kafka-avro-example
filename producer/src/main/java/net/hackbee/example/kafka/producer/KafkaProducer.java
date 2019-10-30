package net.hackbee.example.kafka.producer;

import net.hackbee.kafka.example.schema.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private KafkaTemplate<String, Order> orderKafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Order> orderKafkaTemplate) {
        this.orderKafkaTemplate = orderKafkaTemplate;
    }

    public void send(Order order) {
        orderKafkaTemplate.send("order", order);
    }

}
