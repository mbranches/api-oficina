package com.branches.request;

import com.branches.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class VehiclePostRequest {
    @NotNull(message = "The field vehicleType is required")
    private VehicleType vehicleType;
    @NotBlank(message = "The field brand is required")
    private String brand;
    @NotBlank(message = "The field model is required")
    private String model;
    @NotNull(message = "The field clientId is required")
    private Long clientId;
}