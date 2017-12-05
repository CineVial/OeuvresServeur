package service;

import com.google.gson.Gson;
import meserreurs.MonException;
import metier.Adherent;
import metier.Oeuvrevente;
import metier.Proprietaire;
import persistance.DialogueBd;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("/mediatheque")
public class WService {

	/* AJOUT & MODIFICATION */
	@POST
	@Path("/adherents/insertion")
	@Consumes("application/json")
	public String insertionAdherent(String unAdherent) {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		Gson gson = new Gson();
		Adherent unAdh = gson.fromJson(unAdherent, Adherent.class);
		try {
			String mysql;
			if(unAdh.getIdAdherent() == 0) {
				mysql = "INSERT INTO adherent (nom_adherent, prenom_adherent, ville_adherent) ";
				mysql += " VALUES ( \'" + unAdh.getNomAdherent()+ "\', \'" + unAdh.getPrenomAdherent();
				mysql+="  \', \'"  + unAdh.getVilleAdherent() +  "\') ";
			}
			else {
				mysql = "UPDATE adherent " +
						"SET nom_adherent = '" + unAdh.getNomAdherent() + "', " +
						"prenom_adherent = '" + unAdh.getPrenomAdherent() + "', " +
						"ville_adherent = '" + unAdh.getVilleAdherent() + "' " +
						"WHERE id_adherent = " + unAdh.getIdAdherent() + ";";
			}
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			e.printStackTrace();
		}

		return "OK";
	}

