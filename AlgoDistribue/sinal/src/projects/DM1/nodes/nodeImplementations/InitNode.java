package projects.DM1.nodes.nodeImplementations;

import java.awt.Color;

import projects.DM1.nodes.messages.WalkerMessage;
import projects.DM1.nodes.timers.InitTimer;

/** the initiator node sends the message (the walker) */
public class InitNode extends WalkerNode {

	/* InitNode() { ... } */
	public void init() {
		super.init(); 
		setColor(Color.GREEN);
		(new InitTimer(this)).startRelative(InitTimer.timerRefresh, this); 		
	}

	public void initiate() {
		WalkerMessage walker = new WalkerMessage();
		System.out.println(this + " is sending now message " + walker);
		send(walker, randomWalkChoice(outgoingConnections));
	}

	public String toString() {
		return super.toString() + "(init)";
	}
}
