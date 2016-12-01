package jus.poc.rw.v3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.Resource;
import jus.poc.rw.Simulator;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;
import jus.poc.rw.deadlock.IDetector;


public class Resource_v3 extends Resource implements IResource  {
	
	private Lock lock = new ReentrantLock();
	private Condition writer  = lock.newCondition();;//A Condition instance is intrinsically bound to a lock. 
	private Condition reader = lock.newCondition();//To obtain a Condition instance for a particular Lock instance use its newCondition() method
	private int attentereader = 0;
	private int attentewriter = 0;
	private int nbreaders = 0;
	private int nbwriters = 0;
	
	public Resource_v3(IDetector detector, IObservator observator) {
		super(detector, observator);
	}

	
	/**
	 * param arg0 le thread actor de type reader libère le processeur
	 */
	public void beginR(Actor arg0) throws InterruptedException,
			DeadLockException {
		lock.lock(); 
		try {
			attentereader++; 
			while (nbwriters!=0){
				 reader.await();
			}
			attentereader--;
			nbreaders++;
		 } finally {
			lock.unlock();
		 }
	}

	/**
	 * @param arg0 le thread actor de type writer démarre son quantum de temps sur le processeur
	 **/
	public void beginW(Actor arg0) throws InterruptedException,
			DeadLockException {
		lock.lock(); 
		try {
			attentewriter++;
			while (nbwriters!=0 || nbreaders!=0 )
			{writer.await();}
			attentewriter--;
			nbwriters++;
		 } finally {
			lock.unlock();
		 }
	}

	/**
	 * @param arg0 le thread actor de type reader libère le processeur
	 **/
	public void endR(Actor arg0) throws InterruptedException {
		lock.lock();
		try{
			nbreaders--;
			if (Simulator.getPolicy().equalsIgnoreCase("LOW_WRITER")){
				if(attentereader==0){
					writer.signal();
				}
				else{
					reader.signal();
				}
			}
			else
			{
				if(attentewriter==0){
					reader.signal();
				}
				else{
					writer.signal();
				}
			}
		}
		finally{
			lock.unlock();
		}
		
		
	}

	/**
	 * @param arg0 le thread actor de type writer libère le processeur
	 **/
	public void endW(Actor arg0) throws InterruptedException {
		lock.lock();
		try{
			nbwriters--;
			if (Simulator.getPolicy().equalsIgnoreCase("LOW_WRITER")){
				if(attentereader==0){
					writer.signal();
				}
				else{
					reader.signal();
				}
			}
			else{
				if(attentewriter==0){
					reader.signal();
				}
				else{
					writer.signal();
				}
			}
		}
		finally{
			lock.unlock();
		}
	}

	public void init(Object arg0) throws UnsupportedOperationException {
	}

}
