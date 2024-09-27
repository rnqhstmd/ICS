package org.example.sqi_images.employee.controller;

import lombok.RequiredArgsConstructor;
import org.example.sqi_images.employee.dto.response.SearchEmployeeResponse;
import org.example.sqi_images.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchEmployeeResponse>> searchEmployees(@RequestParam String email) {
        List<SearchEmployeeResponse> searchEmployeeResponseList = employeeService.searchEmployees(email);
        return ResponseEntity.ok(searchEmployeeResponseList);
    }
}
