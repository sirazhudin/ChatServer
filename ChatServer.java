import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    ChatServer() throws IOException {
        serverSocket = new ServerSocket(1234); // new socket with port 1234
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }

    void sendMessage(String cl, String[] message) {
        String n = message[0].trim();
        String msg = message[1];
        System.out.println(n + " : " + msg);
        for (Client client : clients) {
            if (client.name.trim().equals(n)) {
                client.sendToClient("From:" + cl + ":" + msg, client);
            } //else {sendAll(msg);}
        }
    }

    void sendAll(String message) {
        for (Client client : clients) {
            client.receive("From:" + message);
        }
    }

    public void run() {
        while (true) {
            System.out.println("Waiting ...");// wait for a client
            try {
                Socket s = serverSocket.accept();
                int port = s.getPort();
                System.out.println("Client connected " + port + " " + s);
                sendAll("Client connected " + port);//sendToClient("Hello ",s);
                Client thisClient = new Client(s, this);
                //clients.add(new Client(s,this));
                thisClient.sendToClient("Hello:" + port, thisClient);
                clients.add(thisClient);
                //creating a client  Client client = new Client(s);
                System.out.println(thisClient.name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
