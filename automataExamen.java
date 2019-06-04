package PCTR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class automataExamen implements Runnable{

	static final int vacio = 0, frenteElec = 1, colaElec = 2, conductor = 3; 
	static int[][] reticulaT, reticulaT_1;
	static int d, n, m;
	int ini, fin;
	
	public automataExamen(int ini, int fin) {
		this.ini = ini;
		this.fin = fin;
	}
	
	public void run() {
		nextGen(ini, fin);
	}
	
	public void nextGen(int ini, int fin) {
		for (int i = ini; i <= fin; i++) {
			for (int j = 0; j < d; j++) {
				reticulaT_1[i][j] = actualizarCelula(i, j);
			}
		}
			
	}
	
	public int actualizarCelula(int x, int y) {
		int valor = 0;
		
		switch (reticulaT[x][y]) {
		
		case vacio:
			valor = vacio;
			break;
		case frenteElec:
			valor = colaElec;
			break;
		case colaElec:
			valor = conductor;
			break;
		case conductor:
			int numeroFrentesElec = 0;
			for (int i = x-1; i <= x+1; i++) {
				for (int j = y-1; j <= y-1; j++) {
					try{
						if (reticulaT[i][j] == frenteElec && (i != x || j != y))
							numeroFrentesElec++;
					}catch(Exception e) {}
						
				}
			}
			if (numeroFrentesElec == 1 || numeroFrentesElec == 2)
				valor = frenteElec;
			else valor = conductor;
			break;
		}
		return valor;
	}
	
	public static void inicializacionAleatoria() {
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				reticulaT[i][j] = ((int)Math.floor(Math.random()*4));
			}
		}
	}
	
	public static void inicializacionManual() {
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				int valor = -1;
				while (valor < 0 || valor >3) {
					System.out.println("Introduce el valor [" + i + "][" + j + "]");
					valor = sc.nextInt();					
				}
				reticulaT[i][j] = valor;
			}
		}
		sc.close();
	}
	
	public static void actualizarReticulas() {
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				reticulaT[i][j] = reticulaT_1[i][j];
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el tamaño de la reticula");
		d = sc.nextInt();
		System.out.println("Introduce el número de generaciones");
		n = sc.nextInt();
		System.out.println("Introduce el número de tareas paralelas");
		m = sc.nextInt();
		
		reticulaT = new int[d][d];
		reticulaT_1 = new int[d][d];
		
		System.out.println("Introduce 1 para rellenar la reticula aleatoriamente, cualquier otro número para rellenarla manualmente");
		int choose = sc.nextInt();
		if (choose == 1)
			inicializacionAleatoria();
		else inicializacionManual();
		sc.close();

		for (int it = 0; it < n; it++) {
			ThreadPoolExecutor miPool = new ThreadPoolExecutor ( Runtime.getRuntime().availableProcessors() , Runtime.getRuntime().availableProcessors(), 60000L, 
					TimeUnit.MILLISECONDS , new LinkedBlockingQueue < Runnable > ());
	
			int ini = 0, fin = 0;
			for (int i = 0; i < m-1; i++) {
				ini = i * (d/m);
				fin = (i+1) * ((d/m)-1);
				Runnable runnable = (new automataExamen(ini, fin));
				miPool.execute(runnable);
			}
			ini = fin+1;
			fin = d-1;
			Runnable runnable = (new automataExamen(ini, fin));
			miPool.execute(runnable);
			
			miPool.shutdown();
			System.out.println("GENERACION " + it + ":");
			for (int i = 0; i < d; i++) {
				for (int j = 0; j < d; j++)
					System.out.print(reticulaT[i][j] + " ");
				System.out.println();
			}
			System.out.println("__________________");
			actualizarReticulas();
		}
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++)
				System.out.print(reticulaT[i][j] + " ");
			System.out.println();
		}

	}
};
