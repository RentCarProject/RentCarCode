package com.project.rentcar.domain.dto.PortOneDto;

import lombok.Data;

@Data
public class TokenResponseDto {
    private int code;
    private Object message;
    private Response response;
}
