package ua.ithillel.net;

import ua.ithillel.net.server.ChatServer;

public class Main {
    public static void main(String[] args) {
        try (ChatServer chatServer = new ChatServer()) {

            chatServer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}