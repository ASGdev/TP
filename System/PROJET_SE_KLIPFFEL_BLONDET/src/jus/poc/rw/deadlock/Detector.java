package jus.poc.rw.deadlock;

import jus.poc.rw.Actor;
import jus.poc.rw.IResource;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.deadlock.DeadLockException;
import jus.poc.rw.deadlock.IDetector;

public class Detector implements IDetector {
	
	public void freeResource(Actor arg0, IResource arg1) {
	}

	
	public void useResource(Actor arg0, IResource arg1) {
	}

	public void waitResource(Actor arg0, IResource arg1){
	}

}
