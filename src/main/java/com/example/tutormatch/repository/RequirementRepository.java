package com.example.tutormatch.repository;

import com.example.tutormatch.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {
    List<Requirement> findByStudentUserId(Long studentUserId);
}
