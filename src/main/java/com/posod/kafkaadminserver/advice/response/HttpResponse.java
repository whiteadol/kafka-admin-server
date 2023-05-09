package com.posod.kafkaadminserver.advice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse<T> {

    T data;
    int status;
    String failMessage;

    public static <T> HttpResponse<T> success(T data) {
        return new HttpResponse<>(data, HttpStatus.OK.value(), null);
    }

    public static <T> HttpResponse<T> fail(T data, Exception e) {
        return new HttpResponse<>(data, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
