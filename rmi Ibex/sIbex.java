import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class sIbex extends UnicastRemoteObject implements iIbex{
	
	static HashMap<String, Pair<String,Double>> valores = new HashMap<String, Pair<String,Double>>();
	static Random r = new Random(System.nanoTime());
	
	public sIbex()throws RemoteException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce valor para TRI");
		valores.put("TRI", new Pair<String, Double>("TRINCOSA", sc.nextDouble()));
		System.out.println("Introduce valor para CHN");
		valores.put("CHN", new Pair<String, Double>("CHORIZOS NACIONALES SA", sc.nextDouble()));
		System.out.println("Introduce valor para BMM");
		valores.put("BMM", new Pair<String, Double>("BANCO MALO PERO MALO", sc.nextDouble()));
		System.out.println("Introduce valor para CRR");
		valores.put("CRR", new Pair<String, Double>("CORRUPTOLANDIA", sc.nextDouble()));
		System.out.println("Introduce valor para TDC");
		valores.put("TDC", new Pair<String, Double>("TOMA EL DINERO Y CORRE SA", sc.nextDouble()));
		sc.close();
	}
	
	public String consultar(String acronimo)throws RemoteException {
		if (!valores.containsKey(acronimo))
			return "El acrónimo no se corresponde con ninguna empresa";
		String aux = "El valor de la empresa " + valores.get(acronimo).first + " es " + valores.get(acronimo).second;
		return aux;
	}	
	public String consultar() throws RemoteException{
		String aux = "";
		for (String key: valores.keySet()) {
			aux += consultar(key) + "\n";
		}
		return aux;
	}
	
	static void cambiarValores() {
		for (String key: valores.keySet()) {
			double valor = valores.get(key).second + ((r.nextDouble()*2)-1);
			valores.get(key).second = valor;
		}
	}
	
	public static void main(String[] args)throws RemoteException {
		
		sIbex server = new sIbex();
		try {
			Naming.bind("//localhost:2020//server", server);
			System.out.println("Servidor listo");
			while (true) {
				cambiarValores();
				Thread.sleep(100);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

class Pair<V,K>{
	V first;
	K second;
	public Pair(V first, K second) {
		this.first = first;
		this.second = second;
	}
}
