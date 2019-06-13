import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iIbex extends Remote{

	public String consultar(String acronimo)throws RemoteException;
	public String consultar()throws RemoteException;
	
}
