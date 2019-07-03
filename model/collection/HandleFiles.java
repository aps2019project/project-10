package model.collection;


import model.Deck;
import network.Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static model.collection.Account.*;

public class HandleFiles {
    public static final String BEFORE_RELATIVE = "/Users/bahar/Desktop/DUELYST/";
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

    public static void createSpells() throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE+ADDRESS_SPELL);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE+ADDRESS_SPELL + "/" + listOfFiles[i].getName());
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
                Spell spell = new Spell(mana, id, "spell", name, price, desc, target, numOfTarget, action, friendOrEnemy, locationOfTarget,imagePath, imagePath);
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
                Spell.getSpells().add(spell);
            }
        }
    }

    public static void createItems() throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE+ADDRESS_ITEM);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE+ADDRESS_ITEM+"/"+listOfFiles[i].getName());
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
                Buff.createBuffsForSpell(spell, action, buffs, effectValue, delay, last);
                Item item = new Item(spell,itemType,price,name,id,desc,specification,user,activationTime,imagePath,imagePath);
                Item.getItems().add(item);
            }
        }
    }
    public static void createMinions() throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE+ADDRESS_MINION);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE+ADDRESS_MINION + "/" + listOfFiles[i].getName());
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
                Minion minion = new Minion(mana, id, "minion", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType
                        , attackRange, specialPower, action, locationOfTarget, doesNotGetAttack, activationTime,imagePath,imagePath);
                Buff.createBuffsForMinion(minion, action, buffs, effectValue, delay, last, activationTime);
                Minion.getMinions().add(minion);
            }
        }
    }

    public static void createHeroes() throws IOException, ParseException {
        File folder = new File(BEFORE_RELATIVE+ADDRESS_HERO);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().matches("[\\D]+" + ".json")) {
                JSONObject jsonObject = (JSONObject) HandleFiles.readJsonFiles(BEFORE_RELATIVE+ADDRESS_HERO + "/" + listOfFiles[i].getName());
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
                Hero hero = new Hero(mana, id, "hero", name, price, target, numOfTarget, friendOrEnemy, healthPoint, attackPower, attackType, attackRange,
                        specialPower, action, locationOfTarget, coolDown, imagePath,forceInField);
                Buff.createBuffsForHero(hero, action, buffs, effectValue, delay, last);
                Hero.getHeroes().add(hero);
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
        jsonObject.put("deck0",returnStringOfDeck(deck));
        Files.write(Paths.get(HandleFiles.BEFORE_RELATIVE + DECKS_FOLDER + "/"
                + deck.getDeckName() + ".json"), jsonObject.toJSONString().getBytes());
    }
    public static Deck importDeck(String deckName) throws IOException, ParseException,
            CloneNotSupportedException {
        JSONObject jsonObject = (JSONObject)HandleFiles.readJsonFiles(
                HandleFiles.BEFORE_RELATIVE+DECKS_FOLDER+"/" + deckName + ".json");
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
}
