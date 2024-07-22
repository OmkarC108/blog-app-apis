package com.blogger.blog.blog_app_apis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Roles {

    @Id
    private int id;

    private String name;
}
