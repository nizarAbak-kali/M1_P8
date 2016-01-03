package simple_blackjack;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Juge extends Agent {

	@Override
	protected void setup() {
		System.out.println(" === ====================================== === ");
		System.out.println(" ===              Simple Blackjack          === ");
		System.out.println(" === ====================================== === ");

		// Création Jouer_1
		creerJouer("Joueur_1");

		// Création Jouer_2
		creerJouer("Joueur_2");

		addBehaviour(new JugeBehaviour());
	}

	/**
	 * Méthode de création de joueur
	 * 
	 * @param nomJoueur
	 */
	protected void creerJouer(String nomJoueur) {
		Object args[] = new Object[1];
		args[0] = "";
		ContainerController cc = getContainerController();
		try {
			AgentController ac = cc.createNewAgent(nomJoueur,
					"simple_blackjack.Joueur", args);
			ac.start();
		} catch (Exception e) {
			System.out.println("exception : " + e);
		}
	}

	/**
	 * Méthode d'envoi de message
	 * 
	 * @param destinataire
	 * @param message
	 */
	protected void envoyerMessage(String destinataire, String message) {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID(destinataire, AID.ISLOCALNAME));
		msg.setContent(message);
		send(msg);
	}

	private class JugeBehaviour extends Behaviour {

		int banque = 0;
		int compteur = 0;
		String JoueurGagnant = "";

		public void action() {

			// Attendre une seconde
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			compteur++;

			if (compteur % 2 == 0) {
				System.out.println("Envoyer Message a Joueur_1 ");
				envoyerMessage("Joueur_1", Integer.toString(banque));
			} else {
				System.out.println("Envoyer Message a Joueur_2 ");
				envoyerMessage("Joueur_2", Integer.toString(banque));
			}

			ACLMessage message = null;
			while (message == null) {
				block();
				message = receive();
			}

			banque += Integer.parseInt(message.getContent());
			System.out.println(message.getSender().getLocalName() + " joue :"
					+ banque);

			JoueurGagnant = message.getSender().getLocalName();

		}

		public boolean done() {
			if (banque >= 21) {
				System.out.println("Le gangnant est  : " + JoueurGagnant);
				return true;
			}
			return false;
		}
	}
}
