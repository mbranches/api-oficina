package com.branches.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PiecePostRequest {
    private String name;
    private double unitValue;
}
