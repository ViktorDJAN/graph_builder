package ru.promelectronika.clientServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.stream.Stream;

// Here is used socket instead socket channel and also outputStream and inputStream furthermore are here
public class MClientAnother {
    private SocketChannel clientChannel;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    ByteBuffer buffer = ByteBuffer.allocate(256);
    String msg = "";

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public MClientAnother(String address, int port) throws IOException {
        clientChannel = SocketChannel.open();
        SocketAddress socketAddress = new InetSocketAddress(address, port);
        clientChannel.connect(socketAddress);
    }

    public void sendMessageToServer1() throws IOException {
//        msg = br.readLine();
//        // Message to send to the server
//        buffer.clear();
        buffer.put(msg.getBytes());
        buffer.flip();

        // Send the message to the server
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
        buffer.clear();
    }

    public void sendMessageToServer2(Object object) throws IOException {
        buffer.put(object.toString().getBytes());
        buffer.flip();
        // Send the message to the server
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
        buffer.clear();
    }

    public void readMessage() throws IOException {
        clientChannel.read(buffer);
        buffer.flip();
        String response = new String(buffer.array(), 0, buffer.limit());
        System.out.println("Server Response: " + response);
        if (msg.equalsIgnoreCase("close")) {
            clientChannel.close();
        }
    }

    public void sendGraphDataToServer() throws IOException, InterruptedException {
        Thread.sleep(50);
        Object[] values = new Object[]{ 11, 33, 44, 55, 66, 77, 88, 99, 100, 110, 120, 130, 140, 150, 130, 120, 90, 80, 20};
        for (Object value : values) {
            ArrayList<Object> data = new ArrayList<>();
            data.add(88);
            data.add(value);
            sendMessageToServer2("BUILD_GRAPH" +data);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }
}
