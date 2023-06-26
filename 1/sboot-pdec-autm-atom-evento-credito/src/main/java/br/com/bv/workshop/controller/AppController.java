package br.com.bv.workshop.controller;

import br.com.bv.workshop.SimulacaoVeiculoLeve;
import br.com.bv.workshop.Veiculo;
import br.com.bv.workshop.messaging.KafkaProducer;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class AppController {
    private final KafkaProducer producer;

    @Autowired
    public AppController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/post")
    public ResponseEntity<Void> post(@RequestBody String input) throws IOException {
        Decoder decoder = DecoderFactory.get().jsonDecoder(SimulacaoVeiculoLeve.getClassSchema(), input);
        DatumReader<SimulacaoVeiculoLeve> datumReader = new SpecificDatumReader<>(SimulacaoVeiculoLeve.class);
        SimulacaoVeiculoLeve simulacao = datumReader.read(null, decoder);

        producer.sendMessage(simulacao.getCpf().toString(), simulacao);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get")
    public ResponseEntity<Void> get(String cpf, String data) {
        Veiculo veiculo = Veiculo.newBuilder()
                .setAno(2019)
                .setMarca("Citroen")
                .setModelo("C3")
                .setValor(BigDecimal.valueOf(53000))
                .build();

        SimulacaoVeiculoLeve simulacao = SimulacaoVeiculoLeve.newBuilder()
                .setCpf(cpf)
                .setData(LocalDateTime.parse(data))
                .setParcelas(10)
                .setEntrada(BigDecimal.valueOf(10000))
                .setVeiculo(veiculo)
                .build();

        producer.sendMessage(simulacao.getCpf().toString(), simulacao);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
