package PCTR.monitorExamen;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CIWS {
	
	ReentrantLock cerrojo;
	Condition sistemaLibres, tieneArmamento;
	
	boolean[] armamento;
	int [] estacionesArmamento;
	
	public CIWS(){
		cerrojo = new ReentrantLock();
		armamento = new boolean[4];
		for (int i = 0; i < 4; i++)
			armamento[i] = true;
		
		estacionesArmamento = new int[16];
		for (int i = 0; i < estacionesArmamento.length; i++)
			estacionesArmamento[i] = 0;
		
		sistemaLibres = cerrojo.newCondition();
		tieneArmamento = cerrojo.newCondition();
	}
	
	public boolean hayDisponible() {
		boolean hay = false;
		for (int i = 0; i < armamento.length && !hay; i++)
			hay = armamento[i];
		return hay;
	}
	
	public void cogerArmamento(int id) throws InterruptedException {
		
		cerrojo.lock();
		try {
			while(!hayDisponible())
				sistemaLibres.await();
			
			boolean cogido = false;
			for(int i = 0; i < armamento.length && !cogido; i++) {
				if (armamento[i]) {
					cogido = true;
					armamento[i] = false;
					estacionesArmamento[id] = i+1;
					System.out.println("La estación " + id + " ha cogido el armamento " + (i+1) + ".");
				}
			}
			tieneArmamento.signalAll();
		}finally {
			cerrojo.unlock();
		}
	}
	
	public void soltarArmamento(int id) throws InterruptedException {
		
		cerrojo.lock();
		try {
			while (estacionesArmamento[id] == 0)
				tieneArmamento.await();
			System.out.println("La estación " + id + " ha soltado el armamento " + estacionesArmamento[id]);
			armamento[estacionesArmamento[id]-1] = true;
			estacionesArmamento[id] = 0;
			sistemaLibres.signalAll();
			
		}finally {
			cerrojo.unlock();
		}
	}

}
