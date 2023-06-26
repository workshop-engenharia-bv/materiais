package br.com.bv.workshop.messaging;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class KafkaProducerInterceptor implements ProducerInterceptor<String, SimulacaoVeiculoLeve> {
    @Override
    public ProducerRecord<String, SimulacaoVeiculoLeve> onSend(ProducerRecord<String, SimulacaoVeiculoLeve> producerRecord) {
        producerRecord.headers().add("correlation-id", String.valueOf(producerRecord.value().getCpf()).getBytes());

        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
