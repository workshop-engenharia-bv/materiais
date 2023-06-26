package br.com.bv.workshop.service;

import br.com.bv.workshop.Propensao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
public class PropensaoService {
    private static final String SEPARATOR = ";";

    public void salvaPropensao(Propensao propensao) {
        String filePath = String.format("csv/%s.csv", propensao.getCpf());

        try (FileWriter fw = new FileWriter(filePath, true)) {
            StringBuffer sb = new StringBuffer();
            sb.append(System.lineSeparator());
            sb.append(propensao.getCpf());
            sb.append(SEPARATOR);
            sb.append(propensao.getScore());
            sb.append(SEPARATOR);
            sb.append(propensao.getGrupo());

            fw.append(sb.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
