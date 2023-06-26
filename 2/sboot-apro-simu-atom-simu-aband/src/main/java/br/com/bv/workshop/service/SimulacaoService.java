package br.com.bv.workshop.service;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import br.com.bv.workshop.messaging.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class SimulacaoService {
    private final KafkaProducer producer;

    @Autowired
    public SimulacaoService(KafkaProducer producer) {
        this.producer = producer;
    }

    public void filtraSimulacaoAbandonada(SimulacaoVeiculoLeve simulacao) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.minus(10, ChronoUnit.MINUTES);
        LocalDateTime data = simulacao.getData();
        if (data.isBefore(limite)) {
            log.warn("Simulacao abandonada -> {}", simulacao);
            log.info("Sending message -> {}", simulacao);
            producer.sendMessage(String.valueOf(simulacao.getCpf()), simulacao);
            log.info("Message sent -> {}", simulacao);
        }
    }
}
