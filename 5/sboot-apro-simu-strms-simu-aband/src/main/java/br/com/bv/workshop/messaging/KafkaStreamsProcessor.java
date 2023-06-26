package br.com.bv.workshop.messaging;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class KafkaStreamsProcessor {
    private final String input;
    private final String output;

    @Autowired
    public KafkaStreamsProcessor(@Value("${topic.input}") String input, @Value("${topic.output}") String output) {
        this.input = input;
        this.output = output;
    }

    @Bean
    KStream<String, SimulacaoVeiculoLeve> filtraSimulacaoAbandonada(StreamsBuilder sb) {
        KStream<String, SimulacaoVeiculoLeve> stream = sb.stream(input);

        stream.filter((key,value) ->
        {
            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime limite = agora.minus(10, ChronoUnit.MINUTES);
            LocalDateTime data = value.getData();

            return data.isBefore(limite);
        }).to(output);

        return stream;
    }
}
