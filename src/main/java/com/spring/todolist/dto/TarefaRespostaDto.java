package com.spring.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record TarefaRespostaDto( Long id, String titulo,  String descricao,boolean completo) {

}
