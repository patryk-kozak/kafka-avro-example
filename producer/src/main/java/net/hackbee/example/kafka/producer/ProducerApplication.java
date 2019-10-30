package net.hackbee.example.kafka.producer;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import net.hackbee.kafka.example.schema.Order;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaAdmin;

@SpringBootApplication
public class ProducerApplication {

    private final KafkaProducer kafkaProducer;

    public ProducerApplication(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartupComplete() {
        IntStream.range(0, 10)
            .forEach(iteration -> kafkaProducer.send(prepareOrder(iteration)));
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("order", 1, (short) 1);
    }

    private Order prepareOrder(int iteration) {
        return Order.newBuilder()
            .setCorrelationId(String.format("correlationId%d", iteration))
            .setCreatedDate(String.valueOf(Instant.now()))
            .setOptionalField("This field contains some data")
            .build();
    }

}
