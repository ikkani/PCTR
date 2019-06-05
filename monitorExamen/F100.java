package PCTR.monitorExamen;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class F100 {
	public static void main(String[] args) {
		CIWS ciws = new CIWS();
		ArrayList<combatStations> estaciones = new ArrayList<combatStations>(16);
		ExecutorService pool = Executors.newFixedThreadPool(16);
		
		
		for (int i = 0; i < 16; i++)
		{
			estaciones.add(new combatStations(ciws, i));
			pool.execute(estaciones.get(i));
		}
		
		pool.shutdown();
		while(!pool.isTerminated());
				
	}
}
