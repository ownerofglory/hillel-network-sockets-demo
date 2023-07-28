package ua.ithillel.net.client;

import java.io.IOException;
import java.net.*;

public class DatagramClient  {
    private final DatagramSocket datagramSocket;
    private final InetAddress localAddress;
    private final int localPort;


    public DatagramClient(String localHost, int localPort) throws UnknownHostException, SocketException {
        this.localAddress = InetAddress.getByName(localHost);
        this.localPort = localPort;
        this.datagramSocket = new DatagramSocket(localPort, localAddress);
    }

    public void requestServer(String host, int port, String data) throws UnknownHostException, SocketException {

        try {
            InetAddress serverAddress = InetAddress.getByName(host);



            byte[] bytes = data.getBytes();

            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, serverAddress, port);
            datagramSocket.send(datagramPacket);

            byte[] buffer = new byte[128];
            DatagramPacket serverDatagram = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(serverDatagram);

            byte[] serverData = serverDatagram.getData();
            System.out.println("server response: " + new String(serverData));
        } catch (IOException e) {
            System.out.println("Unable to send a datagram to the server");
        }
    }

}
