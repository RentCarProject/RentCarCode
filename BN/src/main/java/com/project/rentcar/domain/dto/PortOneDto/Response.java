package com.project.rentcar.domain.dto.PortOneDto;

import lombok.Data;

@Data
public class Response {
    private String access_token;
    private int now;
    private int expired_at;
}
