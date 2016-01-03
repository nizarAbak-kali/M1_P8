package simple_blackjack;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Joueur extends Agent {

	@Override
	protected void setup() {
		System.out.println("Démarrage " + getLocalName());
		addBehaviour(new JoueurBehaviour());
	}

	/**
	 * class interne pour gérer le comportement des joueurs
	 */
	private class JoueurBehaviour extends CyclicBehaviour {

		public void action() {

			ACLMessage message = null;
			while (message == null) {
				message = receive();
				block();
			}

			System.out.println(getLocalName() + " a reçu "
					+ message.getContent());

			int rand = 1 + (int) (Math.random() * 3);
			envoyerMessage("Juge", Integer.toString(rand));
		}

		/**
		 * Méthode d'envoi de messages
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

	}
}
