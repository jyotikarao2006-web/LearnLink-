package com.example.tutormatch.service;

import com.example.tutormatch.dto.TeacherProfileDto;
import com.example.tutormatch.entity.Teacher;
import com.example.tutormatch.entity.User;
import com.example.tutormatch.repository.TeacherRepository;
import com.example.tutormatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepo;
    private final UserRepository userRepo;

    public TeacherService(TeacherRepository teacherRepo, UserRepository userRepo) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
    }

    public Teacher saveOrUpdateProfile(User user, TeacherProfileDto dto) {
        Optional<Teacher> ot = teacherRepo.findByUserId(user.getId());
        Teacher t = ot.orElseGet(Teacher::new);
        t.setUserId(user.getId());
        t.setSubjects(dto.getSubjects());
        t.setLevels(dto.getLevels());
        t.setFeePerHour(dto.getFeePerHour());
        t.setTimeslots(dto.getTimeslots());
        t.setLocation(dto.getLocation());
        if (dto.getMode() != null) {
            try { t.setMode(Teacher.Mode.valueOf(dto.getMode())); } catch(Exception e){ t.setMode(Teacher.Mode.ONLINE);}
        }
        return teacherRepo.save(t);
    }

    public Optional<Teacher> getByUser(User user) {
        return teacherRepo.findByUserId(user.getId());
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }
}
