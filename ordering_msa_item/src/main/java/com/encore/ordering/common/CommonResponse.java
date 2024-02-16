package com.encore.ordering.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
//    성공한 메시지
    private HttpStatus status;
    private String message;
    private Object result;
}
