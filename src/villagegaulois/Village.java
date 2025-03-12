package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		this.marche = new Marche(nbEtal);
		villageois = new Gaulois[nbVillageoisMaximum];
		
	}
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for (int i=0;i<nbEtal;i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int i = 0;
			while (etals[i].isEtalOccupe() && i<etals.length) {
				i++;
			}
			if (i == etals.length)  {
				return -1;
			} else {
				return i;
			}
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalOcc = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalOcc++;
				}
			}
			Etal[] etalsOcc = new Etal[nbEtalOcc];
			int j = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit)) {
					etalsOcc[j] = etals[i];
					j++;
				}
			}
			return etalsOcc;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			while (etals[i].getVendeur() != gaulois && i < etals.length) {
				i++;
			}
			return etals[i];
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].getVendeur().getNom() + " vend " + etals[i].getQuantite() + " " 
				+ etals[i].getProduit() + "\n");
				} else {
					nbEtalVide++;
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
		
	}
	
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre 20 fleurs.\n");
		int emplacement = marche.trouverEtalLibre();
		marche.utiliserEtal(emplacement,vendeur,produit,nbProduit);
		emplacement++;
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'etal n°" + emplacement);
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		switch(etals.length) {
		case 0:
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marché.\n");
			break;
		case 1:
			chaine.append("Seul le vendeur " + etals[0].getVendeur() + " propose des " + produit + " au marché.\n");
			break;
		default:
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i=0;i<etals.length;i++) {
				chaine.append("- " + etals[i].getVendeur() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = this.rechercherEtal(vendeur);
		return etal.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	
	
}