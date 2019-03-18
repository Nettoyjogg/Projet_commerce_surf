package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="administrateurs")
public class Administrateur implements Serializable{


	private static final long serialVersionUID = 1L;
	// déclaration des attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_admin")
	private int idAdmin;
	@Column(name = "nom_admin")
	private String nomAdmin;
	@Column(name = "prenom_admin")
	private String prenomAdmin;
	@Column(name = "mail_admin")
	private String mailAdmin;
	@Column(name = "mdp_admin")
	private String mdp;
	
	// transformation de l'association UML en JAVA
	
	//déclaration des constructeurs
	public Administrateur() {
		super();
	}
	public Administrateur(String nomAdmin, String prenomAdmin, String mailAdmin, String mdp) {
		super();
		this.nomAdmin = nomAdmin;
		this.prenomAdmin = prenomAdmin;
		this.mailAdmin = mailAdmin;
		this.mdp = mdp;
	}
	public Administrateur(int idAdmin, String nomAdmin, String prenomAdmin, String mailAdmin, String mdp) {
		super();
		this.idAdmin = idAdmin;
		this.nomAdmin = nomAdmin;
		this.prenomAdmin = prenomAdmin;
		this.mailAdmin = mailAdmin;
		this.mdp = mdp;
	}
	
	//déclaration des getters et setter
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public String getNomAdmin() {
		return nomAdmin;
	}
	public void setNomAdmin(String nomAdmin) {
		this.nomAdmin = nomAdmin;
	}
	public String getPrenomAdmin() {
		return prenomAdmin;
	}
	public void setPrenomAdmin(String prenomAdmin) {
		this.prenomAdmin = prenomAdmin;
	}
	public String getMailAdmin() {
		return mailAdmin;
	}
	public void setMailAdmin(String mailAdmin) {
		this.mailAdmin = mailAdmin;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	@Override
	public String toString() {
		return "Administrateur [idAdmin=" + idAdmin + ", nomAdmin=" + nomAdmin + ", prenomAdmin=" + prenomAdmin
				+ ", mailAdmin=" + mailAdmin + ", mdp=" + mdp + "]";
	}
	
	
	
}
