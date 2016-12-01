package jus.poc.rw.control;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.control.IObservator;

public class Observator_v1 implements IObservator {

	/**
	 * @param arg0 le thread actor qui démarre son quantum de temps sur le processeur
	 * @param arg1 la ressource demandé et aquire par le thread Actor
	 **/
	public void acquireResource(Actor arg0, IResource arg1)
			throws ControlException {
		System.out.println(arg0.getName() + " Acquire : " + arg1.toString());
	}

	
	public void init(int arg0, int arg1) throws ControlException {
	}

	/**
	 * @param arg0 le thread actor relache sa ressource
	 * @param arg1 la ressource relachée par le thread Actor
	 **/
	public void releaseResource(Actor arg0, IResource arg1)
			throws ControlException {
		System.out.println(arg0.getName()  + " Release : " + arg1.toString());	
	}

	/**
	 * @param arg0 le thread actor demande l'acces à la ressource
	 * @param arg1 la ressource demandée par le thread Actor
	 **/
	public void requireResource(Actor arg0, IResource arg1)
			throws ControlException {
		System.out.println(arg0.getName() + " Require : " + arg1.toString());
	}

	public void restartActor(Actor arg0, IResource arg1)
			throws ControlException {
	
		
	}

	public void startActor(Actor arg0) throws ControlException {
	}

	public void stopActor(Actor arg0) throws ControlException {
	}
			
	
}
