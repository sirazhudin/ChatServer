import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    ChatServer server;
    String name;

    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.name = String.valueOf(socket.getPort());
        //starting thread // Thread thread = new Thread(client);// thread.start();
        new Thread(this).start();


    }

    void sendToClient(String message, Client client) {
        try {
            new PrintStream(client.socket.getOutputStream()).println(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            //creating server socket with port 1234
            //get I/O streams
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            // create I/O interfaces
            in = new Scanner(is);
            out = new PrintStream(os);
            // reading/writing(sending to) network
            out.println("Welcome to chat ! ");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                if (input.split("::").length >= 2) {
                    server.sendMessage(this.name, ((input.split("::"))));
                } else server.sendAll(this.name + ":" + input);
                out.println("Type 'bye' to end the session .");
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
