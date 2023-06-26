package br.com.bv.workshop.messaging;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

@Slf4j
public class KafkaConsumerInterceptor implements ConsumerInterceptor<String, SimulacaoVeiculoLeve> {
    @Override
    public ConsumerRecords<String, SimulacaoVeiculoLeve> onConsume(ConsumerRecords<String, SimulacaoVeiculoLeve> consumerRecords) {
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
