import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/**
 * @author morat 
 */
public class Customer extends Thread {
	private static String[] properties;
	private static int nbQuestion = 0;
	static{
		properties = System.getProperties().keySet().toArray(new String[] {});
		for(java.lang.reflect.Method m : ISupplier.class.getDeclaredMethods())
			if(m.getName().equals("question")) nbQuestion++;
	}
	
	String ou, qui; int num, client;
	int port;
	private Registry reg;
	private ISupplier obj;
	/**
	 * 
	 * @param ou désignation de la machine distante
	 * @param qui nom générique du Provider
	 * @param num numémo spécifique du Provider
	 * @param client numéro du C
	 */
	public  Customer(String host,int port, String nom, int num, int client) {
		this.ou=host; this.qui=nom; this.num=num; this.client=client;this.port=port;
	}
	public void run(){
		try{
			Registry reg = LocateRegistry.getRegistry(port);
			synchronized (Customer.class){
				System.out.print(this + "->" + "://" + ou + "/" + qui + num);
				reg = LocateRegistry.getRegistry(ou);
				obj = (ISupplier) reg.lookup(qui);
				System.out.println(" est lié a " + obj.name());
			}
			try{sleep((int) (Math.random() * 1000));}catch(Exception e){}
			synchronized (Customer.class){
				switch(new Random().nextInt(nbQuestion)){
					case 0: { // cas de l'utilisation de question()
						System.out.println(this + "->" + obj.name() + ".question() = " + obj.question());
						break;
					}case 1: { // cas de l'utilisation de question(String)
						String s = select();
						System.out.println(this + "->" + obj.name() + ".question(" + s + ") = " + obj.question(s));
						break;
					}default: {
						System.err.println(this + "->" + obj.name() + " question inconnue");
					}
				}
			}
		}catch(Throwable e){
			synchronized (Customer.class){
				System.out.println("Provider error: " + e);
			}
		}
	}
	public String toString()  {return "Customer"+client;}
	private String select() {return properties[(int)(Math.random()*properties.length)];}
}
