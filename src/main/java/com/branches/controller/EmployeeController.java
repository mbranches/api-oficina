package com.branches.controller;

import com.branches.mapper.EmployeeMapper;
import com.branches.model.Employee;
import com.branches.response.EmployeeGetResponse;
import com.branches.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeMapper mapper;
    private final EmployeeService service;

    @GetMapping
    public ResponseEntity<List<EmployeeGetResponse>> findAll(@RequestParam(required = false) String firstName) {
        List<Employee> employees = service.findAll(firstName);

        List<EmployeeGetResponse> response = mapper.toEmployeeGetResponseList(employees);

        return ResponseEntity.ok(response);
    }
}
