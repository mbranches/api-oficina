package com.branches.controller;

import com.branches.request.RepairPostRequest;
import com.branches.response.*;
import com.branches.service.RepairService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("v1/repairs")
@RequiredArgsConstructor
public class RepairController {
    private final RepairService service;

    @GetMapping
    public ResponseEntity<List<RepairGetResponse>> findAll(@RequestParam(required = false) LocalDate dateRepair) {
        List<RepairGetResponse> response = service.findAll(dateRepair);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairGetResponse> findById(@PathVariable Long id) {
        RepairGetResponse response = service.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{repairId}/employees")
    public ResponseEntity<List<RepairEmployeeByRepairResponse>> findEmployeesByRepairId(@PathVariable Long repairId){
        List<RepairEmployeeByRepairResponse> response = service.findEmployeesByRepairId(repairId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RepairPostResponse> save(@Valid @RequestBody RepairPostRequest postRequest) {
        RepairPostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{repairId}/employees/{employeeId}")
    public ResponseEntity<Void> removesRepairEmployeeById(@PathVariable Long repairId, @PathVariable Long employeeId) {
        service.removesRepairEmployeeById(repairId, employeeId);

        return ResponseEntity.noContent().build();
    }
}
