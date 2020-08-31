package Model;

public class StringStatus {
    private String stringStatus;
    private Status status;

    public StringStatus(Status status) {
        stringStatus = status.toString();
        this.status = status;
    }

    public String getStringStatus() {
        return stringStatus;
    }
    public Status getStatus(){
        return status;
    }

    public void changeStatus(Status status){
        stringStatus = status.toString();
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
