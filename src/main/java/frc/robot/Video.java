package frc.robot;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Video
{
    public static void main(String[] args) throws Exception
    {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(ZMQ.SUB);
            socket.bind("tcp://"+"*:5555");
         

            while (!Thread.currentThread().isInterrupted()) {
                // Block until a message is received
                byte[] reply = socket.recv(0);

                // Print the message
                System.out.println(
                    "Received: [" + new String(reply, ZMQ.CHARSET) + "]"
                );

            }
        }
    }
}