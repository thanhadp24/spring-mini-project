package sun.vn.employee_management.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    private String message;
    private LocalDateTime timestamp;
    private int status;
    private Object error;
    private String path;

    public ErrorResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }



    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
