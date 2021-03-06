package model.collection;

import com.google.gson.Gson;
import controller.AI;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.CornerRadii;
import model.AllDatas;
import model.Deck;
import model.Game;
import model.Map;
import model.Player;
import network.Message;
import network.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.GameView;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Inet4Address;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//testing
public class Account implements Cloneable {
    public static final String PLAYERS_FOLDER = "model/collection/players";
    public static final String DECKS_FOLDER = "model/collection/decks";


    public static void createAccount(String name, String password, PrintStream dos) throws Exception {
        if (usernameAlreadyExists(name)) {
            Gson gson = new Gson();
            String alert = "username already exists";
            String str = gson.toJson(alert);
            Message message = new Message(str, "String", "printAlert");
            dos.println(message.messageToString());
            dos.flush();
        } else {
            Gson gson = new Gson();
            String alert = "Account created successfully";
            String str = gson.toJson(alert);
            Message message = new Message(str, "String", "printAlert");
            dos.println(message.messageToString());
            dos.flush();
            writePlayerToFile(name, password);
            Server.getPlayers().add(name);
        }
    }

    public static void login(String name, String password, PrintStream dos) throws Exception {
        System.out.println("entered login");
        if (usernameAlreadyExists(name)) {
            if (checkCorrectPassword(name, password)) {
                setPlayer(name, dos);
            } else {
                Gson gson = new Gson();
                String alert = "password is not correct";
                String str = gson.toJson(alert);
                Message message = new Message(str, "String", "printAlert");
                dos.println(message.messageToString());
                dos.flush();
            }
        } else {
            Gson gson = new Gson();
            String alert = "this username does not exist";
            String str = gson.toJson(alert);
            Message message = new Message(str, "String", "printAlert");
            dos.println(message.messageToString());
            dos.flush();
        }
    }

