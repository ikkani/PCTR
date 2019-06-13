import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class cIbex {
	public static void main(String[] args) {
		try {
			iIbex servidor = (iIbex) Naming.lookup("//localhost:2020//server");
			Scanner sc = new Scanner(System.in);
			String consulta = "";
			while (true) {
				System.out.println("Introduce tu consulta");
				consulta = sc.nextLine();
				if (consulta.equals(""))
					consulta = servidor.consultar();
				else consulta = servidor.consultar(consulta);
				System.out.println(consulta);
				
				
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
