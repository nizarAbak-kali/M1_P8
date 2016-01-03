package com.jade.tp.blackjack;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Player extends Agent {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		System.out.println("Setup: " + getLocalName());
		addBehaviour(new PlayerBehaviour());
	}

	private class PlayerBehaviour extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;
		
		private void sendMessage(String playerName, String message) {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID(playerName, AID.ISLOCALNAME));
			msg.setContent(message);
			send(msg);		
		}

		private ACLMessage receiveMessage() {
			ACLMessage message = null;
			while (message == null) {
				message = receive();
				block();
			}
			
			return message;
		}

		public void action() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ACLMessage message = receiveMessage();			
			System.out.println(getLocalName() + " receive: " + message.getContent());
			
			if (message.getContent() == "kill")
			{
				doDelete();
				return;
			}
			
			if (Judge.bank < 18) {
				Judge.bank += 1 + (int) (Math.random() * 3);
			}
			else {
				Judge.bank += 21 - Judge.bank;
			}
			
			if (isDone()) {
				for (String player : Judge.players) {
					sendMessage(player, "kill");
				}
				doDelete();
				return;
			}
			
			if (++Judge.playerTurn >= Judge.players.size()) {
				Judge.playerTurn = 0;
			}
			
			String nextPlayer = Judge.players.get(Judge.playerTurn);
			System.out.println("Send " + Judge.bank + " to " + nextPlayer + '\n');
			sendMessage(nextPlayer, Integer.toString(Judge.bank));
		}
		
		private boolean isDone() {			
			if (Judge.bank == 21) {
				System.out.println("The winner is: " + Judge.players.get(Judge.playerTurn) + "\n");
				return true;
			}
			else if (Judge.bank > 21) {
				System.out.println("There is no winner \n");
				return true;
			}
			return false;
		}
	}
}
