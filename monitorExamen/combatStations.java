package PCTR.monitorExamen;

public class combatStations implements Runnable{
	
	CIWS ciws = new CIWS();
	int id;
	
	public combatStations(CIWS ciws, int id) {
		this.ciws = ciws;
		this.id   = id;
	}
	
	public void run() {
		while(true) {
			try {
				ciws.cogerArmamento(id);
				ciws.soltarArmamento(id);
			}catch(Exception e) {
			
			}
		}
	}
	
	
}
