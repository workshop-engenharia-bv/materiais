package br.com.bv.workshop.messaging;

import br.com.bv.workshop.Propensao;
import br.com.bv.workshop.SimulacaoVeiculoLeve;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class KafkaProducerInterceptor implements ProducerInterceptor<String, Propensao> {
    @Override
    public ProducerRecord<String, Propensao> onSend(ProducerRecord<String, Propensao> producerRecord) {
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
