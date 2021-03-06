package view;

import controller.BattleController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Deck;
import model.Game;
import model.Player;
import model.collection.*;
import model.GraveYard;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GameView {

    public static String searchTypeAndShow(String name) throws CloneNotSupportedException {
        String returnString = "";
        if(Minion.thisCardIsMinion(name))
            returnString = showMinion(Minion.findMinionByName(name));
        else if(Spell.thisCardIsSpell(name))
            returnString = showSpell(Spell.findSpellByName(name));
        else if(Item.thisCardIsItem(name))
            returnString = showItem(Item.findItemByName(name));
        else if(Hero.thisCardIsHero(name))
            returnString = showHero(Hero.findHeroByName(name));
        return returnString;
    }
    public static String showCard(Card card) {
        if(Minion.thisCardIsMinion(card.getName())){
           return showMinion((Minion)card);
        }
        else if(Spell.thisCardIsSpell(card.getName())){
            return showSpell((Spell)card);
        }
        return null;
    }
    public static String showItem(Item item){
        String sellCost;
        if(item.getItemType().matches("usable")){
            sellCost = Integer.toString(item.getPrice());
        }
        else{
            sellCost = "collectible";
        }
        return ("Name : "+item.getName() + " - Desc : " + item.getDesc() + " - Sell Cost : " + sellCost);
    }
    public static String showMinion(Minion minion){
        return ("Type : Minion - Name : " +
                minion.getName() + " – Class: " + minion.getAttackType() + " - AP : " + minion.getAttackPower() + " - HP : "
                + minion.getHealthPoint() + " – MP : " + minion.getMana() + " - Special Power : " +minion.getDesc() +" – Sell Cost : " + minion.getPrice());
    }
    public static String showSpell(Spell spell){
        return ("Type : Spell - Name : " +
                spell.getName() + " – MP : " + spell.getMana() + "Desc : " + spell.getDesc() + " – Sell Cost : " + spell.getPrice());
    }

    public static String showHero(Hero hero){
        return ("Name : " + hero.getName() + " - AP : " + hero.getAttackPower() +
                " – HP : " + hero.getHealthPoint() + " – Class : " + hero.getAttackType() +" - Special power : " + hero.getDesc()
         +". - Sell Cost : " + hero.getPrice());
    }
    public static void showDeck(String deckName) throws CloneNotSupportedException {
            Deck deck = Deck.findDeckByName(deckName);
            System.out.println("Heroes :");
            try{
                System.out.println("1 : " + showHero(deck.getHeroInDeck()));
            }
            catch (NullPointerException e){
                System.out.println();
            }
            System.out.println("Items :");
            for (int i = 1; i <= deck.getItemsInDeck().size(); i++) {
                System.out.println(i + " : " + showItem(deck.getItemsInDeck().get(i - 1)));
            }
            System.out.println("Cards :");
            for (int i = 1; i <= deck.getCardsInDeck().size(); i++) {
                System.out.println(i + " : " + showCard(deck.getCardsInDeck().get(i - 1)));
            }
    }

//    public static void showCardsInGraveYard(GraveYard graveYard) throws IOException, ParseException {
//        int counter=0;
//        System.out.println("Heros :");
//        for(String name: graveYard.getCardsDeletedFromHandName())
//        {
//            System.out.print(counter+" ");
//            showMinion(Minion.findMinionByName(name));
//        }
//    }

    public static void showNextCard(){
        Card nextCard = Game.getInstance().getPlayer1().getMainDeck().getHand().getNextCard();
        if (Minion.thisCardIsMinion(nextCard.getName())){
            showMinion((Minion)nextCard);
        }else if (Spell.thisCardIsSpell(nextCard.getName())){
            showSpell((Spell)nextCard);
        }
    }

//    public static void showCardInfoInGraveYard(int cardID) throws Exception {
//        String cardName = BattleController.returnNameById(cardID);
//
//        if (GraveYard.thisCardIsInGraveYard(cardName)){
//            for (String name : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
//                if (name.equals(cardName))
//                    GameView.showCard(cardName);
//            }
//        }else{
//            System.out.println("Card is not in Grave yard!");
//        }
//    }

//    public static void showCardsInGraveYard(){
//        for (String cardName : Game.getInstance().getGraveYard().getCardsDeletedFromHandName()){
//            GameView.showCard(cardName);
//        }
//    }

    public static void setAllImagesForCards(Player player){
        try {
            for (Card card : player.getCardsInCollection()) {
                card.setImageViewOfCard(Card.findCardByName(card.getName()).getImageViewOfCard());
//                card.setForceInField(Card.findCardByName(card.getName()).getForceInField());
            }
            for (Hero hero: player.getHeroesInCollection()) {
                hero.setImageViewOfCard(Hero.findHeroByName(hero.getName()).getImageViewOfCard());
//                hero.setForceInField(Hero.findHeroByName(hero.getName()).getForceInField());
            }
            for (Item item : player.getItemsInCollection()) {
                item.setImageViewOfCard(Item.findItemByName(item.getName()).getImageViewOfCard());
//                item.setForceInField(Item.findItemByName(item.getName()).getForceInField());
            }
            for (Deck deck : player.getDecksOfPlayer()) {
                setAllImagesForDecks(deck);
            }
            setAllImagesForDecks(player.getMainDeck());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void setAllImagesForDecks(Deck deck){
        try {
            deck.getHeroInDeck().setImageViewOfCard(Hero.findHeroByName(deck.getHeroInDeck().getName()).getImageViewOfCard());
//            deck.getHeroInDeck().setForceInField(Hero.findHeroByName(deck.getHeroInDeck().getName()).getForceInField());
            for (Card card : deck.getCardsInDeck()) {
//                card.setForceInField(Card.findCardByName(card.getName()).getForceInField());
                card.setImageViewOfCard(Card.findCardByName(card.getName()).getImageViewOfCard());
            }
            for(Item item : deck.getItemsInDeck()){
//                item.setForceInField(Item.findItemByName(item.getName()).getForceInField());
                item.setImageViewOfCard(Item.findItemByName(item.getName()).getImageViewOfCard());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void printInvalidCommandWithThisContent(String content){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(content);
        alert.show();
    }

    public static void makeImageGlowWhileMouseEnters(Node... nodes){
        for (Node node : nodes) {
            node.setOnMouseEntered(event -> {
                Glow glow = new Glow(0.9);
                node.setEffect(glow);
            });

            node.setOnMouseExited(event -> node.setEffect(null));
        }
    }

    public static void printInfoMessageWithThisContent(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.show();
    }
}