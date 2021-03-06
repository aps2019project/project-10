package model.collection;

import animation.GetImage;
import animation.SpriteAnimation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import network.Server;
import org.json.simple.parser.ParseException;
import view.MainView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import static javafx.scene.paint.Color.rgb;

public class Card implements Cloneable {
    //    private static ArrayList<String> cardNames = new ArrayList<>();
    protected int mana;
    protected int id;
    protected String cardType;
    protected String name;
    //    protected AttackType targetType;
    protected boolean isActive;
    protected boolean hasAttackedInThisTurn;
    protected boolean hasMovedInThisTurn;
    protected boolean ableToAttack;
    protected int price;
    protected int counterOfCard;
    protected int x;
    protected int y;
    protected boolean inGame;
    protected transient ImageView imageViewOfCard;
    private transient Image forceInField;
    private String desc;
    private int numInShop;
    private GetImage getImage;
    private transient SpriteAnimation animation;

    private transient Timer timer;
    private transient PrintStream owner;
    private transient PrintStream highestPriceUser;
    private transient IntegerProperty numInShopProperty = new SimpleIntegerProperty();
    private int auctionPrice;
    private int highestAuctionPrice;
    private transient IntegerProperty highestAuctionPriceProperty = new SimpleIntegerProperty();

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public PrintStream getHighestPriceUser() {
        return highestPriceUser;
    }

    public void setHighestPriceUser(PrintStream highestPriceUser) {
        this.highestPriceUser = highestPriceUser;
    }

