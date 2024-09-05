import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class NethostClient {
    public static void start(String host, int port, String password, String path) throws IOException {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect(String.format("tcp://%s:%d", host, port));
                
            byte[] fileData = Files.readAllBytes(Paths.get(path));
            System.out.println(new String(fileData));
            
            ZMsg outMsg = new ZMsg();
            outMsg.add(new ZFrame(password));
            outMsg.add(new ZFrame(fileData));
            outMsg.send(socket);
            System.out.println("Sent file, waiting for response...");

            ZMsg inMsg = ZMsg.recvMsg(socket);
            String response = inMsg.pop().toString();
            System.out.println(response);
        }
    }
}
