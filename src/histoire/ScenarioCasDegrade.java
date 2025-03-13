package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Gaulois acheteur = new Gaulois("acheteur",2);
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test 1");
		try {
			etal.acheterProduit(1,acheteur);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin test 2");
		
		
	}
	
}
