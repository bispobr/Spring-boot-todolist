package com.spring.todolist.service;

import com.spring.todolist.dto.TarefaRequisicaoDto;
import com.spring.todolist.dto.TarefaRespostaDto;
import com.spring.todolist.mapper.TarefaMapper;
import com.spring.todolist.model.Tarefas;
import com.spring.todolist.repository.TarefaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaMapper mapper;

    public List<TarefaRespostaDto> listarTodasTarefas (){
        log.info("Listando todas as tarefas");
        List<Tarefas> ListaTarefas = tarefaRepository.findAll();
        return mapper.toResponseList(ListaTarefas) ;
    }

    @Cacheable(value = "tarefas", key = "#id")
    public ResponseEntity<Tarefas> buscarTarefaPorId(Long id){
        Optional<Tarefas> tarefas = tarefaRepository.findById(id);
        log.info("listando tarefa id: " + id);
        return tarefas.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    public TarefaRespostaDto cadastarNovaTarefa (TarefaRequisicaoDto tarefas){
        Tarefas novaTarefa = mapper.paraTarefa(tarefas);
        Tarefas tarefaSalva = tarefaRepository.save(novaTarefa);
        log.info("Nova Tarefa adicinada");
        return mapper.paraDTOResposta(tarefaSalva);

    }

    @CachePut(value = "tarefas", key = "#id")
    public ResponseEntity<TarefaRespostaDto> atualizarTarefa(Long id, TarefaRequisicaoDto tarefaInformacao){

        Optional<Tarefas> tarefas = tarefaRepository.findById(id);
        log.info("Buscando tarefa id: " + id );
        if (tarefas.isPresent()){
            Tarefas encontrado = tarefas.get();
            encontrado.setTitulo(tarefaInformacao.titulo());
            encontrado.setDescricao(tarefaInformacao.descricao());
            encontrado.setCompleto(tarefaInformacao.completo());

            Tarefas tarefaAtualizada = tarefaRepository.save(encontrado);
            log.info("tarefas atualizada");
            return ResponseEntity.ok(mapper.paraDTOResposta(tarefaAtualizada));

        } else{
            log.info("Tarefa não encontrada");
            return  ResponseEntity.notFound().build();
        }

    }

    @CacheEvict(value = "tarefas", key = "#id")
    public ResponseEntity<Object> removerTarefa(Long id){
        log.info("Buscando tarefa id: " + id );
        Optional<Tarefas> tarefas = tarefaRepository.findById(id);

        if (tarefas.isPresent()){
            tarefaRepository.delete(tarefas.get());
            log.info("tarefas removida com sucesso");
            return ResponseEntity.noContent().build();
        } else {
            log.info("Tarefa não encontrada");
            return ResponseEntity.notFound().build();
        }

    }
}
