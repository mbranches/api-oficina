package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PieceGetResponse {
    private String name;
    private double unitValue;
}
