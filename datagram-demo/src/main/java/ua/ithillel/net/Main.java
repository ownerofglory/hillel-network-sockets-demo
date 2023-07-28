package ua.ithillel.net;

import ua.ithillel.net.server.DatagramServer;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) {
        try (DatagramServer server = new DatagramServer(8000)) {
            server.start();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}