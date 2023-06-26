package br.com.bv.workshop.messaging;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {
    private final String output;
    private final KafkaTemplate<String, SimulacaoVeiculoLeve> kafkaTemplate;

    @Autowired
    public KafkaProducer(@Value("${topic.output}") String output, KafkaTemplate<String, SimulacaoVeiculoLeve> kafkaTemplate) {
        this.output = output;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String key, SimulacaoVeiculoLeve value) {
        log.info("Sending message -> {}", value);
        ProducerRecord<String, SimulacaoVeiculoLeve> record = new ProducerRecord(output, null, key, value);
        kafkaTemplate.send(record);
        log.info("Message sent -> {}", value);
    }
}
