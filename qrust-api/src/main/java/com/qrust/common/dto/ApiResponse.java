import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private final T data;
    private final boolean success;
    private final String timestamp;

    private ApiResponse(int code, String message, T data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
        this.timestamp = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage(), data, true);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ApiResponseCode.SUCCESS.getCode(), message, data, true);
    }

    public static <T> ApiResponse<T> error(ApiResponseCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null, false);
    }

    public static <T> ApiResponse<T> error(ApiResponseCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode(), message, null, false);
    }
}
