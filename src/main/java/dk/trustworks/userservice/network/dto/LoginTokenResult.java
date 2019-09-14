package dk.trustworks.userservice.network.dto;

public class LoginTokenResult {

    private String token;
    private boolean success;
    private String failureReason;

    public LoginTokenResult() {
    }

    public LoginTokenResult(String token, boolean success, String failureReason) {
        this.token = token;
        this.success = success;
        this.failureReason = failureReason;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    @Override
    public String toString() {
        return "LoginTokenResult{" +
                "token='" + token + '\'' +
                ", success=" + success +
                ", failureReason='" + failureReason + '\'' +
                '}';
    }
}
