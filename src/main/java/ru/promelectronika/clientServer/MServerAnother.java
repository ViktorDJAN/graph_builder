package ru.promelectronika.clientServer;

import ru.promelectronika.dto.DataBaseSimple;
import ru.promelectronika.dto.DataDto;
import ru.promelectronika.enums.AECommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

public class MServerAnother {
    protected ServerSocketChannel serverSocketChannel;
    protected Selector selector;
    protected String address;
    protected static SocketChannel client;

    protected DataDto dataDto = new DataDto();


    protected int port;
    protected static DataInputStream dataInputStream;
    protected static DataOutputStream dataOutputStream;
    protected SelectionKey selectKey;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

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


        processMsg(receivedMessage);

        // Process the received message (e.g., echo it back to the client)
        System.out.println("GB_server_received: " + receivedMessage);
        String messageResponse = "Thank you for message";
        Charset charset = StandardCharsets.UTF_8;
        ByteBuffer responseBuffer = charset.encode(messageResponse);
        client.write(responseBuffer);


    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public void processMsg(String message) {

        if (message.substring(0, 11).equals("BUILD_GRAPH")) {
            System.err.println("GOT IT");
            String useful = message.substring(12, message.length() - 1);

            String[] split = useful.split(", ");
            double val1 = Double.parseDouble(split[0]);
            double val2 = Double.parseDouble(split[1]);
            System.err.println(val1 + " " + val2);

            dataDto.setCoordinateA((float) val1);
            dataDto.setCoordinateB((float) val2);
            DataBaseSimple.getObjectsBase().add(dataDto);
        }
    }

}
