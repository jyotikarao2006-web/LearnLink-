package com.example.tutormatch.controller;
import java.util.Map;
import com.example.tutormatch.dto.RequirementDto;
import com.example.tutormatch.entity.Requirement;
import com.example.tutormatch.entity.User;
import com.example.tutormatch.service.AuthService;
import com.example.tutormatch.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final AuthService authService;
    private final StudentService studentService;

    public StudentController(AuthService authService, StudentService studentService) {
        this.authService = authService; this.studentService = studentService;
    }

    @PostMapping("/requirement")
    public ResponseEntity<?> postRequirement(@RequestHeader("X-Auth-Token") String token, @RequestBody RequirementDto dto) {
        User u = authService.getByToken(token);
        if (u == null) return ResponseEntity.status(401).body(Map.of("error","unauth"));
        if (u.getRole() != User.Role.STUDENT) return ResponseEntity.status(403).body(Map.of("error","not a student"));
        Requirement r = studentService.postRequirement(u.getId(), dto);
        var matches = studentService.findMatches(r);
        return ResponseEntity.ok(Map.of("requirement", r, "matches", matches));
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptTeacher(@RequestHeader("X-Auth-Token") String token,
                                           @RequestParam Long teacherId) {
        User u = authService.getByToken(token);
        if (u == null) return ResponseEntity.status(401).body(Map.of("error","unauth"));
        if (u.getRole() != User.Role.STUDENT) return ResponseEntity.status(403).body(Map.of("error","not a student"));
        var acc = studentService.acceptTeacher(u.getId(), teacherId);
        return ResponseEntity.ok(acc);
    }
}
