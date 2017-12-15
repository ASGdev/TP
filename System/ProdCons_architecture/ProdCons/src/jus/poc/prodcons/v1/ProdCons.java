package jus.poc.prodcons.v1;
import jus.poc.prodcons.*;
import java.util.Vector;


public class ProdCons implements Tampon{
	
	Vector<MessageX> tampon = new Vector<>();
	
	public ProdCons(int tampsize) {
		
	}

	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int taille() {
		return tampon.size();
	}
}
