import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class cliente {
	public static void main(String[] args) {
		try {
			iServidor server = (iServidor)Naming.lookup("//localhost:2020//server");
			Scanner sc = new Scanner(System.in);
			System.out.println("Introduce tu nickname");
			String nick = sc.nextLine();
			iCliente objetoCallBack = new impICliente(nick);
			server.registroCallBack(objetoCallBack);
			String mensaje = "";
			while(true) {
				mensaje = sc.nextLine();
				server.mandarMensaje(mensaje, nick);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
