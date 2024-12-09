package ru.promelectronika;

import ru.promelectronika.dataPacker.DataParser;
import ru.promelectronika.dto.DataBaseSimple;
import ru.promelectronika.dto.SentParamDto;
import ru.promelectronika.dto.ReceivedParamDto;
import ru.promelectronika.enums.ColorTuner;
import ru.promelectronika.http.ServerHttp;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UDPMain2 { // UDP-Client

    public static InetSocketAddress ownServerIp = new InetSocketAddress("127.0.0.1", 10000);
    public static InetSocketAddress remoteServerIp = new InetSocketAddress("127.0.0.1", 11000);
    public static DatagramSocket datagramSocket;
    static byte[] bufForReceiving = new byte[1024];
    private static final ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(ColorTuner.PURPLE + "GRAPH_BUILDER started__GB"+ ColorTuner.RESET);

        datagramSocket = new DatagramSocket(null);

        datagramSocket.bind(new InetSocketAddress(ownServerIp.getAddress(), ownServerIp.getPort()));
        if (datagramSocket == null) {
            throw new RuntimeException("DatagramSocket is null");
        }

        System.out.println(ColorTuner.PURPLE +datagramSocket.getLocalAddress() + "HttpServer port is "+ ColorTuner.RESET);
        ServerHttp.startHttpServer("127.0.0.1", 3060);
        System.out.println(ColorTuner.PURPLE + "HTTP CREATED: " + ServerHttp.getServer().getAddress().getPort()+ ColorTuner.RESET);

        service.scheduleAtFixedRate((new ReceivedMsgRunnable()), 0, 500, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate((new SentMsgRunnable()), 250, 500, TimeUnit.MILLISECONDS);


    }

    public static List<SentParamDto> getSentParams() {
        List<SentParamDto> dtoList = new ArrayList<>();
        dtoList.add(new SentParamDto(0, 400, 20, 800, 40, 150, 60000, 0, 3, 1, 40, 0, 0));
        dtoList.add(new SentParamDto(0, 400, 20, 800, 40, 150, 60000, 0, 3, 1, 40, 0, 0));
        dtoList.add(new SentParamDto(1, 500, 50, 800, 40, 150, 60000, 1, 5, 1, 50, 1, 0));
        dtoList.add(new SentParamDto(1, 500, 50, 800, 40, 150, 60000, 1, 5, 1, 60, 1, 0));
        dtoList.add(new SentParamDto(1, 500, 50, 800, 40, 150, 50000, 1, 5, 1, 70, 1, 0));
        dtoList.add(new SentParamDto(1, 500, 50, 800, 40, 150, 50000, 1, 5, 1, 100, 1, 0));
        dtoList.add(new SentParamDto(0, 300, 30, 800, 40, 150, 30000, 0, 3, 0, 100, 0, 1));
        dtoList.add(new SentParamDto(0, 200, 20, 800, 40, 150, 20000, 0, 3, 0, 100, 0, 1));
        return dtoList;
    }

    public static void sendMessageToPoemCCS() {
        if (!DataBaseSimple.getSentMsgDataBase().isEmpty()) {
            SentParamDto dto = DataBaseSimple.getSentMsgDataBase().pollFirst();
            byte[] arrayForSending = DataParser.packSentDtoToByteArray(dto);
            DatagramPacket sentDatagramPacket = new DatagramPacket(arrayForSending, arrayForSending.length, remoteServerIp.getAddress(), remoteServerIp.getPort());
            try {
                datagramSocket.send(sentDatagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(ColorTuner.PURPLE + "Data base is empty:" + DataBaseSimple.getSentMsgDataBase().size()+ ColorTuner.RESET);
        }
    }

    public static void receiveMessageFromPoem(DatagramSocket datagramSocket, InetSocketAddress serverIp) {
        DatagramPacket receivedDatagramPacket = new DatagramPacket(bufForReceiving, bufForReceiving.length, serverIp.getAddress(), serverIp.getPort()); // 11000 is SEWD TO distanation address
        try {
            datagramSocket.receive(receivedDatagramPacket);
            byte[] data = receivedDatagramPacket.getData();
            ReceivedParamDto receivedParamDto = DataParser.unpackDataToReceivedDto(data);
            DataBaseSimple.getReceivedMsgDataBase().add(receivedParamDto);
            System.out.println(ColorTuner.BLUE + "size received base: "+ DataBaseSimple.getReceivedMsgDataBase().size() + ColorTuner.RESET);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static class SentMsgRunnable implements Runnable {
        @Override
        public void run() {
            sendMessageToPoemCCS();
            System.out.println(ColorTuner.PURPLE + "Main: Message is send , base size: "+DataBaseSimple.getSentMsgDataBase().size()+ ColorTuner.RESET);
        }
    }


    static class ReceivedMsgRunnable implements Runnable {
        @Override
        public void run() {
            receiveMessageFromPoem(datagramSocket, ownServerIp);
        }
    }
}




