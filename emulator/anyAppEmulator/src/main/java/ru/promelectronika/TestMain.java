package ru.promelectronika;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TestMain {
    public static void main(String[] args) {
        int counter =0;
        while(true) {
            for(int i = 0 ;i <  7;i++){
                System.out.println(counter++);
                if(counter<7){
                    System.out.println("less");
                }else{
                    counter=0;
                }
            }
        }


        // byte[] ass = new byte[]{(byte) 0x3f, (byte) 0x80, (byte) 0x00, (byte) 0x00};
//        byte[] ass = new byte[]{(byte) 0x00, (byte) 0x02, (byte) 0x80, (byte) 0x3f, (byte) 0x31, (byte) 0x3d, (byte) 0x3e, (byte) 0x3c};
//        byte[] dataSlice = new byte[4];
//        ByteBuffer dataBBuffer;
//
//        for (int i = 0; i < ass.length; i++) {
//            if (i <= 2) {
//                // slice0[i] = gottenData[i];
//                dataSlice[i] = ass[i];
//            }
//
//            else if (i >= 3 && i < 5) {
//                dataSlice[i - 3] = ass[i];
//            }
//        }
//
//
//        try {
//            ServerSocket socket1 = new ServerSocket();
//            ServerSocket socket2 = new ServerSocket();
//            socket1.bind(new InetSocketAddress("127.0.0.1", 10000));
//            socket2.bind(new InetSocketAddress("127.0.0.1", 11000));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void showData(ByteBuffer bb) {
        for (byte b : bb.array()) {
            System.out.print("_" + b);
        }
    }
}
