package ru.promelectronika;


import ru.promelectronika.clientServer.MClientAnother;
import ru.promelectronika.clientServer.MServerAnother;
import ru.promelectronika.enums.Command;
import ru.promelectronika.http.ServerHttp;

import java.io.IOException;

public class Main1 {
    static MServerAnother mServer;
    static MClientAnother mClient;

    public static void main(String[] args) throws Exception {
        System.out.println("GRAPH_BUILDER started__GB");
        mServer = MServerAnother.createServerSocket("127.0.0.123", 10000); //  /127.0.0.124:10000
        System.out.println("GB_Server is created: " + mServer.getServerSocketChannel().getLocalAddress());
        Thread.sleep(200);
        ServerHttp serverHttp = new ServerHttp();
        serverHttp.startHttpServer("127.0.0.1", 3060);
        System.out.println("HTTP CREATED" + ServerHttp.getServer());
        Thread thread = new Thread(new ServerRunnable());
        thread.start();
        Thread.sleep(1000);


        mClient = new MClientAnother("127.0.0.123", 9000);
        System.out.println("GB_Client is created: " + mClient.getClientChannel().getLocalAddress());
        Thread.sleep(1000);
        mClient.sendMessageToServer(Command.CONNECT.getCommandContent());
        Thread.sleep(1000);
        mClient.sendMessageToServer(Command.SEND_ME_DATA.getCommandContent());


    }

    static class ServerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    mServer.startServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}

