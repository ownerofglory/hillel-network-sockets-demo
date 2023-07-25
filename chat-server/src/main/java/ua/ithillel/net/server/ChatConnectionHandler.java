package ua.ithillel.net.server;

public interface ChatConnectionHandler {
    void onConnect(ChatConnection connection);
    void onMessage(ChatConnection connection, String message);
    void onDisconnect(ChatConnection connection);
}
