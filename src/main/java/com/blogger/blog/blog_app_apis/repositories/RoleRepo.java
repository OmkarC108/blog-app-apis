package com.blogger.blog.blog_app_apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogger.blog.blog_app_apis.entities.Roles;

public interface RoleRepo extends JpaRepository<Roles, Integer> {

}
