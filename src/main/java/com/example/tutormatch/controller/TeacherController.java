package com.example.tutormatch.controller;

import com.example.tutormatch.dto.TeacherProfileDto;
import com.example.tutormatch.entity.Acceptance;
import com.example.tutormatch.entity.Teacher;
import com.example.tutormatch.entity.User;
import com.example.tutormatch.repository.AcceptanceRepository;
import com.example.tutormatch.repository.UserRepository;
import com.example.tutormatch.service.AuthService;
import com.example.tutormatch.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final AuthService authService;
    private final TeacherService teacherService;
    private final AcceptanceRepository acceptanceRepo;
    private final UserRepository userRepo;

    public TeacherController(AuthService authService,
                             TeacherService teacherService,
                             AcceptanceRepository acceptanceRepo,
                             UserRepository userRepo) {
        this.authService = authService;
        this.teacherService = teacherService;
        this.acceptanceRepo = acceptanceRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> saveProfile(@RequestHeader("X-Auth-Token") String token,
                                         @RequestBody TeacherProfileDto dto) {
        User u = authService.getByToken(token);
        if (u == null)
            return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));

        if (u.getRole() != User.Role.TEACHER)
            return ResponseEntity.status(403).body(Map.of("error", "not a teacher"));

        Teacher t = teacherService.saveOrUpdateProfile(u, dto);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/me")
    public ResponseEntity<?> myProfile(@RequestHeader("X-Auth-Token") String token) {
        User u = authService.getByToken(token);
        if (u == null)
            return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));

        Optional<Teacher> ot = teacherService.getByUser(u);
        return ot.<ResponseEntity<?>>map(ResponseEntity::ok)
                 .orElseGet(() -> ResponseEntity.ok(Map.of("msg", "no profile")));
    }

    @GetMapping("/accepted")
    public ResponseEntity<?> acceptedStudents(@RequestHeader("X-Auth-Token") String token) {
        User u = authService.getByToken(token);
        if (u == null)
            return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));

        Optional<Teacher> ot = teacherService.getByUser(u);
        if (ot.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "no profile"));

        Long teacherId = ot.get().getId();
        List<Acceptance> list = acceptanceRepo.findByTeacherId(teacherId);

        List<Map<String, Object>> out = list.stream().map(a -> {
            Map<String, Object> m = new HashMap<>();
            userRepo.findById(a.getStudentUserId()).ifPresent(us -> m.put("student", us));
            m.put("acceptedAt", a.getAcceptedAt());
            return m;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(out);
    }
}
