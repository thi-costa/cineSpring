package br.com.ada.cineSpring.dao;

import br.com.ada.cineSpring.model.Ator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AtorDAO {
    private static Logger logger = LoggerFactory.getLogger(AtorDAO.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static List<Ator> atores = new ArrayList<>();

    private static int proximoId = 1;

    static {
        try{
            atores = objectMapper.readValue(
                    new File("src/main/java/br/com/ada/cineSpring/database/atores.json"),
                    new TypeReference<>() {
                    });

            logger.info("Arquivo 'atores.json' foi lido!");

            if(atores.size() > 0) proximoId = atores.get( atores.size() - 1 ).getId() + 1;

        } catch (IOException e){
            System.out.println(e.getMessage());

        }
    }

    public void adicionar(Ator ator){
        ator.setId(proximoId++);
        atores.add(ator);

        salvarArquivo();
    }

    public List<Ator> buscarAtoresPorId(List<Integer> atoresIds){
        return atores.stream()
                .filter(ator -> atoresIds.contains(ator.getId()))
                .collect(Collectors.toList());

    }

    public void remover(int id){
        atores.removeIf(ator -> ator.getId() == id);
        salvarArquivo();
    }

    public List<Ator> buscarTodos(){
        return atores;
    }

    public Ator buscarPorId(int id){
        return atores.stream()
                .filter(ator -> ator.getId() == id)
                .findFirst().orElse(null);
    }

    public void atualizar(Ator ator){
        for(int i = 0; i < atores.size(); i++){
            Ator a = atores.get(i);

            if(a.getId() == ator.getId()) atores.set(i, ator);
        }
    }

    public void salvarArquivo(){
        try {
            objectMapper.writeValue(
                    new File("src/main/java/br/com/ada/cineSpring/database/atores.json"),
                    atores);
        } catch (IOException e){
            logger.error(e.getMessage());
        }
    }
}
