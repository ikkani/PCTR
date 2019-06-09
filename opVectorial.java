package operacionesVectoriales;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class opVectorial implements Runnable{

	static float[] vA, vB, vC;
	static float[] vAux;
	int ini, fin;
	static int nHilos = 0;
	static float k = 0;
	static Semaphore semaforo = new Semaphore(1);
	static Semaphore semaforo2 = new Semaphore(0);
	
	public opVectorial(int ini, int fin) {
		this.ini = ini;
		this.fin = fin;
	}
	
	public void run() {
		try {
			//Va + Vb
		for (int i = ini; i < fin; ++i) {
			vAux[i] = vA[i]+vB[i];
		}
		//(Va + Vb) * Vc
		for (int i = ini; i < fin; ++i) {
			vAux[i] = vAux[i] * vC[i];
		}
		//Modulo
		float sumaParcial = 0;
		for (int i = ini; i < fin; ++i) {
			sumaParcial += vAux[i] * vAux[i];
		}
		//RegionCritica
		semaforo.acquire();
		nHilos++;
		k+= sumaParcial;
		if (nHilos == 4) {
			k = (float)Math.sqrt(k);
			semaforo2.release(4);
		}
		semaforo.release();
		semaforo2.acquire();
		//Va * k
		for (int i = ini; i < fin; ++i) {
			vAux[i] = vA[i]*k;
		}
		semaforo2.release();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		vA = new float[] {1,1,1,1};
		vB = new float[] {1,1,1,1};
		vC = new float[] {1,1,1,1};
		vAux = new float[4];
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 4, 6000, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
		
		int ini = 0, fin = 0;
		
		for (int i = 0; i < 4-1; i++) {
			ini = fin;
			fin = (i+1) * (vA.length/4);
			pool.execute(new opVectorial(ini, fin));
		}
		ini = fin;
		fin = vA.length;
		pool.execute(new opVectorial(ini, fin));
		
		pool.shutdown();
		
		try {
			pool.awaitTermination(1, TimeUnit.DAYS);
			System.out.println(Arrays.toString(vAux));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
