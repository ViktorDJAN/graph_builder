package ru.promelectronika;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        String s = "3f";

        System.out.println(s.chars().mapToObj(Integer::toHexString)
                .collect(Collectors.joining()).toUpperCase());


//        System.out.println(as);

        byte [] bytes = {(byte) 0xfa, (byte)0x3b, (byte) 0xcb,(byte) 0xfd};
        for(byte as : bytes){
            System.out.println(as);
        }
        //   00,00,80,3f,00,00,00,40,00,00,40,40,00,00,80,40,00,00,a0,40,00,00,c0,40,00,00,e0,40,00,00,00,41,00,00,10,41,0a,00,00,00,01,00,00,00
        String a1 = "00000000000048430000a04000803b440000fa430000964358020000010000000000000001000000330000000000000000000000";
        formMessage(a1);
        try {
            ServerSocket socket1 = new ServerSocket();
            ServerSocket socket2 = new ServerSocket();
            socket1.bind(new InetSocketAddress("127.0.0.1", 10000));
            socket2.bind(new InetSocketAddress("127.0.0.1", 11000));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        counter();


        String message = "BUILD_GRAPH[88.8, 12, 24.0]";
        String cmnd = message.substring(0, 11);
        System.out.println(cmnd);
        String useful = message.substring(12, message.length() - 1);
        System.out.println(useful);
        System.out.println();
        String[] split = useful.split(", ");
        int ind = 0;
        for (Object o : split) {

        }
        double val1 = Double.parseDouble(split[0]);
        double val2 = Double.parseDouble(split[1]);
        double val3 = Double.parseDouble(split[2]);

        System.out.println(val1 + " and " + val2 + " and " + val3);
    }

    static void counter() throws InterruptedException {
        int counter = 0;
        ArrayList<Integer> arraList = new ArrayList<>();
        while (counter < 10) {
            Thread.sleep(500);
            arraList.add(counter);
            counter++;
            System.out.println(arraList);
        }
    }

    static void createDataParams(int arrayLenhth) {
        int index = 0;
        while (index < arrayLenhth) {

        }

    }

    public static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    public static void formMessage(String message) {
        int counter = 0;
        int index = 0;
        byte[] array = new byte[]{00,00,00,00,00,00,48,43,00,00,(byte)0xa0,40,00,80,(byte)0x3b,44,00,00,(byte)0xfa,43,00,00,96,43,58,02,00,00,01,00,00,00,00,00,00,00,01,00,00,00,33,00,00,00,00,00,00,00,00,00,00} ;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < message.length(); i++) {
            if (counter < 2) {
                sb.append(message.charAt(i));
                counter++;
            } else {
                i--;

                counter = 0;
                System.err.print(sb+",");
                sb.delete(0, sb.length());
            }
        }
    }
}
