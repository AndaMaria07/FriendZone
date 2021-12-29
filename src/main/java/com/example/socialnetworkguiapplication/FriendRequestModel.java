package domain;

public class FriendRequestDto {
    private String fromUserEmail;
    private String status;
    private String date;

    public FriendRequestDto(String fromUserEmail, String status, String date) {
        this.fromUserEmail = fromUserEmail;
        this.status = status;
        this.date = date;
    }

    public String getFromUserEmail() {
        return fromUserEmail;
    }

    public void setFromUserEmail(String fromUserEmail) {
        this.fromUserEmail = fromUserEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
