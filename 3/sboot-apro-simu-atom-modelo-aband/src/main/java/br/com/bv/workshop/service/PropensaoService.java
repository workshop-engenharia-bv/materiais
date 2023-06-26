package br.com.bv.workshop.service;

import br.com.bv.workshop.Propensao;
import br.com.bv.workshop.SimulacaoVeiculoLeve;
import br.com.bv.workshop.messaging.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
@Service
public class PropensaoService {
    private final KafkaProducer producer;

    @Autowired
    public PropensaoService(KafkaProducer producer) {
        this.producer = producer;
    }

    public void calculaPropensao(SimulacaoVeiculoLeve simulacao) {
        Random random = new Random();
        Integer score = random.nextInt();
        Integer grupo = random.nextInt();

        Propensao propensao = Propensao.newBuilder()
                .setCpf(simulacao.getCpf())
                .setScore(BigDecimal.valueOf(score))
                .setGrupo(grupo)
                .build();

        log.info("Sending message -> {}", propensao);
        producer.sendMessage(String.valueOf(propensao.getCpf()), propensao);
        log.info("Message sent -> {}", simulacao);
    }
}
