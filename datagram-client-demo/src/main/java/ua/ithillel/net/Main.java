package ua.ithillel.net;

import ua.ithillel.net.client.DatagramClient;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            DatagramClient datagramClient = new DatagramClient("localhost", 9000);

            datagramClient.requestServer("localhost", 8000, "Hello Server! What time is it?");
            datagramClient.requestServer("localhost", 8000, "Again please");
            datagramClient.requestServer("localhost", 8000, "And once again");
            datagramClient.requestServer("localhost", 8000, "What time is it?");
        } catch (SocketException e) {
            System.out.println("Unable to communicate to the server");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}