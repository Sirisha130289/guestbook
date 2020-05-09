package be.intecbrussel.guestbook.model;

import java.time.LocalDateTime;

public class Message {
    private LocalDateTime date;
    private String name;
    private String message;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Model{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
