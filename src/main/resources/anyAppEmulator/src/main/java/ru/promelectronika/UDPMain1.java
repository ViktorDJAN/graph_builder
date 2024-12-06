package ru.promelectronika;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class UDPMain1 { // UDP-Server
    public static void main(String[] args) throws IOException {// write there null obligatorily to mind your custom address
        InetSocketAddress clientIp = new InetSocketAddress("127.0.0.1", 10450);
        InetSocketAddress serverIp = new InetSocketAddress("127.0.0.1", 11000);

        // to send         // done well
        byte[] array1 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x3b, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array2 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x31, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array3 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x21, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array4 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x11, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array5 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x11, (byte) 0x44,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                  (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                  (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                  (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                  (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                  (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array6 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x11, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xc8, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array7 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x21, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x41,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        byte[] array8 = new byte[]{(byte) 0x00, (byte) 0x80, (byte) 0x3f, (byte) 0x43,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x41,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x7a, (byte) 0x44,
                                   (byte) 0x00, (byte) 0x00, (byte) 0xa0, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x70, (byte) 0x42,
                                   (byte) 0x58, (byte) 0x02, (byte) 0xc0, (byte) 0x41,
                                   (byte) 0x46, (byte) 0x1c, (byte) 0x06, (byte) 0x50,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x8c, (byte) 0x42,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x43,
                                   (byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                   (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

        List<byte[]> arrays = new ArrayList<>();
        arrays.add(array1);
        arrays.add(array2);
        arrays.add(array3);
        arrays.add(array4);
        arrays.add(array5);
        arrays.add(array6);
        arrays.add(array7);
        arrays.add(array8);

        int count = 0;
        ////////////////////
        try (DatagramSocket serverSocket = new DatagramSocket(null)) {
            serverSocket.bind(serverIp);
            System.out.println("Get started: " + serverSocket.getLocalSocketAddress());


            byte[] receiveData;
            byte[] buf = null;
            while (true) {
                receiveData = new byte[52];
                DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
                // Receiving message...
                serverSocket.receive(receivedPacket);
                byte[] gottenData = receivedPacket.getData();


                ArrayList<Number> values = parseDataToValuesArray52(gottenData);
                System.out.println(values.get(0));
                System.out.println(values.get(1));
                System.out.println(values.get(2));
                System.out.println(values.get(3));
                System.out.println(values.get(4));
                System.out.println(values.get(5));
                System.out.println(values.get(6));
                System.out.println(values.get(7));
                System.out.println(values.get(8));
                System.out.println(values.get(9));
                System.out.println(values.get(10));
                System.out.println(values.get(11));
                System.out.println(values.get(12));


                System.out.println("Msg length: " + receivedPacket.getLength());
                String msg = String.valueOf(dataConvert(receiveData));


                if (msg.equals("bye")) {
                    System.out.println("GOT STOP MESSAGE.BYE...");
                    break;
                }
                // Sending response....
//                String line = "Ok. Got message you sent: " + msg;
//                buf = line.getBytes();
//                DatagramPacket packForSending = new DatagramPacket(buf, buf.length, clientIp.getAddress(), clientIp.getPort());
//                serverSocket.send(packForSending);

                for (byte[] array : arrays) {
                    buf = array;
                    System.err.println(buf);
                    DatagramPacket packForSending = new DatagramPacket(buf, buf.length, clientIp.getAddress(), clientIp.getPort());
                    if (count < 7) {
                        serverSocket.send(packForSending);
                        Thread.sleep(1000);
                        count++;
                    } else {
                        break;
                    }

                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static ArrayList<Number> parseDataToValuesArray52(byte[] gottenData) {
        ArrayList<Number> valuesArray = new ArrayList<>();

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


        float n1 = bb1.getInt();
        float n2 = bb2.getFloat();
        float n3 = bb3.getFloat();
        float n4 = bb4.getFloat();
        float n5 = bb5.getFloat();
        float n6 = bb6.getFloat();
        float n7 = bb7.getInt();
        float n8 = bb8.getInt();
        float n9 = bb9.getInt();
        int n10 = bb10.getInt();
        int n11 = bb11.getInt();
        int n12 = bb12.getInt();
        int n13 = bb13.getInt();

        valuesArray.add(n1);
        valuesArray.add(n2);
        valuesArray.add(n3);
        valuesArray.add(n4);
        valuesArray.add(n5);
        valuesArray.add(n6);
        valuesArray.add(n7);
        valuesArray.add(n8);
        valuesArray.add(n9);
        valuesArray.add(n10);
        valuesArray.add(n11);
        valuesArray.add(n12);
        valuesArray.add(n13);

        return valuesArray;

    }

    public static ArrayList<Number> parseDataToValuesArray44(byte[] gottenData) {
        ArrayList<Number> valuesArray = new ArrayList<>();

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


        float n1 = bb1.getFloat();
        float n2 = bb2.getFloat();
        float n3 = bb3.getFloat();
        float n4 = bb4.getFloat();
        float n5 = bb5.getFloat();
        float n6 = bb6.getFloat();
        float n7 = bb7.getFloat();
        float n8 = bb8.getFloat();
        float n9 = bb9.getFloat();
        int n10 = bb10.getInt();
        int n11 = bb11.getInt();
        int n12 = bb12.getInt();
        int n13 = bb13.getInt();

        valuesArray.add(n1);
        valuesArray.add(n2);
        valuesArray.add(n3);
        valuesArray.add(n4);
        valuesArray.add(n5);
        valuesArray.add(n6);
        valuesArray.add(n7);
        valuesArray.add(n8);
        valuesArray.add(n9);
        valuesArray.add(n10);
        valuesArray.add(n11);
        valuesArray.add(n12);
        valuesArray.add(n13);

        return valuesArray;

    }


    public static StringBuilder dataConvert(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}

//           while (true) {
//                receiveData = new byte[512];
//                DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
//                // Receiving message...
//                serverSocket.receive(receivedPacket);
//                System.out.println("Msg length: "+receivedPacket.getLength());
//                String msg = String.valueOf(dataConvert(receiveData));
//
//
//                if (msg.equals("bye")) {
//                    System.out.println("GOT STOP MESSAGE.BYE...");
//                    break;
//                }
//                // Sending response....
//                String line = "Ok. Got message you sent: " + msg;
//                buf = line.getBytes();
//                DatagramPacket packForSending = new DatagramPacket(buf, buf.length, clientIp.getAddress(), clientIp.getPort());
//                serverSocket.send(packForSending);
//
//            }
//        }
