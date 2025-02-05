package com.blogger.blog.blog_app_apis.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blogger.blog.blog_app_apis.entities.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 10, message = "Password must be min of 4 characters & max of 10 characters")
    private String password;

    @NotEmpty
    private String about;

    private Set<Roles> roles = new HashSet<>();
}
