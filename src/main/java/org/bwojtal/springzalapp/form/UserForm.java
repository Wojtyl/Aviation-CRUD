package org.bwojtal.springzalapp.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private String username;
    private String password;
    private String role;
}
