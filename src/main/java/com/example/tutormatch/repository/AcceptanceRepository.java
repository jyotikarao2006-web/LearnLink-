package com.example.tutormatch.repository;

import com.example.tutormatch.entity.Acceptance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptanceRepository extends JpaRepository<Acceptance, Long> {
    List<Acceptance> findByTeacherId(Long teacherId);
    List<Acceptance> findByStudentUserId(Long studentUserId);
}
