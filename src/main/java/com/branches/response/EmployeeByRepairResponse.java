package com.branches.response;

import com.branches.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeByRepairResponse {
    private Long id;
    private String name;
    private String lastName;
    private Category category;
}
