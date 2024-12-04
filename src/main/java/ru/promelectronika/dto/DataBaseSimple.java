package ru.promelectronika.dto;

import java.util.ArrayDeque;

public class DataBaseSimple {
    private static ArrayDeque<Object>objectsBase = new ArrayDeque<>();
    private static ArrayDeque<SentParamDto>sentMsgDataBase = new ArrayDeque<>();
    private static ArrayDeque<ReceivedParamDto>receivedMsgDataBase = new ArrayDeque<>();



    public DataBaseSimple() {
    }

    public static ArrayDeque<Object> getObjectsBase() {
        return objectsBase;
    }

    public static ArrayDeque<SentParamDto> getSentMsgDataBase() {
        return sentMsgDataBase;
    }
    public static ArrayDeque<ReceivedParamDto> getReceivedMsgDataBase() {
        return receivedMsgDataBase;
    }

    @Override
    public String toString() {
        return "DataBaseSimple{" +
                "objectsBase=" + objectsBase +
                '}';
    }
}
