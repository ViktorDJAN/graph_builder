package ru.promelectronika.dataPacker;

import ru.promelectronika.dto.ReceivedParamDto;
import ru.promelectronika.dto.SentParamDto;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class DataParser {

    // output params 52 bytes
    public static byte[] packSentDtoToByteArray(SentParamDto dto) {
        List<Byte> bytesData = new ArrayList<>();
        byte[] dataArray = new byte[52];
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getStartStop()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEv_u()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEv_i()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEv_maxU()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEv_maxI()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEv_maxP()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getTimeCharge()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getCp_on()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getErr_code()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getReady()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getSoc()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getContactorStatus()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getProtocol()).array()));
        for (int i = 0; i < bytesData.size(); i++) {
            dataArray[i] = bytesData.get(i);
        }
        return dataArray;
    }

    public static void addByteToByteArray(List<Byte> list, byte[] bytesArray) {
        for (byte b : bytesArray) {
            list.add(b);
        }
    }
    public static byte[] rotateArray(byte[] array) {
        byte[] newArray = new byte[array.length];
        for (int i = array.length; i > 0; i--) {
            newArray[array.length - i] = array[i - 1];
        }
        return newArray;
    }

    // parsing incoming messages length=44  INPUT PARAMS
    public static ReceivedParamDto unpackDataToReceivedDto(byte[] gottenData) {
        byte[] slice0 = new byte[4];
        byte[] slice4 = new byte[4];
        byte[] slice8 = new byte[4];
        byte[] slice12 = new byte[4];
        byte[] slice16 = new byte[4];
        byte[] slice20 = new byte[4];
        byte[] slice24 = new byte[4];
        byte[] slice28 = new byte[4];
        byte[] slice32 = new byte[4];
        byte[] slice36 = new byte[4];
        byte[] slice40 = new byte[4];


        for (int i = 0; i < gottenData.length; i++) {
            if (i <= 3) {
                slice0[i] = gottenData[i];
            } else if (i >= 4 && i < 8) {
                slice4[i - 4] = gottenData[i];

            } else if (i >= 8 && i < 12) {
                slice8[i - 8] = gottenData[i];

            } else if (i >= 12 && i < 16) {
                slice12[i - 12] = gottenData[i];

            } else if (i >= 16 && i < 20) {
                slice16[i - 16] = gottenData[i];

            } else if (i >= 20 && i < 24) {
                slice20[i - 20] = gottenData[i];

            } else if (i >= 24 && i < 28) {
                slice24[i - 24] = gottenData[i];

            } else if (i >= 28 && i < 32) {
                slice28[i - 28] = gottenData[i];

            } else if (i >= 32 && i < 36) {
                slice32[i - 32] = gottenData[i];

            } else if (i >= 36 && i < 40) {
                slice36[i - 36] = gottenData[i];

            } else if (i >= 40 && i < 44) {
                slice40[i - 40] = gottenData[i];

            }
        }
        ByteBuffer bb1 = ByteBuffer.wrap(slice0).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb2 = ByteBuffer.wrap(slice4).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb3 = ByteBuffer.wrap(slice8).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb4 = ByteBuffer.wrap(slice12).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb5 = ByteBuffer.wrap(slice16).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb6 = ByteBuffer.wrap(slice20).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb7 = ByteBuffer.wrap(slice24).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb8 = ByteBuffer.wrap(slice28).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb9 = ByteBuffer.wrap(slice32).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb10 = ByteBuffer.wrap(slice36).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb11 = ByteBuffer.wrap(slice40).order(ByteOrder.LITTLE_ENDIAN);

        float evse_u = bb1.getFloat();
        float evse_i = bb2.getFloat();
        float evse_maxU = bb3.getFloat();
        float evse_maxI = bb4.getFloat();
        float evse_maxP = bb5.getFloat();
        float cp_u= bb6.getFloat();
        float cp_freq = bb7.getFloat();
        float cp_dutyCycle = bb8.getFloat();
        float dt_message = bb9.getFloat();
        int stage = bb10.getInt();
        int contactorRequest = bb11.getInt();

        return new ReceivedParamDto(evse_u,evse_i,evse_maxU,evse_maxI,evse_maxP,cp_u,cp_freq,cp_dutyCycle,dt_message,stage,contactorRequest);
    }

}
