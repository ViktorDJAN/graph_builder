package ru.promelectronika;

import ru.promelectronika.dto.DataBaseSimple;
import ru.promelectronika.dto.DataDto;
import ru.promelectronika.dto.ReceivedParamDto;
import ru.promelectronika.dto.SentParamDto;
import ru.promelectronika.http.ServerHttp;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class UDPMain2 { // UDP-Client
    public static void main(String[] args) {
        System.out.println("GRAPH_BUILDER started__GB");

        try (DatagramSocket clientSocket = new DatagramSocket(null)) {
            InetAddress ip = new InetSocketAddress("127.0.0.1", 10450).getAddress();
            clientSocket.bind(new InetSocketAddress(ip, 10450));

            Thread.sleep(200);
            ServerHttp.startHttpServer("127.0.0.1", 3060);
            System.out.println("HTTP CREATED" + ServerHttp.getServer());

            try (Scanner scanner = new Scanner(System.in)) {

                byte[] bufForReceiving = new byte[1024];
                System.out.println(ip);


                //          byte[] array = packDtoToByteArray(dto) ;  // 44 bytes
                //52
//                byte[] array = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43, (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x40, (byte) 0x00, (byte) 0x80, (byte) 0x3b, (byte) 0x44, (byte) 0x00, (byte) 0x00, (byte) 0xfa, (byte) 0x43,
//                        (byte) 0x00, (byte) 0x00, (byte) 0x96, (byte) 0x43, (byte) 0x58, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//                        (byte) 0x33, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,};



                int counter = 0;
                while (true) {
                    //Sending....
//                    String line = scanner.nextLine();
                    for (SentParamDto dto1 : getSentParams()) {
                        if (counter < 7) {
                            byte[] array = packDtoToByteArray(dto1);
                            DatagramPacket datagramPacketSent = new DatagramPacket(array, array.length, ip, 11000); // 11000 is SEWD TO distanation address
                            clientSocket.send(datagramPacketSent);
                            DataBaseSimple.getSentMsgDataBase().add(dto1);
                            counter++;
                            System.out.println("Counter value while sending = "+counter +"__sent: " +DataBaseSimple.getSentMsgDataBase().peekFirst());
                            Thread.sleep(1000);
                        } else {
                            break;
                        }

                    }
                    //Receiving....
                    DatagramPacket datagramPacketGotten = new DatagramPacket(bufForReceiving, bufForReceiving.length, ip, 11000); // 11000 is SEWD TO distanation address
                    clientSocket.receive(datagramPacketGotten);
                    byte[] data = datagramPacketGotten.getData();
                    ReceivedParamDto receivedParamDto = unPackDataToDto(data);
                    DataBaseSimple.getReceivedMsgDataBase().add(receivedParamDto);

                    System.out.println(DataBaseSimple.getReceivedMsgDataBase().add(receivedParamDto) + "__AFTER");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }




    // to send data  44 bytes  // OUTPUT PARAMS
    public static byte[] packDtoToByteArray(SentParamDto dto) {
        List<Byte> bytesData = new ArrayList<>();
        byte[] dataArray = new byte[44];
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_U()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_I()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxU()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxI()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getEvse_maxP()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_U()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_Freq()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getCP_DutyCicle()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putFloat(dto.getDT_message()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getStage()).array()));
        addByteToByteArray(bytesData, rotateArray(ByteBuffer.allocate(4).putInt(dto.getContactorRequest()).array()));


        for (int i = 0; i < bytesData.size(); i++) {
            dataArray[i] = bytesData.get(i);
        }
        return dataArray;
    }


    // to rotate array
    public static byte[] rotateArray(byte[] array) {
        byte[] newArray = new byte[array.length];
        for (int i = array.length; i > 0; i--) {
            newArray[array.length - i] = array[i - 1];
        }
        return newArray;
    }


    public static void addByteToByteArray(List<Byte> list, byte[] bytesArray) {
        for (byte b : bytesArray) {
            list.add(b);
        }
    }


    // parsing incoming messages length=52  INPUT PARAMS
    public static ReceivedParamDto unPackDataToDto(byte[] gottenData) {

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
        byte[] slice44 = new byte[4];
        byte[] slice48 = new byte[4];

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

            } else if (i >= 44 && i < 48) {
                slice44[i - 44] = gottenData[i];

            } else if (i >= 48 && i < 52) {
                slice48[i - 48] = gottenData[i];

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
        ByteBuffer bb12 = ByteBuffer.wrap(slice44).order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer bb13 = ByteBuffer.wrap(slice48).order(ByteOrder.LITTLE_ENDIAN);

        int startStop = bb1.getInt();
        float ev_u = bb2.getFloat();
        float ev_i = bb3.getFloat();
        float ev_maxU = bb4.getFloat();
        float ev_maxI = bb5.getFloat();
        float ev_maxP = bb6.getFloat();
        int timeCharge = bb7.getInt();
        int cpOn = bb8.getInt();
        int errCode = bb9.getInt();
        int ready = bb10.getInt();
        int soc = bb11.getInt();
        int contactorStatus = bb12.getInt();
        int protocol = bb13.getInt();

        ReceivedParamDto receivedParamDto = new ReceivedParamDto();
        receivedParamDto.setStartStop(startStop);
        receivedParamDto.setEv_u(ev_u);
        receivedParamDto.setEv_i(ev_i);
        receivedParamDto.setEv_maxU(ev_maxU);
        receivedParamDto.setEv_maxI(ev_maxI);
        receivedParamDto.setEv_maxP(ev_maxP);
        receivedParamDto.setTimeCharge(timeCharge);
        receivedParamDto.setCp_on(cpOn);
        receivedParamDto.setErr_code(errCode);
        receivedParamDto.setReady(ready);
        receivedParamDto.setSoc(soc);
        receivedParamDto.setContactorStatus(contactorStatus);
        receivedParamDto.setProtocol(protocol);


        return receivedParamDto;
    }

    public static List<SentParamDto> getSentParams() {
        List<SentParamDto> dtoList = new ArrayList<>();
        dtoList.add(new SentParamDto(210.5f, 12, 3, 4, 5, 26, 7.5f, 18, 500, 10, 1));
        dtoList.add(new SentParamDto(220.5f, 21, 3, 4, 5, 26, 5.1f, 28, 500, 10, 1));
        dtoList.add(new SentParamDto(220.5f, 32, 3, 4, 5, 36, 7.0f, 38, 500, 10, 0));
        dtoList.add(new SentParamDto(240.5f, 32, 3, 4, 5, 86, 6.0f, 18, 500, 7, 0));
        dtoList.add(new SentParamDto(260.5f, 43, 3, 4, 5, 36, 5.4f, 38, 400, 8, 0));
        dtoList.add(new SentParamDto(260.5f, 22, 3, 4, 5, 26, 5.1f, 18, 400, 8, 1));
        dtoList.add(new SentParamDto(270.5f, 21, 3, 4, 5, 26, 3.2f, 38, 300, 9, 1));

        return dtoList;
    }

}
