package com.spring.todolist.controller;


import com.spring.todolist.model.Tarefas;
import com.spring.todolist.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    public List<Tarefas> getTodasTarefas(){
        return tarefaRepository.findAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Tarefas> getTarefaById(@PathVariable Long id){
        Optional<Tarefas> tarefas = tarefaRepository.findById(id);
        return tarefas.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public  ResponseEntity<Tarefas> criarTarefa (@RequestBody Tarefas tarefas){
        Tarefas tarefaSalva = tarefaRepository.save(tarefas);
        return  ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefas> atualizarTarefa(@PathVariable Long id,@RequestBody Tarefas tarefaInformacao){
        Optional<Tarefas> tarefas = tarefaRepository.findById(id);

        if (tarefas.isPresent()){
            Tarefas encontrado = tarefas.get();
            encontrado.setTitulo(tarefaInformacao.getTitulo());
            encontrado.setDescricao(tarefaInformacao.getDescricao());
            encontrado.setCompleto(tarefaInformacao.isCompleto());

            Tarefas tarefaAtualizada = tarefaRepository.save(encontrado);
            return ResponseEntity.ok(tarefaAtualizada);

        } else{
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> apagarTarefa(@PathVariable Long id){
        Optional<Tarefas> tarefas = tarefaRepository.findById(id);

        if (tarefas.isPresent()){
           tarefaRepository.delete(tarefas.get());
           return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
