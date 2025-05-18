package com.spring.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TarefaRequisicaoDto(@NotBlank String titulo, @NotBlank String descricao, @NotNull boolean completo) {
}
