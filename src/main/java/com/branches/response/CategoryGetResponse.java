package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CategoryGetResponse {
    private String name;
    private double hourlyPrice;
}