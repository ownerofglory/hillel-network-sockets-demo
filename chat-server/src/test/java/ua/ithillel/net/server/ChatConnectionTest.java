package ua.ithillel.net.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ChatConnectionTest {
    @Mock
    private ChatConnectionHandler connectionHandlerMock;
    @Mock
    private Socket socketMock;

    private ChatConnection chatConnection;
    private InputStream fakeInputStream;
    private OutputStream fakeOutputStream;

    @BeforeEach
    public void setUp() throws IOException {
       openMocks(this);

       fakeInputStream = new ByteArrayInputStream(new byte[64]);
       when(socketMock.getInputStream()).thenReturn(fakeInputStream);

       fakeOutputStream = new ByteArrayOutputStream(256);
       when(socketMock.getOutputStream()).thenReturn(fakeOutputStream);

       chatConnection = new ChatConnection(socketMock, connectionHandlerMock);
    }

    @Test
    public void constructorTest_containsNamePrompt() throws IOException {
        String promptMessage = "Please enter your name: ";
        ByteArrayOutputStream fakeBis = (ByteArrayOutputStream) fakeOutputStream;
        String actualValue = fakeBis.toString();

        assertTrue(actualValue.contains(promptMessage));
    }

    @Test
    public void sendMessageTest_containsWrittenMessage() throws IOException {
        String testMessage = "Hello";
        chatConnection.sendMessage(testMessage);

        ByteArrayOutputStream fakeBis = (ByteArrayOutputStream) fakeOutputStream;
        String actualValue = fakeBis.toString();

        assertTrue(actualValue.contains(testMessage));
    }

    @AfterEach
    public void tearDown() {
        System.out.println("End of test");
    }
}
