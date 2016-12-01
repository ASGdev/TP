package jus.poc.rw.v1;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.deadlock.DeadLockException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Resource implements IResource {

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public void beginR(Actor arg0) throws InterruptedException,
			DeadLockException {
		rwl.writeLock().lock();	
	}

	
	public void beginW(Actor arg0) throws InterruptedException,
			DeadLockException {
		rwl.readLock().lock();
		
	}

	
	public void endR(Actor arg0) throws InterruptedException {
		rwl.readLock().unlock();	
	}

	
	public void endW(Actor arg0) throws InterruptedException {	
		rwl.writeLock().unlock();	
	}

	
	public int ident() {
		return rwl.getQueueLength();
	}

	
	public void init(Object arg0) throws UnsupportedOperationException {
		//
	}

}
