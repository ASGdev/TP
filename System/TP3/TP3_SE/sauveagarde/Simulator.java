import jus.poc.rw.Aleatory;
import jus.poc.rw.ResourcePool;
import jus.poc.rw.control.IObservator;
import jus.poc.rw.control.Observator;
import jus.poc.rw.deadlock.IDetector;


/**
 * Main class for the Readers/Writers application. This class firstly creates a pool of read/write resources  
 * implementing interface IResource. Then it creates readers and writers operating on these resources.
 * @author P.Morat & F.Boyer
 */
public class Simulator{
	protected static final String OPTIONFILENAME = "option.xml";
	/** the version of the protocole to be used */
	protected static String version;
	/** the number of readers involve in the simulation */
	protected static int nbReaders;
	/** the number of writers involve in the simulation */
	protected static int nbWriters;
	/** the number of resources involve in the simulation */
	protected static int nbResources;
	/** the number of resources used by an actor */
	protected static int nbSelection;
	/** the law for the reader using delay */
	protected static int readerAverageUsingTime;
	protected static int readerDeviationUsingTime;
	/** the law for the reader vacation delay */
	protected static int readerAverageVacationTime;
	protected static int readerDeviationVacationTime;
	/** the law for the writer using delay */
	protected static int writerAverageUsingTime;
	protected static int writerDeviationUsingTime;
	/** the law for the writer vacation delay */
	protected static int writerAverageVacationTime;
	protected static int writerDeviationVacationTime;
	/** the law for the writer number of iterations */
	protected static int writerAverageIteration;
	protected static int writerDeviationIteration;
	/** the chosen policy for priority */
	protected static String policy;
	
	
	/**
	 * make a permutation of the array
	 * @param array the array to be mixed
	 */
	protected static void mixe(Object[] array) {
		int i1, i2;
		Object a;
		for(int k = 0; k < 2 * array.length; k++){
			i1 = Aleatory.selection(1, array.length)[0];
			i2 = Aleatory.selection(1, array.length)[0];
			a = array[i1]; array[i1] = array[i2]; array[i2] = a;
		}
	}
	/**
	 * Retreave the parameters of the application.
	 * @param file the final name of the file containing the options. 
	 */
	protected static void init(String file) {
		// retreave the parameters of the application
		final class Properties extends java.util.Properties {
			private static final long serialVersionUID = 1L;
			public int get(String key){return Integer.parseInt(getProperty(key));}
			public Properties(String file) {
				try{
					loadFromXML(ClassLoader.getSystemResourceAsStream(file));
				}catch(Exception e){e.printStackTrace();}			
			}
		}
		Properties option = new Properties("jus/poc/rw/options/"+file);	
		System.out.println(System.getProperty("user.dir")+"jus/poc/rw/options/"+file + "  OPTION"option);
		version = option.getProperty("version");
		nbReaders = Math.max(0,new Aleatory(option.get("nbAverageReaders"),option.get("nbDeviationReaders")).next());
		nbWriters = Math.max(0,new Aleatory(option.get("nbAverageWriters"),option.get("nbDeviationWriters")).next());
		nbResources = Math.max(0,new Aleatory(option.get("nbAverageResources"),option.get("nbDeviationResources")).next());
		nbSelection = Math.max(0,Math.min(new Aleatory(option.get("nbAverageSelection"),option.get("nbDeviationSelection")).next(),nbResources));
		readerAverageUsingTime = Math.max(0,option.get("readerAverageUsingTime"));
		readerDeviationUsingTime = Math.max(0,option.get("readerDeviationUsingTime"));
		readerAverageVacationTime = Math.max(0,option.get("readerAverageVacationTime"));
		readerDeviationVacationTime = Math.max(0,option.get("readerDeviationVacationTime"));
		writerAverageUsingTime = Math.max(0,option.get("writerAverageUsingTime"));
		writerDeviationUsingTime = Math.max(0,option.get("writerDeviationUsingTime"));
		writerAverageVacationTime = Math.max(0,option.get("writerAverageVacationTime"));
		writerDeviationVacationTime = Math.max(0,option.get("writerDeviationVacationTime"));
		writerAverageIteration = Math.max(0,option.get("writerAverageIteration"));
		writerDeviationIteration = Math.max(0,option.get("writerDeviationIteration"));
		policy = option.getProperty("policy");
	}
	
	public static void main(String... args) throws Exception{
		// set the application parameters
		init((args.length==1)?args[0]:OPTIONFILENAME);
			
		IObservator observateur = null;
		observateur.init(nbReaders+nbWriters, nbResources);
		IDetector detecteur = null;

		Aleatory uselaw_w = new Aleatory(writerAverageUsingTime,writerDeviationUsingTime);
		Aleatory vacationLaw_w = new Aleatory(writerAverageVacationTime, writerDeviationVacationTime); 
		Aleatory iterationLaw_w = new Aleatory(writerAverageIteration, writerDeviationIteration);
		Aleatory uselaw_r = new Aleatory(readerAverageUsingTime,readerDeviationUsingTime);
		Aleatory vacationLaw_r = new Aleatory(readerAverageVacationTime, readerDeviationVacationTime); 
		
		
		ResourcePool rp = new ResourcePool(nbResources, detecteur, observateur, OPTIONFILENAME );
		Writer w[] = new Writer[nbWriters];
		Reader r[] = new Reader[nbReaders];
		
		for (int i=0 ; i<nbWriters ; i++) {
		    w[i]= new Writer(uselaw_w, vacationLaw_w, iterationLaw_w, rp.selection(1) , observateur);
		}
		for (int i=0 ; i<nbReaders ; i++) {
		    r[i]= new Reader(uselaw_r, vacationLaw_r, null, rp.selection(1) , observateur);
		}
		
	}
}
