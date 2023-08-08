package kz.tele2.crmoda.exception;

public class ErrorResponse{

    public ErrorResponse(String message, String httpStatus, String localizedMessage){
        this.message = message;
        this.httpStatus = httpStatus;
        this.localizedMessage = localizedMessage;
    }

    private String message;
    private String httpStatus;
    private String localizedMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }
}
