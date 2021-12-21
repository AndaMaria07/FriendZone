package domain;

import java.util.Objects;

public class FriendRequest extends Entity<Tuple<String,String>>{
    private String Status;

    public FriendRequest(String status, Tuple<String,String> userIds) {
        Status = status;
        this.setId(userIds);
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(Status, that.Status) && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Status, getId());
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "Status='" + Status + '\'' +
                ", userEmails=" + getId() +
                '}';
    }
}
