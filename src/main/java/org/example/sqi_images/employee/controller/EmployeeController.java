package org.example.sqi_images.employee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.sqi_images.employee.dto.response.SearchEmployeeResponse;
import org.example.sqi_images.employee.service.EmployeeQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Employee", description = "사원 조회 API")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeQueryService employeeQueryService;

    @Operation(summary = "이메일로 사원 검색", description = "검색한 단어가 이메일에 포함되어있는 사원 리스트를 반환합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<SearchEmployeeResponse>> searchEmployees(@RequestParam String email) {
        List<SearchEmployeeResponse> searchEmployeeResponseList = employeeQueryService.searchEmployees(email);
        return ResponseEntity.ok(searchEmployeeResponseList);
    }
}
