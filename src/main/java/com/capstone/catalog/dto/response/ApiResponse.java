package com.capstone.catalog.dto.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	
	private Integer status;
	
	private String message;
	
	private T data;
	
	private PaginationInfo paginationInfo;
	
	private Timestamp timestamp;
	
	public static <T> ApiResponse<T> success(T data){
		return ApiResponse.<T>builder()
				.status(200)
				.message("Success")
				.data(data)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
	
	public static <T> ApiResponse<T> success(T data, String message){
		return ApiResponse.<T>builder()
				.status(200)
				.message(message)
				.data(data)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
	
	public static <T> ApiResponse<T> success(String message){
		return ApiResponse.<T>builder()
				.status(200)
				.message(message)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
	
	public static <T> ApiResponse<T> successWithPagination(T data, PaginationInfo pagination){
		return ApiResponse.<T>builder()
				.status(200)
				.message("Success")
				.data(data)
				.paginationInfo(pagination)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
	
	public static <T> ApiResponse<T> error(Integer status, String message){
		return ApiResponse.<T>builder()
				.status(status)
				.message(message)
				.timestamp(Timestamp.valueOf(LocalDateTime.now()))
				.build();
	}
}
