package com.example.tutormatch.service;

import com.example.tutormatch.dto.RequirementDto;
import com.example.tutormatch.entity.Acceptance;
import com.example.tutormatch.entity.Requirement;
import com.example.tutormatch.entity.Teacher;
import com.example.tutormatch.matching.MatchingUtil;
import com.example.tutormatch.repository.AcceptanceRepository;
import com.example.tutormatch.repository.RequirementRepository;
import com.example.tutormatch.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final RequirementRepository reqRepo;
    private final TeacherRepository teacherRepo;
    private final AcceptanceRepository accRepo;

    public StudentService(RequirementRepository reqRepo, TeacherRepository teacherRepo, AcceptanceRepository accRepo) {
        this.reqRepo = reqRepo;
        this.teacherRepo = teacherRepo;
        this.accRepo = accRepo;
    }

    public Requirement postRequirement(Long studentUserId, RequirementDto dto) {
        Requirement r = new Requirement();
        r.setStudentUserId(studentUserId);
        r.setSubject(dto.getSubject());
        r.setLevel(dto.getLevel());
        r.setTimeslots(dto.getTimeslots());
        if (dto.getMode() != null) {
            try { r.setMode(Requirement.Mode.valueOf(dto.getMode())); } catch(Exception e){ r.setMode(Requirement.Mode.ONLINE);}
        }
        r.setLocation(dto.getLocation());
        r.setBudget(dto.getBudget());
        return reqRepo.save(r);
    }

    public List<Map<String, Object>> findMatches(Requirement req) {
        List<Teacher> teachers = teacherRepo.findAll();
        return MatchingUtil.matchRequirementToTeachers(req, teachers);
    }

    public Acceptance acceptTeacher(Long studentUserId, Long teacherId) {
        Acceptance a = new Acceptance();
        a.setStudentUserId(studentUserId);
        a.setTeacherId(teacherId);
        return accRepo.save(a);
    }
}