    public int getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(int auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public int getHighestAuctionPrice() {
        return highestAuctionPrice;
    }

    public void setHighestAuctionPrice(int highestAuctionPrice) {
        this.highestAuctionPrice = highestAuctionPrice;
    }

    public int getHighestAuctionPriceProperty() {
        return highestAuctionPriceProperty.get();
    }

    public IntegerProperty highestAuctionPricePropertyProperty() {
        return highestAuctionPriceProperty;
    }

    public void setHighestAuctionPriceProperty(int highestAuctionPriceProperty) {
        this.highestAuctionPriceProperty.set(highestAuctionPriceProperty);
    }

    public PrintStream getOwner() {
        return owner;
    }

    public void setOwner(PrintStream owner) {
        this.owner = owner;
    }

    public GetImage getGetImage() {
        return getImage;
    }

    public void setGetImage(GetImage getImage) {
        this.getImage = getImage;
    }

    public SpriteAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(SpriteAnimation animation) {
        this.animation = animation;
    }

    private boolean hasFlag;

    public boolean isHasFlag() {
        return hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public int getNumInShopProperty() {
        return numInShopProperty.get();
    }

    public IntegerProperty numInShopPropertyProperty() {
        return numInShopProperty;
    }

    public void setNumInShopProperty(int numInShopProperty) {
        this.numInShopProperty.set(numInShopProperty);
    }

    public Card() {

    }

    public int getNumInShop() {
        return numInShop;
    }

    public void setNumInShop(int numInShop) {
        this.numInShop = numInShop;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Image getForceInField() {
        return forceInField;
    }

    public void setForceInField(Image forceInField) {
        this.forceInField = forceInField;
    }

    public Card(String mana, String id, String cardType, String name, String price, String imagePath, String inField,String desc,String numInShop) throws FileNotFoundException {
        if (mana.equals("null")) this.mana = 0;
        else this.mana = Integer.parseInt(mana);
        this.id = Integer.parseInt(id);
        this.cardType = cardType;
        this.name = name;
        if (!price.equals("null")) this.price = Integer.parseInt(price);
        else this.price = 0;
        if(cardType.matches("hero")){
            this.imageViewOfCard = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Heros/" + name + "/" + name + ".png");
        }
        else if(cardType.matches("item")){
            this.imageViewOfCard = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Items/" + name + "/" + name + ".png");
        }
        else if(cardType.matches("spell")){
            this.imageViewOfCard = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Spells/" + name + "/" + name + ".png");
        }
        else
            this.imageViewOfCard = MainView.getPhotoWithThisPath(HandleFiles.BEFORE_RELATIVE + "view/Photos/Minions/" + name + "/" + name + ".png");
        if(cardType.matches("hero")){
            this.forceInField = new Image(new FileInputStream(HandleFiles.BEFORE_RELATIVE + inField));
        }
        this.desc = desc;
        this.numInShop = Integer.parseInt(numInShop);
        this.setNumInShopProperty(Integer.parseInt(numInShop));
        this.timer = new Timer();
        this.timer.setCard(this);
    }

    public static Card findCardById(int id) throws CloneNotSupportedException {
        switch (id / 100) {
            case 1:
                return (Hero) Hero.findHeroByID(id).clone();
            case 3:
                return (Minion) Minion.findMinionByID(id).clone();
            case 4:
                return (Spell) Spell.findSpellByID(id).clone();
        }
        return null;
    }

    public static Card findCardByNameInServer(String name) throws CloneNotSupportedException {
        for (Card card:
                Server.getCards()) {
            if(card.name.matches(name)){
                return (Card)card.clone();
            }
        }
        return null;
    }
    public static Card findCardByName(String cardName) throws CloneNotSupportedException {
        for (Hero hero : Hero.getHeroes()) {
            if (hero.getName().compareToIgnoreCase(cardName) == 0) {
                return (Hero) hero.clone();
            }
        }
        for (Minion minion : Minion.getMinions()) {
            if (minion.getName().compareToIgnoreCase(cardName) == 0) {
                return (Minion) minion.clone();
            }
        }
        for (Spell spell : Spell.getSpells()) {
            if (spell.getName().compareToIgnoreCase(cardName) == 0) {
                return (Spell) spell.clone();
            }
        }
        return null;
    }

    public boolean isAbleToAttack() {
        return ableToAttack;
    }

    public void setAbleToAttack(boolean ableToAttack) {
        this.ableToAttack = ableToAttack;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isHasAttackedInThisTurn() {
        return hasAttackedInThisTurn;
    }

    public void setHasAttackedInThisTurn(boolean hasAttackedInThisTurn) {
        this.hasAttackedInThisTurn = hasAttackedInThisTurn;
    }

    public boolean isHasMovedInThisTurn() {
        return hasMovedInThisTurn;
    }

    public void setHasMovedInThisTurn(boolean hasMovedInThisTurn) {
        this.hasMovedInThisTurn = hasMovedInThisTurn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCounterOfCard() {
        return counterOfCard;
    }

    public void setCounterOfCard(int counterOfCard) {
        this.counterOfCard = counterOfCard;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageView getImageViewOfCard() {
        return imageViewOfCard;
    }

    public void setImageViewOfCard(ImageView imageViewOfCard) {
        this.imageViewOfCard = imageViewOfCard;
    }

    //    public boolean checkIfCardCanBePlaced(int x, int y){
//
//
//    }
//
//    public boolean checkIfThisCoordinationIsValid(int x, int y){
//
//
//    }
//
//    public boolean checkIfThisCardExistsInHand(String cardName){
//
//    }

    public void increaseCounterOfCards() {

    }

//    public static boolean thisCardIsMelee(String cardName) throws CloneNotSupportedException {
//        Card card = findCardByName(cardName);
//        if (card.getCardType().equals("minion")){
//            if (((Minion)card).getAttackType().equals("melee"))
//                return true;
//
//        }else if (card.getCardType().equals("hero")){
//            if (((Hero)card).getAttackType().equals("melee"))
//                return true;
//        }
//        return false;
//    }

//    public static boolean thisCardIsRanged(String cardName) throws CloneNotSupportedException {
//        Card card = findCardByName(cardName);
//        if (card.getCardType().equals("minion")){
//            if (((Minion)card).getAttackType().equals("ranged"))
//                return true;
//        }else if (card.getCardType().equals("hero")){
//            if (((Hero)card).getAttackType().equals("ranged"))
//                return true;
//        }
//        return false;
//    }

//    public static boolean thisCardIsHybrid(String cardName) throws CloneNotSupportedException {
//        Card card = findCardByName(cardName);
//        if (card.getCardType().equals("minion")){
//            if (((Minion)card).getAttackType().equals("hybrid"))
//                return true;
//        }else if (card.getCardType().equals("hero")){
//            if (((Hero)card).getAttackType().equals("hybrid"))
//                return true;
//        }
//        return false;
//    }

    public static boolean checkIfCardCanAttack(Card card, int targetX, int targetY) {
        int cardX = card.getX();
        int cardY = card.getY();

        String attackType = null;
        if (card.getCardType().equals("minion")) {
            attackType = ((Minion) card).getAttackType();
        } else if (card.getCardType().equals("hero")) {
            attackType = ((Hero) card).getAttackType();
        }

        switch (attackType) {
            case "melee":
                if (Map.thisCellsAreAdjusting(cardX, cardY, targetX, targetY))
                    return true;
                else
                    return false;
            case "ranged":
                if (!Map.thisCellsAreAdjusting(cardX, cardY, targetX, targetY))
                    return true;
                else
                    return false;
            case "hybrid":
                return true;
        }
        return true;
    }

    public void dispelThisForce(String enemyOrFriend) {
        //Todo : implementing
        if (enemyOrFriend.equals("friend")) {

        } else if (enemyOrFriend.equals("enemy")) {

        }
    }

    public void killCard() {
        //Todo : implementing
    }

    public boolean isEnemy(int x, int y) {
        for (Minion minion : Game.getInstance().getMap().getEnemyMinions()) {
            if (minion.getX() == x && minion.getY() == y) {
                return true;
            }
        }
        if (Game.getInstance().getMap().getEnemyHero().getX() == x
                && Game.getInstance().getMap().getEnemyHero().getY() == y) {
            return true;
        }
        return false;
    }

    public static Card getCardByCoordination(int x, int y) {
        Map map = Game.getInstance().getMap();
        for (Card card : map.getFriendMinions()) {
            if (card.getX() == x && card.getY() == y) {
                System.out.println("return friend minion");
                return card;
            }
        }

        for (Card card : map.getEnemyMinions())
            if (card.getX() == x && card.getY() == y) {
                System.out.println("return enemy minion");
                return card;
            }


        if (map.getFriendHero().getX() == x && map.getFriendHero().getY() == y) {
            System.out.println("return friend hero");
            return map.getFriendHero();
        }

        if (map.getEnemyHero().getX() == x && map.getEnemyHero().getY() == y) {
            System.out.println("return enemy hero");
            return map.getEnemyHero();
        }

        System.out.println("null");
        return null;
    }

    public static boolean thisCardIsYours(int x, int y) {
        if (Game.getInstance().getMap().getFriendHero().x == x
                && Game.getInstance().getMap().getFriendHero().y == y) {
            return true;
        } else {
            for (Card card : Game.getInstance().getMap().getFriendMinions()) {
                if (card.x == x && card.y == y) {
                    return true;
                }
            }
            return false;
        }
    }


    public static Card returnNthCard(String cardType, int index) {
        switch (cardType) {
            case "minion":
                return Minion.getMinions().get(index);
            case "spell":
                return Spell.getSpells().get(index);
            case "hero":
                return Hero.getHeroes().get(index);
            case "item":
                return Item.getItems().get(index);

        }
        return null;
    }

    public static Card findCardInCollectionByID(int cardID) {
        switch (cardID / 100) {
            case 1:
                return findHeroInCollection(cardID);
            case 2:
                return findItemInCollection(cardID);
            case 3:
                return findMinionInCollection(cardID);
            case 4:
                return findSpellInCollection(cardID);
        }
        return null;
    }

    public static Spell findSpellInCollection(int cardID) {
        Iterator<Card> iterator = Game.getInstance().getPlayer1().getCardsInCollection().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getId() == cardID)
                return (Spell) card;
        }
        return null;
    }


    public static Card findMinionInCollection(int cardID) {
        Iterator<Card> iterator = Game.getInstance().getPlayer1().getCardsInCollection().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getId() == cardID)
                return card;
        }
        return null;
    }

    public static Hero findHeroInCollection(int cardID) {
        Iterator<Hero> heroIterator = Game.getInstance().getPlayer1().getHeroesInCollection().iterator();
        while (heroIterator.hasNext()) {
            Hero hero = heroIterator.next();
            if (hero.getId() == cardID) {
                return hero;
            }
        }
        return null;
    }

    public static Item findItemInCollection(int cardID) {
        Iterator<Item> itemIterator = Game.getInstance().getPlayer1().getItemsInCollection().iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.getId() == cardID)
                return item;
        }
        return null;
    }

    public void setDescOfCard(Text text) throws FileNotFoundException {
        text.setText(this.desc);
        Font font = Font.loadFont(new FileInputStream(
                HandleFiles.BEFORE_RELATIVE + "view/Fonts/averta-regularitalic-webfont.ttf"), 10);
        text.setWrappingWidth(Shop.CARD_IN_SHOP_WIDTH - 30);

        text.setFont(font);
        text.setFill(rgb(199, 247, 255));
    }


}
