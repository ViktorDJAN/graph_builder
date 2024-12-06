package ru.promelectronika.clientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class MServerAnother {
    protected ServerSocketChannel serverSocketChannel;
    protected Selector selector;
    protected String address;
    protected static SocketChannel client;
    protected int port;
    protected static DataInputStream dataInputStream;
    protected static DataOutputStream dataOutputStream;
    protected SelectionKey selectKey;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private MClientAnother ownServersClient;

    public MServerAnother() { // needs exclusively for initialization
        this.serverSocketChannel = null;
        this.selector = null;
    }

    public void presetServerSocket(String address, int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        this.selector = Selector.open();//
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(address, port));
        System.out.println(serverSocketChannel.getLocalAddress());
        int ops = serverSocketChannel.validOps();
        this.selectKey = serverSocketChannel.register(selector, ops, null);
    }

    public static MServerAnother createServerSocket(String address, int port) {
        MServerAnother server = new MServerAnother();
        try {
            server.presetServerSocket(address, port);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Exception occurred when server created");
            return null;
        }
        return server;
    }

    public void acceptChannel() throws IOException {
        client = serverSocketChannel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Client accepted" + client.getRemoteAddress());


    }

    public void startServer() throws IOException {
        selector.select(1);
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isAcceptable()) {
                acceptChannel();
            }
            if (key.isReadable()) {
                readMessage(key);
            }
            iterator.remove();
        }
    }

    public void readMessage(SelectionKey key) throws IOException {
        int bytesRead = 0;
        // Read data from the client
        SocketChannel client = (SocketChannel) key.channel();
        byteBuffer.clear();
        try {

            bytesRead = client.read(byteBuffer); // returns number of bytes
        } catch (SocketException e) {
            bytesRead = -1;
        }

        if (bytesRead == -1) {
            // Client closed the connection
            key.cancel();
            client.close();

        }
        byteBuffer.flip();
        String receivedMessage = new String(byteBuffer.array(), 0, bytesRead);


        System.out.println("AE_server received: " + receivedMessage);
        processMsg(receivedMessage);

        // Process the received message (e.g., echo it back to the client)

        String messageResponse = "Thank you for message";
        Charset charset = StandardCharsets.UTF_8;
        ByteBuffer responseBuffer = charset.encode(messageResponse);
        client.write(responseBuffer);


    }


    public void processMsg(String message) {
        int commandNumber;
        if (!message.isBlank()) {
            String first = message.substring(0, 1);
            try {
                commandNumber = Integer.parseInt(first);
                System.out.println("CN: " + commandNumber);
                switch (commandNumber) {
                    case 1:
                        String remoteServerAddress = message.substring(2,13);
                        int remoteServerPort = Integer.parseInt(message.substring(14));
                        ownServersClient = new MClientAnother(remoteServerAddress, remoteServerPort);
                        System.out.println("COMMAND_1:  Successfully connected to " + ownServersClient);
                        break;
                    case 2:
                        System.out.println("COMMAND_2: sending graph data to server");
                        ownServersClient.sendGraphDataToServer();

                        break;
                    case 3:
                        System.out.println("Command: " + commandNumber + ", msg = " + message);
                        break;
                }

            } catch (NumberFormatException | IOException e) {
                System.err.println("Impossible to pars as this is not integer!!!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public MClientAnother getOwnServersClient() {
        return ownServersClient;
    }

//        int commandNumber = Integer.parseInt(message.substring(0,1));
//        System.out.println("Command: " + commandNumber);
//        switch (message){
//            case "1":
//                System.out.println();
//                break;
//        }
}

