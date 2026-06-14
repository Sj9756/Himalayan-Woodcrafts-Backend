package com.santosh.userservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T data;
    private HttpStatus status;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data ){
        return ApiResponse.<T>builder().data(data).build();

    }
    public static <T> ApiResponse<T> success(T data,String message ){
        return ApiResponse.<T>builder().data(data).build();

    }
    public static <T> ApiResponse<T> success(T data,HttpStatus status ){
        return ApiResponse.<T>builder().data(data).status(status).build();

    }
    public static <T> ApiResponse<T> success(T data,HttpStatus status,String message){
        return ApiResponse.<T>builder().message(message).data(data).status(status).build();

    }
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder().message(message).build();
    }
    public static <T> ApiResponse<T> error(String message,HttpStatus status) {
        return ApiResponse.<T>builder().message(message).status(status).build();
    }
}