package br.com.bv.workshop.messaging;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import br.com.bv.workshop.service.SimulacaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    private final SimulacaoService service;

    @Autowired
    public KafkaConsumer(SimulacaoService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${topic.input}")
    public void consume(ConsumerRecord<String, SimulacaoVeiculoLeve> message) {
        log.info(String.format("Message received -> %s",message.value().getCpf()));
        service.filtraSimulacaoAbandonada(message.value());
    }
}