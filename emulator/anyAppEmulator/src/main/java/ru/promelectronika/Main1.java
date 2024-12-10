package ru.promelectronika;

import ru.promelectronika.clientServer.MServerAnother;

import java.io.IOException;

public class Main1 {
    static MServerAnother mServer;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("APP_EMULATOR started__AE");
        mServer = MServerAnother.createServerSocket("127.0.0.123",9000);
        Thread thread = new Thread(new ServerRunnable());
        thread.start();

        // waiting for remote own client creation
        boolean creation = false;
        while(!creation) {
            if(mServer.getOwnServersClient()==null){
              Thread.sleep(100);
            }else{
                System.out.println("Own_Servers_client connected to: "+mServer.getOwnServersClient().getClientChannel().getRemoteAddress());
                creation = true;
            }
        }





    }

    static class ServerRunnable implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    mServer.startServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}