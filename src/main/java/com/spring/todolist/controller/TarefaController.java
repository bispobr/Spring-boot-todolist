package com.spring.todolist.controller;


import com.spring.todolist.dto.TarefaRequisicaoDto;
import com.spring.todolist.dto.TarefaRespostaDto;
import com.spring.todolist.model.Tarefas;
import com.spring.todolist.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    @Operation(description = "Endpoint responsável por listar todos as tarefas")
    @ApiResponse(responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<TarefaRespostaDto>> getTodasTarefas(){
        log.info("Solicitação de Listagem de todas as tarefas recebida");
        return  ResponseEntity.ok().body(tarefaService.listarTodasTarefas());
    }

    @GetMapping ("/{id}")
    @Operation(description = "Endpoint responsável por listar tarefa por id")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Tarefas> getTarefaById(@PathVariable Long id){
        log.info("solicitação para listar tarefas por id recebida");
        return tarefaService.buscarTarefaPorId(id);
    }

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novas tarefas")
    @ApiResponse (responseCode = "201", description = "Cadastro bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<TarefaRespostaDto> criarTarefa (@Valid  @RequestBody TarefaRequisicaoDto tarefas){
        log.info("solicitação para criar tarefas  recebida");
        return  ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.cadastarNovaTarefa(tarefas));
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por Atualizar tarefa")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<TarefaRespostaDto> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TarefaRequisicaoDto tarefaInformacao){
        log.info("solicitação para atualizar tarefas por id recebida ");
        return tarefaService.atualizarTarefa(id,tarefaInformacao);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint responsável por remover tarefa")
    @ApiResponse (responseCode = "204", description = "remoção bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<Object> apagarTarefa(@PathVariable Long id){
        log.info("solicitação para remover tarefa  recebida " );
        return  tarefaService.removerTarefa(id);
    }
}
