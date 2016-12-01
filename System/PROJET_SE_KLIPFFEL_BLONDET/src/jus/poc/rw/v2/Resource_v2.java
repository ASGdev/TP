package jus.poc.rw.v2;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.Resource;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;
import jus.poc.rw.deadlock.IDetector;


public class Resource_v2 extends Resource implements IResource {

	private final Object lock = new Object();
	private int NB_READERS = 2;
	private int CPT_NB_READERS = 2; // Initialisation si le premier Actor est un writer
	private int nbreaders = 0;
	private int nbwriters = 0;
	
	public Resource_v2(IDetector detector, IObservator observator) {
		super(detector, observator);
	}

	/**
	 * param arg0 le thread actor de type reader libère le processeur
	 */
	public void beginR(Actor arg0) throws InterruptedException,
			DeadLockException {
		synchronized (lock) {
			while(nbwriters!=0 ){
				lock.wait();
			}
			CPT_NB_READERS ++;
			nbreaders++;
		}
	}

	/**
	 * @param arg0 le thread actor de type writer démarre son quantum de temps sur le processeur
	 **/
	public void beginW(Actor arg0) throws InterruptedException,
			DeadLockException {
		synchronized (lock) {
			while(nbwriters!=0 || nbreaders!=0 || CPT_NB_READERS < NB_READERS){
				lock.wait();
			}
		nbwriters++;
		CPT_NB_READERS = 0;

		}
	}

	/**
	 * @param arg0 le thread actor de type reader libère le processeur
	 **/
	public void endR(Actor arg0) throws InterruptedException {
		synchronized (lock) {
			if (--nbreaders==0){lock.notify();}
		}
	}

	/**
	 * @param arg0 le thread actor de type writer libère le processeur
	 **/
	public void endW(Actor arg0) throws InterruptedException {	
		synchronized (lock) {
			nbwriters--;
			lock.notifyAll();
		}
	}

	
	public int ident() {
		return 0;
	}

	
	public void init(Object arg0) throws UnsupportedOperationException {
		//OPTION
	}

}
