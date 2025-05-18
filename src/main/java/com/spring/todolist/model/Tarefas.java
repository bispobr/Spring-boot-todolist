package com.spring.todolist.model;

import com.spring.todolist.dto.TarefaRequisicaoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Tarefas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private boolean completo;

    public Tarefas(TarefaRequisicaoDto tarefa) {
        this.titulo = tarefa.titulo();
        this.descricao = tarefa.descricao();
        this.completo = tarefa.completo();
    }
}
