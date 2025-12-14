package com.spring.todolist.service;

import com.spring.todolist.dto.TarefaRequisicaoDto;
import com.spring.todolist.dto.TarefaRespostaDto;
import com.spring.todolist.mapper.TarefaMapper;
import com.spring.todolist.model.Tarefas;
import com.spring.todolist.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaServiceTest {


    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaMapper mapper;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void listarTodasTarefas_DeveRetornarListaDeDtos() {
        List<Tarefas> tarefas = List.of(new Tarefas(1L, "Tarefa 1", "Teste Tarefa 1", false));
        List<TarefaRespostaDto> resposta = List.of(new TarefaRespostaDto(1L, "Tarafa 1", "Teste Tarefa 1", false));

        when(tarefaRepository.findAll()).thenReturn(tarefas);
        when(mapper.toResponseList(tarefas)).thenReturn(resposta);

        List<TarefaRespostaDto> resultado = tarefaService.listarTodasTarefas();

        assertEquals(resposta, resultado);
        verify(tarefaRepository,times(1)).findAll();
        verify(mapper, times(1)).toResponseList(tarefas);
    }

    @Test
    public void buscarTarefaPorId_TarefaExiste_DeveRetornarOk() {
        Tarefas tarefa = new Tarefas(1L, "Tarefa 2", "Teste Tarefa 2", false);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        ResponseEntity<Tarefas> response = tarefaService.buscarTarefaPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertEquals(tarefa, response.getBody());

        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    public void buscarTarefaPorId_TarefaNaoExiste_DeveRetornar404() {
        Long id = 404L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Tarefas> response = tarefaService.buscarTarefaPorId(id);

        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
        assertNull(response.getBody());

        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    public void cadastarNovaTarefa_DeveSalvarETransformarParaDto() {
        TarefaRequisicaoDto dto = new TarefaRequisicaoDto("Titulo 3", "Teste tarefa 3", true);
        Tarefas tarefa = new Tarefas(null, "Titulo 3 ", "Teste tarefa 3", true);
        Tarefas tarefaSalva = new Tarefas(1L, "Titulo 3", "Teste tarefa 3", true);
        TarefaRespostaDto respostaEsperada = new TarefaRespostaDto(1L, "Titulo 3", "Teste tarefa 3", true);

        when(mapper.paraTarefa(dto)).thenReturn(tarefa);
        when(tarefaRepository.save(tarefa)).thenReturn(tarefaSalva);
        when(mapper.paraDTOResposta(tarefaSalva)).thenReturn(respostaEsperada);

        TarefaRespostaDto resposta = tarefaService.cadastarNovaTarefa(dto);


        assertEquals(respostaEsperada, resposta);
    }

    @Test
    public void atualizarTarefa_TarefaExiste_DeveAtualizarETransformarParaDto() {
        Long id = 1L;
        TarefaRequisicaoDto dto = new TarefaRequisicaoDto("Atualizado", "Nova desc", true);
        Tarefas existente = new Tarefas(id, "Velho", "Velho desc", false);
        Tarefas atualizado = new Tarefas(id, "Atualizado", "Nova desc", true);
        TarefaRespostaDto dtoResposta = new TarefaRespostaDto(id, "Atualizado", "Nova desc", true);

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(tarefaRepository.save(any())).thenReturn(atualizado);
        when(mapper.paraDTOResposta(atualizado)).thenReturn(dtoResposta);

        ResponseEntity<TarefaRespostaDto> response = tarefaService.atualizarTarefa(id, dto);

        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertEquals(dtoResposta, response.getBody());

        verify(tarefaRepository, times(1)).save(any());
    }

    @Test
    public void atualizarTarefa_TarefaNaoExiste_DeveRetornar404() {

        Long id = 404L;
        TarefaRequisicaoDto dto = new TarefaRequisicaoDto("não existente", "TarefaNaoExiste", false);

        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<TarefaRespostaDto> response = tarefaService.atualizarTarefa(id, dto);

        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
        assertNull(response.getBody());

    }

    @Test
    public void removerTarefa_TarefaExiste_DeveRemoverComSucesso() {
        Tarefas tarefa = new Tarefas(1L, "Remover", "Desc", false);
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        ResponseEntity<Object> response = tarefaService.removerTarefa(1L);

        assertEquals(HttpStatus.NO_CONTENT ,response.getStatusCode());
        verify(tarefaRepository).delete(tarefa);
    }

    @Test
    public void removerTarefa_TarefaNaoExiste_DeveRetornar404() {
        Long id = 404L;
        when(tarefaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = tarefaService.removerTarefa(id);

        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
    }



}