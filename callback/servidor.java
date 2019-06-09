import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class servidor extends UnicastRemoteObject implements iServidor{
	
	protected servidor() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	static ArrayList<iCliente> clientes = new ArrayList<iCliente>();
	static Object cerrojo = new Object(); 
	
	public void registroCallBack(iCliente objetoCallBack) throws RemoteException{
		clientes.add(objetoCallBack);		
	}
	
	public void mandarMensaje(String mensaje, String nick) throws RemoteException{
		synchronized (cerrojo) {
			String mensajeFinal = "[" + nick + "] " + mensaje;
			for (int i = 0; i < clientes.size(); ++i)
				clientes.get(i).nuevoMensaje(mensajeFinal);
		}
	}

	public static void main(String[] args)  {
		try {
			servidor server = new servidor();
			Naming.bind("//localhost:2020//server", server);
			System.out.println("Chat preparado");
			
		} catch (MalformedURLException | RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
