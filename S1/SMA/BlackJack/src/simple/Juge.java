package simple;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Juge extends Agent {
    Random random ;
    ArrayList<AgentController> player_list ;
    int tours ;
    int nbjoueurs ;
	@Override
	protected void setup() {

        random = new Random();
        player_list = new ArrayList<>();
        nbjoueurs = 6;


        System.out.println(" === ====================================== === ");
        System.out.println(" ===              Simple Blackjack          === ");
        System.out.println(" === ====================================== === ");

        System.out.println("Lancement de la création des N joueurs");
        creerNJoueur(nbjoueurs);
        startNJoueur();
        /*
        * pour lancer de maniere alea les N joueurs je shuffle ma liste de joueurs
        * */
        //Collections.shuffle(player_list);

        tours = random.nextInt(nbjoueurs);
        System.out.println("INIT: tours :"+tours);
        addBehaviour(new JugeBehaviour());
	}

    public void startNJoueur(){
        for (int i = 1; i < nbjoueurs; i++) {
            try {
                player_list.get(i).start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }

    public void aleaStartJoueur(){
        ArrayList<AgentController> tmp  = (ArrayList) player_list.clone();
       AgentController x ;
        while (!tmp.isEmpty()){
            int choix = random.nextInt()%tmp.size();
            x = tmp.get(choix);
            try {
                x.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
            tmp.remove(x);
        }
    }

    public void creerNJoueur(int nbJ){
        String nom_Joueur_n = "Joueur_";
        String tmp ;
        for (int i = 0; i < nbJ; i++) {
            tmp = nom_Joueur_n+i;
            System.out.println(tmp+" crée !!");
            creerJouer(tmp);
        }
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
			AgentController ac = cc.createNewAgent(nomJoueur,"simple.Joueur", args);
            player_list.add(ac);
            player_list.get(player_list.size()-1).start();
            //ac.start();

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

    public void envoyerNMessage(String banque) throws StaleProxyException {
        String player_name;

        for (int i = 0; i < nbjoueurs; i++) {
            player_name = player_list.get(i).getName();
            System.out.println("Envoyer Message a "+player_name);
            envoyerMessage(player_name,banque);
        }
    }

	private class JugeBehaviour extends Behaviour {

		int banque = 0;

		String JoueurGagnant = "";
        String JoueurPerdant = "";

        public void action() {

			// Attendre une seconde
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

            tours +=  1;
            tours = tours%nbjoueurs;
            System.out.printf("tours : "+tours);
            System.out.println("envoie au joueur : "+tours+" ");
            envoyerMessage(("Joueur_"+tours),Integer.toString(banque));


            ACLMessage message = null;
			while (message == null) {
				block();
				message = receive();
			}
            int valJouee = Integer.parseInt(message.getContent());
            if(banque+valJouee > 21){
                JoueurPerdant = message.getSender().getLocalName();
            }
            else if(banque+valJouee == 21){
                banque += valJouee;
                System.out.println(message.getSender().getLocalName() + " joue :"
                        + banque);
                System.out.println(message.getSender().getLocalName() + " Jackpot");
                JoueurGagnant = message.getSender().getLocalName();
            }
            else {
                banque += valJouee;
                System.out.println(message.getSender().getLocalName() + " joue :" + banque);
            }
		}

		public boolean done() {
			if (banque >= 21) {
                System.out.println("Le gangnant est  : " + JoueurGagnant);

                for (int i = 0; i < nbjoueurs; i++) {
                    envoyerMessage("Joueur_"+i,"meurt");
                }

                return true;
            }
			return false;
		}
	}
}
