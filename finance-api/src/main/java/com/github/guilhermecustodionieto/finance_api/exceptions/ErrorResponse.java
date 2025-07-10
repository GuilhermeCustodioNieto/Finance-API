package com.github.guilhermecustodionieto.finance_api.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorResponse(
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime timestamp,
        Integer code,
        String status,
        List<String> errors
) {

}
