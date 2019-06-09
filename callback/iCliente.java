import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iCliente extends Remote{
	public void nuevoMensaje(String msg) throws RemoteException;

}
