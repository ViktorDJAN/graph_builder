package ru.promelectronika.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.promelectronika.dataPacker.DataParser;
import ru.promelectronika.dto.DataBaseSimple;
import ru.promelectronika.dto.SentParamDto;
import ru.promelectronika.dto.ReceivedParamDto;
import ru.promelectronika.enums.ColorTuner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServerHttp {
    private static HttpServer server;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void startHttpServer(String host, int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/check", new MyHandler1());
        server.createContext("/postDataToJava", new MyHandler2());
        server.createContext("/getDataFromJava", new MyHandler3());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println( ColorTuner.GREEN +  "Server started at " + server.getAddress() + ColorTuner.RESET);
    }

    static class MyHandler1 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("GOT IN the method");
            String response = "<h1>The Graph builder app is working...</h1>";
            exchange.sendResponseHeaders(200, response.length());//response code and length
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
            responseBody.close();

            if (!exchange.getResponseHeaders().isEmpty()) {
                System.out.println("NyHandler1 has a response");
            } else {
                System.out.println("NyHandler1 doesn't have any response");
            }

        }
    }

    // POST and GATHER DATA FROM JAVASCRIPT  TO JAVA
    static class MyHandler2 implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedMessage = "";
            if (exchange.getRequestMethod().equals("POST")) {
                requestedMessage = getString(exchange.getRequestBody()); // getRequestBody is input stream
                exchange.getRequestBody().close();
            } else {
                System.err.println("Sorry! It is not Post Method");
            }
            if (!requestedMessage.isEmpty()) {
//                hashMapData = mapper.readValue(requestedMessage, HashMap.class);
                // took data from screen
                SentParamDto sentParamDto = mapper.readValue(requestedMessage, SentParamDto.class);
                byte [] ar = DataParser.packSentDtoToByteArray(sentParamDto);
                DataBaseSimple.getSentMsgDataBase().add(sentParamDto);
                System.out.println(ColorTuner.GREEN + "SENT-PARAM _DTO " + sentParamDto + ColorTuner.RESET +" ; SIZE _" + DataBaseSimple.getSentMsgDataBase().size());
            } else {
                System.out.println("Sorry there is nothing to read out");
            }

            // In here is forming headers that we send if everything is ok
            Headers responseHeaders = exchange.getResponseHeaders(); // create response header
            responseHeaders.add("Access-Control-Allow-Origin", exchange.getRequestHeaders().get("Origin").get(0)); // specify type and content info
            String response = "Got the message: \n" + requestedMessage; // form response
            exchange.sendResponseHeaders(200, response.length()); // send response headers
            OutputStream os = exchange.getResponseBody(); // returns output stream to which the response body must be written.
            os.write(response.getBytes()); // cast it to bytes
            os.close(); // close stream


        }
    }
// GET : FROM JAVA TO JAVASCRIPT
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    static class MyHandler3 implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            Headers headersResponse = exchange.getResponseHeaders();
            headersResponse.add("Access-Control-Allow-Origin", exchange.getRequestHeaders().get("Origin").get(0));
            Map<String, Object> dataMap = new HashMap<>();

            if (!DataBaseSimple.getSentMsgDataBase().isEmpty()) {
                SentParamDto sentParamDto = DataBaseSimple.getSentMsgDataBase().peekLast();
                dataMap.put("sentData", sentParamDto);
            }
            if (!DataBaseSimple.getReceivedMsgDataBase().isEmpty()) {
                System.out.println(ColorTuner.GREEN + "before size received base: "+ DataBaseSimple.getReceivedMsgDataBase().size() + ColorTuner.RESET);
                ReceivedParamDto receivedParamDto = DataBaseSimple.getReceivedMsgDataBase().pollFirst();
                System.out.println(ColorTuner.BLUE + " after size received base: "+ DataBaseSimple.getReceivedMsgDataBase().size() + ColorTuner.RESET);

                dataMap.put("receivedData", receivedParamDto);
            }
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(dataMap);
            exchange.sendResponseHeaders(200, response.length());//response code and length
            OutputStream responseBody = exchange.getResponseBody();// stream where response body must be written
            responseBody.write(response.getBytes());
            responseBody.close();

//            if (!exchange.getResponseHeaders().isEmpty()) {
//                System.out.println("NyHandler3 has a response" + exchange.getResponseHeaders().toString());
//            } else {
//                System.out.println("NyHandler3 doesn't have any response");
//            }

        }
    }


    public static String getString(InputStream in) {
        // pass in here Stream of request body , process it , get a string
        return new Scanner(in, StandardCharsets.UTF_8)
                .useDelimiter("\\z") // what you want to use as like delimiter
                .next();
    }





    public static HttpServer getServer() {
        return server;
    }

    public static void setServer(HttpServer server) {
        ServerHttp.server = server;
    }
}
