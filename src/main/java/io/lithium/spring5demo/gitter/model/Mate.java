package io.lithium.spring5demo.gitter.model;

public class Mate {

    public String nickname;
    public String firstName;
    public Boolean coolGuy;

    public Mate() {
    }

    public Mate(final String nickname, final String firstName) {
        this(nickname, firstName, true);
    }

    public Mate(final String nickname, final String firstName, final Boolean coolGuy) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.coolGuy = coolGuy;
    }
}
