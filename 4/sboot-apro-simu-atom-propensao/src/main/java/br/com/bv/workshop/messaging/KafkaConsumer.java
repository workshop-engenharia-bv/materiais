package br.com.bv.workshop.messaging;

import br.com.bv.workshop.Propensao;
import br.com.bv.workshop.service.PropensaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    private final PropensaoService service;

    @Autowired
    public KafkaConsumer(PropensaoService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${topic.input}")
    public void consume(ConsumerRecord<String, Propensao> message) {
        log.info(String.format("Message received -> %s",message.value().getCpf()));
        service.salvaPropensao(message.value());
    }
}