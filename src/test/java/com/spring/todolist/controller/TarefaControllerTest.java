package com.spring.todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todolist.dto.TarefaRequisicaoDto;
import com.spring.todolist.dto.TarefaRespostaDto;
import com.spring.todolist.model.Tarefas;
import com.spring.todolist.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(tarefaController).build();
    }

    @Test
    public void getTodasTarefas_DeveRetornarListaComStatus200() throws Exception {
        List<TarefaRespostaDto> tarefas = List.of(
                new TarefaRespostaDto(1L, "Teste", "Descricao", false)
        );

        when(tarefaService.listarTodasTarefas()).thenReturn(tarefas);

        mockMvc.perform(get("/api/tarefas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Teste"));

        verify(tarefaService,times(1)).listarTodasTarefas();
    }

    @Test
    public void getTarefaById_TarefaExiste_DeveRetornar200() throws Exception {
        Long id= 1L;
        Tarefas tarefa = new Tarefas(id, "Teste2", "Descricao", false);

        when(tarefaService.buscarTarefaPorId(id)).thenReturn(ResponseEntity.ok(tarefa));

        mockMvc.perform(get("/api/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titulo").value("Teste2"));

        verify(tarefaService,times(1)).buscarTarefaPorId(id);
    }

    @Test
    public void getTarefaById_TarefaNaoExiste_DeveRetornar404() throws Exception {
        Long id= 404L;
        when(tarefaService.buscarTarefaPorId(id)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/api/tarefas/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void criarTarefa_TarefaValida_DeveRetornar201() throws Exception {
        Long id= 201L;
        TarefaRequisicaoDto requisicao = new TarefaRequisicaoDto("Nova", "tarefa nova", true);
        TarefaRespostaDto resposta = new TarefaRespostaDto(id, "Nova", "tarefa nova", true);

        when(tarefaService.cadastarNovaTarefa(requisicao)).thenReturn(resposta);

        mockMvc.perform(post("/api/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nova"))
                .andExpect(jsonPath("$.descricao").value("tarefa nova"))
                .andExpect(jsonPath("$.completo").value(true));
    }

    @Test
    public void atualizarTarefa_TarefaExiste_DeveRetornar200() throws Exception {

        Long id = 200L;
        TarefaRequisicaoDto dto = new TarefaRequisicaoDto("Atualizado", "Desc", true);
        TarefaRespostaDto resposta = new TarefaRespostaDto(id, "Atualizado", "Desc", true);

        when(tarefaService.atualizarTarefa(eq(id), eq(dto))).thenReturn(ResponseEntity.ok(resposta));

        mockMvc.perform(put("/api/tarefas/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200L))
                .andExpect(jsonPath("$.titulo").value("Atualizado"))
                .andExpect(jsonPath("$.descricao").value("Desc"))
                .andExpect(jsonPath("$.completo").value(true));
    }

    @Test
    public void atualizarTarefa_TarefaNaoExiste_DeveRetornar404() throws Exception {
        Long id= 404L;
        TarefaRequisicaoDto dto = new TarefaRequisicaoDto("Inexistente", "Nada", false);

        when(tarefaService.atualizarTarefa(eq(id), eq(dto)))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(put("/api/tarefas/404")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void apagarTarefa_TarefaExiste_DeveRetornar204() throws Exception {

        Long id = 204L;
        when(tarefaService.removerTarefa(id)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/tarefas/204"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void apagarTarefa_TarefaNaoExiste_DeveRetornar404() throws Exception {
        Long id = 404L;
        when(tarefaService.removerTarefa(id)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/api/tarefas/404"))
                .andExpect(status().isNotFound());
    }



}