    public static Object readPlayerFromFile(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public static void writePlayerToFile(String name, String password) throws IOException {
        JSONObject tempPlayer = new JSONObject();
        tempPlayer.put("username", name);
        tempPlayer.put("password", password);
        tempPlayer.put("daric", 15000000);
        tempPlayer.put("numOfWins", 0);
        tempPlayer.put("numOfAllDecks", 0);
        tempPlayer.put("collection", "");
        Files.write((Paths.get(HandleFiles.BEFORE_RELATIVE + PLAYERS_FOLDER + "/" + name + ".json")), tempPlayer.toJSONString().getBytes());
    }

    public static void savePlayer(Player player,PrintStream dos){
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", player.getUserName());
            jsonObject.put("password", player.getPassword());
            jsonObject.put("daric", player.getDaric());
            jsonObject.put("numOfWins", player.getNumOfWins());
            jsonObject.put("numOfAllDecks", player.getDecksOfPlayer().size());
            for (int i = 0; i < player.getDecksOfPlayer().size(); i++) {
                jsonObject.put("deck" + i, returnStringOfDeck(player.getDecksOfPlayer().get(i)));
            }
            if (player.getMainDeck() != null) {
                jsonObject.put("mainDeck", player.getMainDeck().getDeckName());
            }
            jsonObject.put("collection", returnStringOfCollection(player));
            for (Card card : Server.getCards()) {
                HandleFiles.writeChangesToJson(card);
            }
            Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + PLAYERS_FOLDER + "/" + player.getUserName() + ".json"), jsonObject.toJSONString().getBytes());
            String message = gson.toJson("Data was saved successfully");
            dos.println(new Message(message,"String","printAlert").messageToString());
            dos.flush();
        }
        catch (Exception e){
            e.printStackTrace();
            String message = gson.toJson("Problems saving account. Please try again later");
            dos.println(new Message(message,"String","printAlert").messageToString());
            dos.flush();
        }
    }

    public static boolean usernameAlreadyExists(String checkName) {
        for (String name : Server.getPlayers()) {
            if (name.matches(checkName))
                return true;
        }
        return false;
    }

    public static boolean checkCorrectPassword(String name, String password) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(HandleFiles.BEFORE_RELATIVE + PLAYERS_FOLDER + "/" + name + ".json");
        return jsonObject.get("password").toString().matches(password);
    }

    public static String returnStringOfDeck(Deck deck) {
        System.out.println(deck.getDeckName());
        String list = deck.getDeckName();
        if (deck.getHeroInDeck() != null) {
            System.out.println(deck.getHeroInDeck().name);
            list = list + "," + deck.getHeroInDeck().name;
        }
        for (Item item :
                deck.getItemsInDeck()) {
            System.out.println(item.getName());
            list = list + "," + item.getName();
        }
        for (Card card : deck.getCardsInDeck()) {
            System.out.println(card.getName());
            list = list + "," + card.name;
        }
        System.out.println(list);
        return list;
    }

    public static Deck findMainDeckByName(Player player, String deckName) {
        for (Deck deck :
                player.getDecksOfPlayer()) {
            if (deck.getDeckName().matches(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public static Deck createDeckFromStringClient(String deckString) throws CloneNotSupportedException {
        if (!deckString.matches("")) {
            String[] parts = deckString.split(",");
            Deck deck = new Deck(parts[0]);
            if (parts.length > 1) {
                deck.setHeroInDeck(Hero.findHeroByName(parts[1]));
                for (int i = 2; i < parts.length; i++) {
                    if (Item.thisCardIsItem(parts[i])) {
                        deck.getItemsInDeck().add(Item.findItemByName(parts[i]));
                    } else {
                        deck.getCardsInDeck().add(Card.findCardByName(parts[i]));
                    }
                }
            }
            return deck;
        }
        return null;
    }


    public static void createCollectionFromString(Player player, String collection) throws CloneNotSupportedException {
        if (!collection.matches("")) {
            String[] parts = collection.split(",");
            for (int i = 0; i < parts.length; i++) {
                switch (Card.findCardByName(parts[i]).getCardType()) {
                    case "hero":
                        player.getHeroesInCollection().add((Hero) Card.findCardByName(parts[i]));
                        break;
                    case "item":
                        player.getItemsInCollection().add((Item) Card.findCardByName(parts[i]));
                        break;
                    default:
                        player.getCardsInCollection().add(Card.findCardByName(parts[i]));
                        break;
                }
            }
        }
    }

    public static String returnStringOfCollection(Player player) {
        ArrayList<String> names = new ArrayList<>();
        String list = "";
        for (Hero hero:
             player.getHeroesInCollection()) {
            names.add(hero.name);
        }
        for(Item item : player.getItemsInCollection()){
            names.add(item.name);
        }
        for(Card card : player.getCardsInCollection()) {
            names.add(card.name);
        }
        if(names.size() > 0){
            list = list + names.get(0);
            for(int i=1; i<names.size(); i++){
                list = list + "," + names.get(i);
            }
        }
        return list;
    }

    public static void setPlayer(String name, PrintStream dos) throws Exception {
        JSONObject jsonObject = (JSONObject) readPlayerFromFile(HandleFiles.BEFORE_RELATIVE + PLAYERS_FOLDER + "/" + name + ".json");
        Player player;
        player = new Player(
                (String) jsonObject.get("username"),
                (String) jsonObject.get("password")
        );
        player.setDaric(Integer.parseInt(jsonObject.get("daric").toString()));
        player.setNumOfWins(Integer.parseInt(jsonObject.get("numOfWins").toString()));
        player.setNumOfMana(2);
        int numOfDecks = Integer.parseInt(jsonObject.get("numOfAllDecks").toString());
        for (int i = 0; i < numOfDecks; i++) {
            String addDeck = jsonObject.get("deck" + i).toString();
            if (addDeck != null) {
                player.getDecksToCreate().add(addDeck);
            }
        }
        if (jsonObject.get("mainDeck") != null) {
            player.setMainDeckName(jsonObject.get("mainDeck").toString());
        }
        player.setCollectionToCreate(jsonObject.get("collection").toString());
        Gson gson = new Gson();
        String str = gson.toJson(player, Player.class);
        Server.getOnlinePlayers().add(player.getUserName());
        dos.println(new Message(str, "Player", "setPlayer").messageToString());
        dos.flush();
    }

//    public static void setPlayerThatHasPlayedBefore(String name) throws Exception {
//        JSONObject jsonObject = (JSONObject) readPlayerFromFile(HandleFiles.BEFORE_RELATIVE + PLAYERS_FOLDER+"/" + name + ".json");
//        Player player;
//        if(jsonObject.get("justCreated").toString().matches("false")){
//            player = new Player(
//                    (String) jsonObject.get("username"),
//                    (String) jsonObject.get("password"));
//            player.setDaric(Integer.parseInt(jsonObject.get("daric").toString()));
//            player.setNumOfWins(Integer.parseInt(jsonObject.get("numOfWins").toString()));
//            int numOfDecks = Integer.parseInt(jsonObject.get("numOfAllDecks").toString());
//            for(int i=0; i<numOfDecks; i++){
//                Deck addDeck = createDeckFromString(jsonObject.get("deck").toString()+i);
//                if(addDeck != null) {
//                    player.getDecksOfPlayer().add(addDeck);
//                }
//            }
//            if(jsonObject.get("mainDeck")!=null) {
//                player.setMainDeck(createDeckFromString((String) jsonObject.get("mainDeck")));
//            }
//            player.setNumOfMana(2);
//            createCollectionFromString(player,(String)jsonObject.get("collection"));
//            Game createGame = new Game();
//            Game.setCurrentGame(createGame);
////            model.Map map = new Map();
////            Game.getInstance().setMap(map);
//            Game.getInstance().setPlayer1(player);
//            Game.getInstance().setPlayer1Turn(true);
//        }
//    }
}
