package jus.poc.rw.v1;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.Resource;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;
import jus.poc.rw.deadlock.IDetector;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Resource_v1 extends Resource implements IResource {

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
	
	public Resource_v1(IDetector detector, IObservator observator) {
		super(detector, observator);
	}

	/**
	 * @param arg0 le thread actor de type reader démarre son quantum de temps sur le processeur
	 * La méthode rwl.readLock().lock() permet de bloquer le lock utilisé pour les Readers
	 * -- permet une lecture en parralèle des lecteurs --
	 */
	public void beginR(Actor arg0) throws InterruptedException,
			DeadLockException {
		rwl.readLock().lock();	
	}

	/**
	 * @param arg0 le thread actor de type writer démarre son quantum de temps sur le processeur
	 * La méthode rwl.writeLock().lock() permet de bloquer le lock utilisé pour les Writers
	 * -- permet une écriture exclusive  --
	 */
	public void beginW(Actor arg0) throws InterruptedException,
			DeadLockException {
		rwl.writeLock().lock();
		
	}

	/**
	 * @param arg0 le thread actor de type reader libère le processeur
	 * La méthode rwl.readerLock().unlock() permet de libérer le lock utilisé pour les Readers
	 */
	public void endR(Actor arg0) throws InterruptedException {
		rwl.readLock().unlock();	
	}

	/**
	 * @param arg0 le thread actor de type writer libère le processeur
	 * La méthode rwl.writeLock().unlock() permet de libérer le lock utilisé pour les Writers
	 */
	public void endW(Actor arg0) throws InterruptedException {	
		rwl.writeLock().unlock();	
	}

	
	public int ident() {
		return 0;
	}

	
	public void init(Object arg0) throws UnsupportedOperationException {
		//OPTION
	}

}
