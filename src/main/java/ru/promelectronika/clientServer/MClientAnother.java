package ru.promelectronika.clientServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

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

    public void sendMessageToServer(String command) throws IOException {
//        msg = br.readLine();
        // Message to send to the server
        buffer.clear();
        buffer.put(command.getBytes());
        buffer.flip();

        // Send the message to the server
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
//        buffer.clear();

    }

    public void readMessage() throws IOException {
        clientChannel.read(buffer);
        buffer.flip();
        String response = new String(buffer.array(), 0, buffer.limit());
        System.out.println("Server Response: " + response);
        if(msg.equalsIgnoreCase("close")){
            clientChannel.close();
        }
    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }


}
