package com.devstack.taskflow.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto{

    @NotBlank(message = "O campo deve ser preenchido.")
    @Size(min = 2, message = "Campo sem o mínimo de caracteres (2).")
    private String name;

    @NotBlank(message = "O campo deve ser preenchido.")
    @Email(message = "Email inválido, fora do padrão: (example@email.com).")
    private String email;

    @NotBlank(message = "O campo deve ser preenchido.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;

    public UserRequestDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
