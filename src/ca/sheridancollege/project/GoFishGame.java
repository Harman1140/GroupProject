/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @modifier Dilrose
 * @modifier Harman
 * @modifier navleen
 * @modifier Ritika
 */
import java.util.ArrayList;
import java.util.Scanner;

public class GoFishGame extends Game {

    private Deck deck;

    public GoFishGame(String name) {
        super(name);
        deck = new Deck();
    }

    @Override
    public void play() {
        // Initialize players and deck
        initializePlayers();
        deck.populateDeck();
        deck.shuffle();

        // Deal initial cards to players
        dealCards();

        // Main game loop
        while (!deck.getCards().isEmpty() && !isGameFinished()) {
            for (Player player : getPlayers()) {
                playTurn(player);
                if (isGameFinished()) {
                    break;
                }
            }
        }

        // Declare winner
        declareWinner();
    }

    private void initializePlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            getPlayers().add(new Player(playerName));
        }
    }

    private void dealCards() {
        for (Player player : getPlayers()) {
            for (int i = 0; i < 4; i++) {
                player.addCardToHand(deck.drawCard());
            }
        }
    }

    private void playTurn(Player player) {
        System.out.println("\n" + player.getName() + "'s turn:");
        System.out.println("Your hand: " + player.getHand());

        Scanner scanner  = new Scanner(System.in);
        String namePlayer;
        Player opponent;
        while(true){
        System.out.print("which player you want to ask for a rank (Name): ");
        namePlayer = scanner.nextLine().trim();
           if(!player.getName().equals(namePlayer)) {
            opponent = getOpponent(namePlayer);
            if(opponent == null) {
                System.out.println("Invalid Player name. Try Again! ");
            } else {
                break;
            }
           } else {
               System.out.println("You Can not enter your own name please try again.");
           }
        }
        System.out.print("Ask "+ opponent.getName() + " for a rank: ");
        String rank = scanner.nextLine().trim();

        if (opponent != null) {
            if (opponent.hasCard(rank)) {
                System.out.println(opponent.getName() + " says: Yes, I have " + rank + "s.");
                player.getHand().addAll(opponent.giveCards(rank));
            } else {
                System.out.println(opponent.getName() + " says: Go Fish!");
                player.addCardToHand(deck.drawCard());
            }
        } else {
            System.out.println("No opponents to ask!");
        }
    }
    
    
    

    private Player getOpponent(String namePlayer) {
        for (Player player : getPlayers()) {
            if (player.getName().equals(namePlayer)) {
                return player;
            }
        }
        return null;
    }

    private boolean isGameFinished() {
        for (Player player : getPlayers()) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void declareWinner() {
        Player winner = null;
        for (Player player : getPlayers()) {
            if (winner == null || player.getHand().size() > winner.getHand().size()) {
                winner = player;
            }
        }
        System.out.println(winner.getName() + " wins!");
    }

    public static void main(String[] args) {
        GoFishGame game = new GoFishGame("Go Fish");
        game.play();
    }
}

