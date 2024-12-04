package ru.promelectronika.enums;

public enum AECommand {
    BUILD_GRAPH("BUILD_GRAPH");


    private String commandContent;

    AECommand(String commandContent) {
        this.commandContent = commandContent;
    }

    public String getCommandContent() {
        return commandContent;
    }
}
