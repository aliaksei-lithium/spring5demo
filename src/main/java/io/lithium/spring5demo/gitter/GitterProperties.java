package io.lithium.spring5demo.gitter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gitter")
public class GitterProperties {

    private String token;
    private String room;
    private String chatRoom;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(final String room) {
        this.room = room;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(final String chatRoom) {
        this.chatRoom = chatRoom;
    }
}
