import java.rmi.*;

public interface iServidor extends Remote{
	public void registroCallBack(iCliente objetoCallBack) throws RemoteException;
	public void mandarMensaje(String mensaje, String nick) throws RemoteException;
}
