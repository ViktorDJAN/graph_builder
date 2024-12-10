package ru.promelectronika.dto;

import java.util.ArrayDeque;

public class DataBaseSimple {
    private static final ArrayDeque<SentParamDto>sentMsgDataBase = new ArrayDeque<>();
    private static final ArrayDeque<ReceivedParamDto>receivedMsgDataBase = new ArrayDeque<>();

    public static ArrayDeque<SentParamDto> getSentMsgDataBase() {
        return sentMsgDataBase;
    }
    public static ArrayDeque<ReceivedParamDto> getReceivedMsgDataBase() {
        return receivedMsgDataBase;
    }

}
