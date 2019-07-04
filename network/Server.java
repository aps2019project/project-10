package network;

import controller.Controller;
import model.collection.Card;
import model.collection.HandleFiles;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ArrayList<String> players = new ArrayList<>();
    private static ArrayList<Card> cards = new ArrayList<>();
    private static ArrayList<Socket> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public Server() throws Exception {
        HandleFiles.createStringOfPlayers();
        Controller.createAllDataFromJSON("server");
        serverSocket = new ServerSocket(7766);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("client accepted");
            Scanner dis = new Scanner(client.getInputStream());
            PrintStream dos = new PrintStream(client.getOutputStream());
            new Thread(() -> {
                while(true) {
                    if (dis.hasNext()) {
                        Message.stringToMessage(dis.nextLine()).handleMessageReceivedByServer(dos);
                    }
                }
            }).start();
        }
    }


    public static void main(String[] args) throws Exception {
        new Server();
    }
}
