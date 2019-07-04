package model.collection;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Deck;
import model.Player;
import model.Hand;
import model.Shop;
import network.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static model.collection.Account.*;

public class HandleFiles {
    public static final String BEFORE_RELATIVE = "/Users/hamilamailee/Documents/project-10/";
    private static final String ADDRESS_HERO = "model/collection/JSON-Heroes";
    private static final String ADDRESS_MINION = "model/collection/JSON-Minions";
    private static final String ADDRESS_SPELL = "model/collection/JSON-Spells";
    private static final String ADDRESS_ITEM = "model/collection/JSON-Items";

    public static void createStringOfPlayers() {
        File folder = new File(BEFORE_RELATIVE + "model/collection/players");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            Server.getPlayers().add(listOfFiles[i].getName().split("\\.")[0]);
        }
    }

    public static void createSpells(String thread) throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE + ADDRESS_SPELL);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE + ADDRESS_SPELL + "/" + listOfFiles[i].getName());
                String mana = jsonObject.get("mana").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String desc = jsonObject.get("desc").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                String imagePath = jsonObject.get("imagePath").toString();
                String numInShop = jsonObject.get("numInShop").toString();
                Spell spell = new Spell(mana, id, "spell", name, price, desc, target, numOfTarget, action,
                        friendOrEnemy, locationOfTarget, imagePath, imagePath, numInShop);
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
                if (thread.matches("client")) {
                    Spell.getSpells().add(spell);
                } else {
                    Server.getCards().add(spell);
                }
            }
        }
    }

    public static void createItems(String thread) throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE + ADDRESS_ITEM);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE + ADDRESS_ITEM + "/" + listOfFiles[i].getName());
                String itemType = jsonObject.get("itemType").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String desc = jsonObject.get("desc").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                String specification = jsonObject.get("specification").toString();
                String user = jsonObject.get("user").toString();
                String activationTime = jsonObject.get("activationTime").toString();
                String imagePath = jsonObject.get("imagePath").toString();
                Spell spell = new Spell(target, numOfTarget, action, friendOrEnemy, locationOfTarget);
                String numInShop = jsonObject.get("numInShop").toString();
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
                Item item = new Item(spell, itemType, price, name, id, desc, specification, user, activationTime, imagePath, imagePath, numInShop);
                if (thread.matches("client")) {
                    Item.getItems().add(item);
                } else {
                    Server.getCards().add(item);
                }
            }
        }
    }

    public static void createMinions(String thread) throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE + ADDRESS_MINION);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE + ADDRESS_MINION + "/" + listOfFiles[i].getName());
                String healthPoint = jsonObject.get("healthPoint").toString();
                String attackRange = jsonObject.get("attackRange").toString();
                String attackPower = jsonObject.get("attackPower").toString();
                String mana = jsonObject.get("mana").toString();
                String attackType = jsonObject.get("attackType").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String activationTime = jsonObject.get("activationTime").toString();
                String specialPower = jsonObject.get("specialPower").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("locationOfTarget").toString();
                String doesNotGetAttack = jsonObject.get("doesNotGetAttack").toString();
                String imagePath = jsonObject.get("imagePath").toString();
                String numInShop = jsonObject.get("numInShop").toString();
                Minion minion = new Minion(mana, id, "minion", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType
                        , attackRange, specialPower, action, locationOfTarget, doesNotGetAttack, activationTime, imagePath, imagePath, numInShop);
                Buff.createBuffsForMinion(minion, action, buffs, effectValue, delay, last, activationTime);
                if (thread.matches("client")) {
                    Minion.getMinions().add(minion);
                } else {
                    Server.getCards().add(minion);
                }

            }
        }
    }

    public static void createHeroes(String thread) throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE + ADDRESS_HERO);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE + ADDRESS_HERO + "/" + listOfFiles[i].getName());
                String healthPoint = jsonObject.get("healthPoint").toString();
                String attackRange = jsonObject.get("attackRange").toString();
                String attackPower = jsonObject.get("attackPower").toString();
                String attackType = jsonObject.get("attackType").toString();
                String mana = jsonObject.get("mana").toString();
                String price = jsonObject.get("price").toString();
                String name = jsonObject.get("name").toString();
                String id = jsonObject.get("id").toString();
                String coolDown = jsonObject.get("coolDown").toString();
                String specialPower = jsonObject.get("specialPower").toString();
                String target = jsonObject.get("target").toString();
                String numOfTarget = jsonObject.get("numOfTarget").toString();
                String action = jsonObject.get("action").toString();
                String buffs = jsonObject.get("buffs").toString();
                String effectValue = jsonObject.get("effectValue").toString();
                String delay = jsonObject.get("delay").toString();
                String last = jsonObject.get("last").toString();
                String friendOrEnemy = jsonObject.get("friendOrEnemy").toString();
                String locationOfTarget = jsonObject.get("healthPoint").toString();
                String imagePath = jsonObject.get("imagePath").toString();
                String forceInField = jsonObject.get("forceInField").toString();
                String numInShop = jsonObject.get("numInShop").toString();
                Hero hero = new Hero(mana, id, "hero", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType, attackRange,
                        specialPower, action, locationOfTarget, coolDown, imagePath, forceInField, numInShop);
                Buff.createBuffsForHero(hero, action, buffs, effectValue, delay, last);
                if (thread.matches("client")) {
                    Hero.getHeroes().add(hero);
                } else {
                    Server.getCards().add(hero);
                }
            }
        }
    }

    //    public static void createStringOfItems() {
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            String fileName = listOfFiles[i].getName().split("\\.")[0];
//            Item.itemNames.add(fileName);
//        }
//    }
//    public static void createStringOfUsableItems() throws Exception{
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for(int i=0; i<listOfFiles.length; i++){
//            JSONObject item = (JSONObject) HandleFiles.readJsonFiles(ADDRESS_OF_JSON_FILES + "JSON-Items/"+listOfFiles[i].getName());
//            if(item.get("itemType").toString().matches("usable")){
//                String itemName = listOfFiles[i].getName().split("\\.")[0];
//                Shop.usableItems.add(itemName);
//            }
//        }
//    }
    public static Object readJsonFiles(String fileName) throws IOException, ParseException {
        FileReader fileReader = new FileReader(fileName);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fileReader);
    }

    public static void exportDeck(Deck deck) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deck0", returnStringOfDeck(deck));
        Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + DECKS_FOLDER + "/"
                + deck.getDeckName() + ".json"), jsonObject.toJSONString().getBytes());
    }

    public static Deck importDeck(String deckName) throws IOException, ParseException,
            CloneNotSupportedException {
        JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(
                HandleFiles.BEFORE_RELATIVE + DECKS_FOLDER + "/" + deckName + ".json");
        return createDeckFromString(jsonObject.get("deck" + 0).toString());
    }

