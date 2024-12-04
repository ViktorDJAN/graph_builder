package ru.promelectronika;

import ru.promelectronika.dto.ReceivedParamDto;
import ru.promelectronika.dto.SentParamDto;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TestMain2 {
    public static void main(String[] args) {
        Deque<Integer> dataQueue = new ArrayDeque<>();

        System.out.println(dataQueue);

        System.out.println(dataQueue);




        float a1 = 1;
        float a2 = 2;

        SentParamDto dto = new SentParamDto();
        dto.setEvse_U(1);
        dto.setEvse_I(2);
        dto.setEvse_maxU(3);
        dto.setEvse_maxI(4);
        dto.setEvse_maxP(5);
        dto.setCP_U(6);
        dto.setCP_Freq(7);
        dto.setCP_DutyCicle(8);
        dto.setDT_message(9);
        dto.setStage(10);
        dto.setContactorRequest(1);

        byte [] a = getPackedByteArray(dto);
        System.out.print(a.length);

    }

    public static byte[] getPackedByteArray(SentParamDto dto) {
        List<Byte>bytesData = new ArrayList<>();
        byte[] dataArray = new byte[44];
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_U()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_I()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxU()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxI()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxP()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_U()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_Freq()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_DutyCicle()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putFloat(dto.getDT_message()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putInt  (dto.getStage()).array()));
        addByteToByteArray(bytesData,rotateArray(ByteBuffer.allocate(4).putInt(  dto.getContactorRequest()).array()));;

        for(int i = 0; i < bytesData.size(); i++){
            dataArray[i] = bytesData.get(i);
        }
        return dataArray;
    }


    public static void addByteToByteArray(List<Byte> list, byte[] bytesArray){
        for(byte b : bytesArray){
            list.add(b);
        }
    }
    // to send data  44 bytes
    public static byte[] packDataToByteArray(SentParamDto dto){

        return null;
    }

    public  static byte[] rotateArray(byte[] array){
        byte [] newArray = new byte[array.length];
        for(int i = array.length; i >0 ; i--){
            newArray[array.length - i] = array[i-1];
            System.err.println(newArray[array.length - i]);
        }
        return newArray;
    }

}
