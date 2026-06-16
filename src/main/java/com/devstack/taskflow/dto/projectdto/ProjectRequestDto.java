package com.devstack.taskflow.dto.projectdto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequestDto(
        @NotBlank(message = "O campo \"Nome\" deve ser informado.")
        @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
        String name,
        String description,

        @NotBlank(message = "Um \"Email\" deve estar associado ao projeto.")
        @Email(message = "Informe um email válido (example@email.com).")
        String ownerEmail) {
}
