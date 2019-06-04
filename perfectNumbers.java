package PCTR;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class perfectNumbers implements Callable{

	static int n;
	int ini, fin;
	
	public perfectNumbers(int ini, int fin) {
		this.ini = ini;
		this.fin = fin;
	}
	
	public Object call() throws Exception {
		int nPerfectos = 0;
		for (int i = ini; i <= fin; i++) {
			if (esPerfecto(i))  
				nPerfectos++;	
		}
		return nPerfectos;
	}
	
	public static boolean esPerfecto (int numero) {
		int sumaDivisores = 0;
		for (int aux = numero-1; aux >=1; aux--) {
			if ((numero % aux) == 0)
				sumaDivisores +=aux;
		}
		
		if (sumaDivisores == numero) 
			return true; 
		else return false;
	}
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		n = 100000;
		int nHilos = 16;
		ExecutorService executor = Executors.newFixedThreadPool(nHilos);
		ArrayList<Future<Integer>> numeroDePerfectos = new ArrayList<Future<Integer>>();
		int ini = 0, fin = 0, i;
		for (i = 0; i < nHilos-1; i++) {
			ini = (i)* (n/nHilos) + 1;
			fin = ((i+1) * (n/nHilos));
			Callable<Integer> callable = new perfectNumbers(ini,fin);
			numeroDePerfectos.add(executor.submit(callable));
		}
		ini = fin+1;
		fin = n;
		Callable<Integer> callable = new perfectNumbers(ini, fin);
		numeroDePerfectos.add(executor.submit(callable));
		executor.submit(callable);
		
		Integer total = 0;
		
		for (i = 0; i < numeroDePerfectos.size(); i++) {
			try {
				total += (numeroDePerfectos.get(i)).get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Hay " + total + " número(s) perfecto(s)\nTiempo de ejecución: " + ((System.currentTimeMillis()-time)/1000.0) + " segundos");
	}
	
}
