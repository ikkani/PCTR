import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class impICliente extends UnicastRemoteObject implements iCliente{
	String nick;
	protected impICliente(String nick) throws RemoteException {
		super();
		this.nick = nick;
		// TODO Auto-generated constructor stub
	}

	public void nuevoMensaje(String msg) throws RemoteException{
		System.out.println(msg);
	}

}
