package com.devstack.taskflow.repository;

import com.devstack.taskflow.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);
}
