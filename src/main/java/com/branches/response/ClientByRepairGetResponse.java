package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientByRepairGetResponse {
    private String name;
    private String lastName;
}