	/* GET ALL */
	@GET
	@Path("/Adherents")
	@Produces("application/json")
	public String rechercheLesAdherents() throws MonException {
		List<Object> rs;
		List<Adherent> mesAdherents = new ArrayList<Adherent>();
		int index = 0;
		try {
			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "";

			mysql = "SELECT * FROM adherent ORDER BY id_adherent ASC";

			rs = unDialogueBd.lecture(mysql);

			while (index < rs.size()) {

				// On cr?e un objet Adherent
				Adherent unAdh = new Adherent();
				unAdh.setIdAdherent(Integer.parseInt(rs.get(index + 0).toString()));
				unAdh.setNomAdherent(rs.get(index + 1).toString());
				unAdh.setPrenomAdherent(rs.get(index + 2).toString());
				unAdh.setVilleAdherent(rs.get(index + 3).toString());
				index = index + 4;

				mesAdherents.add(unAdh);
			}

			Gson gson = new Gson();
			String json = gson.toJson(mesAdherents);
			return json;

		} catch (MonException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/* DELETE */
	@DELETE
	@Path("/adherents/suppression/{unAdh}")
	public String suppressionAdherent(@PathParam("unAdh") int id) {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		Gson gson = new Gson();

		try {
			String mysql;
			mysql = "DELETE FROM `adherent` WHERE id_adherent = " + id + ";";
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			e.printStackTrace();
		}

		return "OK";
	}


	@GET
	@Path("/Oeuvres")
	@Produces("application/json")
	public String rechercheLesOeuvres() throws MonException {
		try {
			List<Object> rs;
			List<Oeuvrevente> mesOeuvres = new ArrayList<>();

			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "SELECT * FROM oeuvrevente o LEFT JOIN proprietaire p ON o.id_proprietaire = p.id_proprietaire ORDER BY id_oeuvrevente ASC";

			rs = unDialogueBd.lecture(mysql);

			int index = 0;

			while (index < rs.size()) {

				Oeuvrevente uneOeuvre = new Oeuvrevente();

				uneOeuvre.setIdOeuvrevente(Integer.parseInt(rs.get(index + 0).toString()));
				uneOeuvre.setTitreOeuvrevente(rs.get(index + 1).toString());
				uneOeuvre.setEtatOeuvrevente(rs.get(index + 2).toString());
				uneOeuvre.setPrixOeuvrevente(Float.parseFloat(rs.get(index + 3).toString()));

				Proprietaire proprietaire = new Proprietaire();
				proprietaire.setIdProprietaire(Integer.parseInt(rs.get(index + 4).toString()));
				proprietaire.setNomProprietaire(rs.get(index + 6).toString());
				proprietaire.setPrenomProprietaire(rs.get(index + 7).toString());

				uneOeuvre.setProprietaire(proprietaire);

				index = index + 8;

				mesOeuvres.add(uneOeuvre);
			}

			Gson gson = new Gson();
			String json = gson.toJson(mesOeuvres);

			return json;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@GET
	@Path("/Oeuvres/{id}")
	@Produces("application/json")
	public String rechercheUneOeuvre(@PathParam("id") String id) throws MonException {
		try {
			List<Object> rs;

			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "SELECT * FROM oeuvrevente WHERE id_oeuvrevente = " + id;

			rs = unDialogueBd.lecture(mysql);

			Oeuvrevente uneOeuvre = new Oeuvrevente();

			uneOeuvre.setIdOeuvrevente(Integer.parseInt(rs.get(0).toString()));
			uneOeuvre.setTitreOeuvrevente(rs.get(1).toString());
			uneOeuvre.setEtatOeuvrevente(rs.get(2).toString());

			Proprietaire proprietaire = new Proprietaire();
			proprietaire.setIdProprietaire(Integer.parseInt(rs.get(4).toString()));

			mysql = "SELECT * FROM proprietaire WHERE id_proprietaire = " + proprietaire.getIdProprietaire();
			rs = unDialogueBd.lecture(mysql);
			proprietaire.setNomProprietaire(rs.get(1).toString());
			proprietaire.setPrenomProprietaire(rs.get(2).toString());

			uneOeuvre.setProprietaire(proprietaire);

			Gson gson = new Gson();
			String json = gson.toJson(uneOeuvre);

			return json;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@POST
	@Path("/oeuvres/insertion")
	@Consumes("application/json")
	public String insertionOeuvre(String uneOeuvre) {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		Gson gson = new Gson();
		Oeuvrevente uneOeuvrevente = gson.fromJson(uneOeuvre, Oeuvrevente.class);
		try {
			String mysql;
			if(uneOeuvrevente.getIdOeuvrevente() == 0) {
				mysql = "INSERT INTO oeuvrevente (titre_oeuvrevente, etat_oeuvrevente, prix_oeuvrevente, id_proprietaire) ";
				mysql += " VALUES ( \'" + uneOeuvrevente.getTitreOeuvrevente()+ "\', \'" + uneOeuvrevente.getEtatOeuvrevente();
				mysql += "\', \'" + uneOeuvrevente.getPrixOeuvrevente()+ "\', \'" + uneOeuvrevente.getProprietaire().getIdProprietaire() + "\')";
			}
			else {
				mysql = "UPDATE oeuvrevente " +
						"SET titre_oeuvrevente = '" + uneOeuvrevente.getTitreOeuvrevente() + "', " +
						"etat_oeuvrevente = '" + uneOeuvrevente.getEtatOeuvrevente() + "', " +
						"prix_oeuvrevente = '" + uneOeuvrevente.getPrixOeuvrevente() + "', " +
						"id_proprietaire = '" + uneOeuvrevente.getProprietaire().getIdProprietaire() + "' " +
						"WHERE id_oeuvrevente = '" + uneOeuvrevente.getIdOeuvrevente() + "'";
			}
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			e.printStackTrace();
		}

		return "OK";
	}

	@DELETE
	@Path("/oeuvres/suppression/{uneOeuvre}")
	public String suppressionOeuvre(@PathParam("uneOeuvre") int id) {
		DialogueBd unDialogueBd = DialogueBd.getInstance();

		try {
			String mysql;
			mysql = "DELETE FROM `oeuvrevente` WHERE id_oeuvrevente = " + id + ";";
			unDialogueBd.insertionBD(mysql);
		} catch (MonException e) {
			e.printStackTrace();
		}

		return "OK";
	}

	@GET
	@Path("/Proprietaires")
	@Produces("application/json")
	public String rechercheLesProprietaires() throws MonException {
		try {
			List<Object> rs;
			List<Proprietaire> proprietaires = new ArrayList<>();

			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "SELECT * FROM proprietaire ORDER BY id_proprietaire ASC";

			rs = unDialogueBd.lecture(mysql);

			int index = 0;

			while (index < rs.size()) {

				Proprietaire proprietaire = new Proprietaire();

				proprietaire.setIdProprietaire(Integer.parseInt(rs.get(index + 0).toString()));
				proprietaire.setNomProprietaire(rs.get(index + 1).toString());
				proprietaire.setPrenomProprietaire(rs.get(index + 2).toString());

				index = index + 3;

				proprietaires.add(proprietaire);
			}

			Gson gson = new Gson();

			return gson.toJson(proprietaires);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
}
