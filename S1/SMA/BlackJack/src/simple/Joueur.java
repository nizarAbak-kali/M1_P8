package simple;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.StringACLCodec;

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
        int etat_banque;
        String die_mess;

        public void action() {

            ACLMessage message = null;
            while (message == null) {
                message = receive();
                block();
            }

            etat_banque = Integer.parseInt(message.getContent());
            die_mess = message.getContent();
            if (die_mess.equals("meurt")) {
                System.out.println("je suis morrrrrrrrrrrt : " + getLocalName());
                doDelete();
            } else {

                System.out.println(getLocalName() + " a reçu comme banque" + etat_banque);

                int chiffre = 0;
                for (int i = 1; i < 4; i++) {
                    if (((etat_banque - i) % 4) == 0) {
                        chiffre = i;
                        //System.out.println("sapaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssee");
                    }
                    else {
                        chiffre = 1;
                    }
                }
                //System.out.println("chiffre :"+chiffre);
                if(etat_banque+chiffre <= 21)
                    envoyerMessage("Juge", Integer.toString(chiffre));
                else{
                    envoyerMessage("Juge", Integer.toString(chiffre));
                    doDelete();
                }
            }
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
