package ua.ithillel.net;

import ua.ithillel.net.client.EchoClient;

public class Main {
    public static void main(String[] args) {

        EchoClient echoClient = new EchoClient();

        String data = echoClient.requestServer("localhost", 8000);

        System.out.println(data);
    }
}