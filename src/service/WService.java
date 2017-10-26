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
	
	
	/***************************************************/
	/***************Partie sur les adhérents **************/
	/*****************************************************/
	@POST
	@Path("/Adherents/ajout/{unAdh}")
	@Consumes("application/json")	
	public void insertionAdherent(String unAdherent) throws MonException {
		DialogueBd unDialogueBd = DialogueBd.getInstance();
		Gson gson = new Gson();
		Adherent unAdh = gson.fromJson(unAdherent, Adherent.class);
		try {
			String mysql = "";
			mysql = "INSERT INTO adherent (nom_adherent, prenom_adherent, ville_adherent) ";
			mysql += " VALUES ( \'" + unAdh.getNomAdherent()+ "\', \'" + unAdh.getPrenomAdherent();
			mysql+="  \', \'"  + unAdh.getVilleAdherent() +  "\') ";
			
			unDialogueBd.insertionBD(mysql);
			
		} catch (MonException e) {
			throw e;
		}
	}
	
	
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

				// On crï¿½e un objet Adherent
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

	@GET
	@Path("/Oeuvres")
	@Produces("application/json")
	public String rechercheLesOeuvres() throws MonException {
		try {
			List<Object> rs;
			List<Oeuvrevente> mesOeuvres = new ArrayList<>();

			DialogueBd unDialogueBd = DialogueBd.getInstance();
			String mysql = "SELECT * FROM oeuvrevente ORDER BY id_oeuvrevente ASC";

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
				uneOeuvre.setProprietaire(proprietaire);

				index = index + 5;

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

//	@POST
//	@Path("/Oeuvres/ajout/{id}")
//	@Consumes("application/json")
//	public void insertionOeuvre(String uneOeuvre) {
//		DialogueBd unDialogueBd = DialogueBd.getInstance();
//		Gson gson = new Gson();
//		Adherent unAdh = gson.fromJson(uneOeuvre, Adherent.class);
//		try {
//			String mysql = "";
//			mysql = "INSERT INTO adherent (titre_oeuvrevente, etat_oeuvrevente, prix_oeuvrevente, id_proprietaire) ";
//			mysql += " VALUES ( \'" + unAdh.getNomAdherent()+ "\', \'" + unAdh.getPrenomAdherent();
//			mysql+="  \', \'"  + unAdh.getVilleAdherent() +  "\') ";
//
//			unDialogueBd.insertionBD(mysql);
//
//		} catch (MonException e) {
//			throw e;
//		}
//	}
}
