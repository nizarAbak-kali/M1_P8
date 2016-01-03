package com.jade.tp.blackjack;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Judge extends Agent {
	
	private static final long serialVersionUID = 1L;
	
	public static ArrayList<String> players = new ArrayList<>();
	
	public static int bank = 0;
	public static int playerTurn;

	@Override
	protected void setup() {		
		players.add("Joueur_1");
		players.add("Joueur_2");
		players.add("Kevin");
		
		for (String player : players) {
			createPlayer(player);
		}
		
		playerTurn = (1 + (int) (Math.random() * players.size())) - 1;
		sendMessage(players.get(playerTurn), Integer.toString(bank));
		
		doDelete();
	}
	
	private void createPlayer(String playerName) {
		Object args[] = new Object[1];
		args[0] = "";
		ContainerController cc = getContainerController();
		try {
			AgentController ac = cc.createNewAgent(playerName, "com.jade.tp.blackjack.Player", args);
			ac.start();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	private void sendMessage(String playerName, String message) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID(playerName, AID.ISLOCALNAME));
		msg.setContent(message);
		send(msg);		
	}
}
