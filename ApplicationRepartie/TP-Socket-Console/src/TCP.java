/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Classe de service fournissant toutes les interactions (read, write) en mode TCP.
 * @author Morat 
 */
class TCP{
	private static final int MAX_LEN_BUFFER = 1024;
	/**
	 * 
	 * @param soc the socket
	 * @param not the notification
	 * @throws IOException
	 */
	static void writeProtocole(Socket soc,  Notification not) throws IOException {
	DataOutputStream out = new DataOutputStream(soc.getOutputStream());
	out.writeInt(not.ordinal());
	}
	/**
	 * 
	 * @param soc the socket 
	 * @return the notification
	 * @throws IOException
	 */
	static Notification readProtocole(Socket soc) throws IOException {
		DataInputStream in = new DataInputStream(soc.getInputStream());
		Notification not = Notification.values()[in.readInt()];
		return not;
	}
	
	static void writeData(Socket soc,String file) throws IOException{
		FileInputStream inf=new FileInputStream(new File("d:\\f.txt"));
        DataOutputStream out= new DataOutputStream(soc.getOutputStream());               
                byte buf[] = new byte[1024];
                int n;                   
                while((n=inf.read(buf))!=-1){
                   out.write(buf,0,n);                   
                }           
	}
	
	public static void fileTransfert(InputStream in, OutputStream out, boolean closeOnExit) throws IOException
    {
        byte buf[] = new byte[MAX_LEN_BUFFER];        
        int n=0;
        while((n=in.read(buf))!=-1)
            out.write(buf,0,n);
        
        if (closeOnExit)
        {
            in.close();
            out.close();
        }
    }
}
