package com.spring.todolist.mapper;

import com.spring.todolist.dto.TarefaRequisicaoDto;
import com.spring.todolist.dto.TarefaRespostaDto;
import com.spring.todolist.model.Tarefas;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarefaMapper {

    public Tarefas paraTarefa(TarefaRequisicaoDto dto) {
        Tarefas tarefas =  new Tarefas();
        tarefas.setTitulo(dto.titulo());  ;
        tarefas.setDescricao(dto.descricao());
        tarefas.setCompleto(dto.completo());
        return tarefas;
    }

    public TarefaRespostaDto paraDTOResposta (Tarefas tarefas){

        TarefaRespostaDto dtoResposta =  new TarefaRespostaDto(tarefas.getId(), tarefas.getTitulo(), tarefas.getDescricao(), tarefas.isCompleto());
        return  dtoResposta;
    }

    public List<TarefaRespostaDto> toResponseList (List<Tarefas> list){

        List<TarefaRespostaDto> listaResposta = list.stream().map(l->{
            TarefaRespostaDto respostaDto =  new TarefaRespostaDto(l.getId(),l.getTitulo(),l.getDescricao(),l.isCompleto());
            return respostaDto;
        }).toList();
        return listaResposta;
    }
}
