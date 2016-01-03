package com.jade.tp;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class FirstAgent extends Agent {

	private static final long serialVersionUID = 1L;
	
	private int age = 0;

	protected void setup() {
		System.out.println("Setup: " + getLocalName());
		addBehaviour(new FirstAgentBehavior());
	}
	
	private class FirstAgentBehavior extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(age >= 15) {
					doDelete();
			}
			
			System.out.println("New age " + getLocalName() + " : " + age++);
		}
	}
}
