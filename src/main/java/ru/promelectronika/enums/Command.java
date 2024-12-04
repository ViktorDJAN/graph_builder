package ru.promelectronika.enums;

public enum Command {
    CONNECT("1=127.0.0.123:10000"),
    SEND_ME_DATA("2");

    private String commandContent;

    Command(String commandContent) {
        this.commandContent = commandContent;
    }

    public String getCommandContent() {
        return commandContent;
    }
}
