package _2.zadanie.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse ok(Object data) {
        return new ApiResponse(true, "OK", data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }

}

