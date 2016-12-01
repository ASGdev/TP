package jus.poc.rw;

import jus.poc.rw.Aleatory;
import jus.poc.rw.IResource;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;

public class Reader extends Actor {
	public Reader(Aleatory useLaw, Aleatory vacationLaw, Aleatory iterationLaw,
			IResource[] selection, IObservator observator) {
		super(useLaw, vacationLaw, iterationLaw, selection, observator);		
	}
	

	/**
	 * the acquisition stage of the resources.
	 */
	protected void acquire(IResource resource) throws InterruptedException,
			DeadLockException {
		resource.beginR(this);
		// Appel de l'observateur "observator" pour afficher l'acquisition de la ressource
		this.observator.acquireResource(this, resource);
		
	}

	/**
	 * the release stage of the resources prevously acquired
	 */
	protected void release(IResource resource) throws InterruptedException {
		resource.endR(this);
	}

}
