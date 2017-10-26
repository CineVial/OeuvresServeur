package metier;

import java.util.*;

import persistance.DialogueBd;
import meserreurs.MonException;

public class Oeuvre {

	private int identifiant;
	private String titre;
	private String etat;
	private Float prix;
	private int idproprietaire;

	public Oeuvre() {

	}

	public Oeuvre(int identifiant, String titre, String etat, Float prix, int idproprietaire) {
		this.identifiant = identifiant;
		this.titre = titre;
		this.etat = etat;
		this.prix = prix;
		this.idproprietaire = idproprietaire;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public int getIdentifiant() {
		return this.identifiant;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public Float getPrix() {
		return this.prix;
	}

	public void setidproprietaire(int idproprietaire) {
		this.idproprietaire = idproprietaire;

	}

	public int getidproprietaire() {
		return this.idproprietaire;
	}

	public void insertionOeuvre(Oeuvre uneOeuvre) throws MonException {

		try {
			String mysql = "";
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			mysql = "INSERT INTO oeuvrevente (titre_oeuvrevente, etat_oeuvrevente, prix_oeuvrevente, id_proprietaire) ";
			mysql = mysql + " VALUES ( \'" + uneOeuvre.getTitre() + "\', \'" + uneOeuvre.getEtat() + "\', " + uneOeuvre.getPrix()
					+ ", " + uneOeuvre.getidproprietaire()  +")";
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	public void modificationOeuvre(Oeuvre uneOeuvre) throws MonException {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			String mysql = "";

			mysql = "UPDATE oeuvrevente  ";
			mysql = mysql + "SET titre_oeuvrevente = \'" + uneOeuvre.getTitre() + "\' , etat_oeuvrevente = \'"
					+ uneOeuvre.getEtat() + "\', " + "prix_oeuvrevente = " + uneOeuvre.getPrix() + ", id_proprietaire = "
					+ uneOeuvre.getidproprietaire();
			mysql = mysql + " WHERE  id_oeuvrevente = " + uneOeuvre.getIdentifiant();
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			throw e;
		}
	}

	public void suppressionOeuvre(Oeuvre uneOeuvre) throws MonException {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		try {
			String mysql = "";

			mysql = "DELETE FROM oeuvrevente ";
			mysql = mysql + " WHERE  id_oeuvrevente = " + uneOeuvre.getIdentifiant();
			unDialogueBd.insertionBD(mysql);

		} catch (MonException e) {
			throw e;
		}
	}

	public Oeuvre getOeuvre(int Identifiant) throws MonException {
		List<Object> rs;
		Oeuvre uneOeuvre = new Oeuvre();

		int index = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "";

			mysql = "SELECT * FROM oeuvrevente WHERE id_oeuvrevente = " + Identifiant;

			rs = unDialogueBd.lecture(mysql);

			while (index < rs.size()) {

				uneOeuvre.setIdentifiant(Integer.parseInt(rs.get(index + 0).toString()));
				uneOeuvre.setTitre(rs.get(index + 1).toString());
				uneOeuvre.setEtat(rs.get(index + 2).toString());
				uneOeuvre.setPrix(Float.parseFloat(rs.get(index + 3).toString()));
				uneOeuvre.setidproprietaire(Integer.parseInt(rs.get(index + 4).toString()));
				index = index + 5;

			}

			return uneOeuvre;

		} catch (MonException e) {
			System.out.println(e.getMessage());
			throw e;
		}

	}

	public List<Oeuvre> rechercheLesOeuvres() throws MonException {
		List<Object> rs;
		List<Oeuvre> mesOeuvres = new ArrayList<Oeuvre>();
		int index = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "";

			mysql = "SELECT * FROM oeuvrevente ORDER BY id_oeuvrevente ASC";

			rs = unDialogueBd.lecture(mysql);

			while (index < rs.size()) {

				// On cree un objet OEUVRE
				Oeuvre unOv = new Oeuvre();
				unOv.setIdentifiant(Integer.parseInt(rs.get(index + 0).toString()));
				unOv.setTitre(rs.get(index + 1).toString());
				unOv.setEtat(rs.get(index + 2).toString());
				unOv.setPrix(Float.parseFloat(rs.get(index + 3).toString()));
				unOv.setidproprietaire(Integer.parseInt(rs.get(index + 4).toString()));

				index = index + 5;

				mesOeuvres.add(unOv);
			}

			return mesOeuvres;

		} catch (MonException e) {
			System.out.println(e.getMessage());
			throw e;
		}

	}
}
