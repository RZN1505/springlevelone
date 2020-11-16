package pro.bolshakov.geekbrains.dz6.controller;

public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    // Геттеры и сеттеры

    public ErrorResponse() {

    }

    void setStatus(int status){
        this.status = status;
    };

    void setMessage(String message){
        this.message = message;
    };

    void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    };
}
