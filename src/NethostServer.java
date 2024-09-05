import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class NethostServer {

    private static String getIPAddress() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        return localhost.getHostAddress();
    }

    public static void start(int port, String password, String path) {
        try {
            System.out.println("Your IP is: " + NethostServer.getIPAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://*:" + port);

            System.out.println("Server is running on port " + port + " ...");
            
            while (!Thread.currentThread().isInterrupted()) {
                ZMsg inMsg = ZMsg.recvMsg(socket);
                String inPassword = inMsg.pop().toString();
                byte[] fileData = inMsg.pop().getData();

                ZMsg outMsg = new ZMsg();

                if (inPassword.equals(password)) {
                    try {
                        Files.write(Paths.get(path), fileData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    outMsg.add(new ZFrame("Receiver successfully received the file!"));
                    outMsg.send(socket);
                    System.out.println("Successfully received file!");
                    socket.close();
                    break;
                }
                else {
                    outMsg.add(new ZFrame("Incorrect password"));
                    outMsg.send(socket);
                }
            }
        }
    }

}