//    public static JSONObject returnJsonObjectByItsAddress(String fileName) throws IOException, ParseException {
//        File folder = new File(ADDRESS_OF_JSON_FILES + "JSON-Items");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles[i].length(); i++) {
//            return (JSONObject) readJsonFiles(fileName);
//        }
//        return null;
//    }

    public static void writeChangesToJson(Card card) {
        System.out.println("write changes to JSON");
        JSONObject jsonObject = null;
        try {
            switch (card.getCardType()) {
                case "hero":
                    System.out.println("case hero");
                    jsonObject = (JSONObject) HandleFiles.readJsonFiles(
                            BEFORE_RELATIVE + ADDRESS_HERO + "/" + card.getName() + ".json");
                    jsonObject.remove("numInShop");
                    jsonObject.put("numInShop", Integer.toString(card.getNumInShop()));
                    System.out.println("put & remove");
                    Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + ADDRESS_HERO + "/"
                            + card.getName() + ".json"), jsonObject.toJSONString().getBytes());
                    System.out.println("write");
                    break;
                case "minion":
                    jsonObject = (JSONObject) HandleFiles.readJsonFiles(
                            BEFORE_RELATIVE + ADDRESS_MINION + "/" + card.getName() + ".json");
                    jsonObject.remove("numInShop");
                    jsonObject.put("numInShop", Integer.toString(card.getNumInShop()));
                    Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + ADDRESS_MINION + "/"
                            + card.getName() + ".json"), jsonObject.toJSONString().getBytes());
                    break;
                case "spell":
                    jsonObject = (JSONObject) HandleFiles.readJsonFiles(
                            BEFORE_RELATIVE + ADDRESS_SPELL + "/" + card.getName() + ".json");
                    jsonObject.remove("numInShop");
                    jsonObject.put("numInShop", Integer.toString(card.getNumInShop()));
                    Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + ADDRESS_SPELL + "/"
                            + card.getName() + ".json"), jsonObject.toJSONString().getBytes());
                    break;
                case "item":
                    jsonObject = (JSONObject) HandleFiles.readJsonFiles(
                            BEFORE_RELATIVE + ADDRESS_ITEM + "/" + card.getName() + ".json");
                    jsonObject.remove("numInShop");
                    jsonObject.put("numInShop", Integer.toString(card.getNumInShop()));
                    Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + ADDRESS_ITEM + "/"
                            + card.getName()+ ".json"), jsonObject.toJSONString().getBytes());
                    break;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }




    }
}
