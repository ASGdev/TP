package jus.poc.rw;

import jus.poc.rw.Aleatory;
import jus.poc.rw.IResource;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;

public class Writer extends Actor {

	public Writer(Aleatory useLaw, Aleatory vacationLaw, Aleatory iterationLaw,
			IResource[] selection, IObservator observator) {
		super(useLaw, vacationLaw, iterationLaw, selection, observator);
	}

	/**
	 * the acquisition stage of the resources.
	 */
	protected void acquire(IResource resource) throws InterruptedException,
			DeadLockException {
		resource.beginW(this);
		this.observator.acquireResource(this, resource);
		
	}

	/**
	 * the release stage of the resources prevously acquired
	 */
	protected void release(IResource resource) throws InterruptedException {
		resource.endW(this);	
	}

}
