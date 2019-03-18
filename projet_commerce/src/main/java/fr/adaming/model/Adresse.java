package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class Adresse implements Serializable{


	private static final long serialVersionUID = 1L;
		// déclaration des attributs
	@Column(name = "numero_adresse_c")
		private String numero;
	@Column(name = "rue_adresse_c")
		private String rue;
	@Column(name = "codePostal_adresse_c")
		private String codePostal;
	@Column(name = "ville_adresse_c")
		private String ville;
	@Column(name = "pays_adresse_c")
		private String pays;

		// déclaration des constructeurs
		public Adresse() {
			super();
		}

		public Adresse(String numero, String rue, String codePostal, String ville, String pays) {
			super();
			this.numero = numero;
			this.rue = rue;
			this.codePostal = codePostal;
			this.ville = ville;
			this.pays = pays;
		}

		// getter et setter
		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public String getRue() {
			return rue;
		}

		public void setRue(String rue) {
			this.rue = rue;
		}

		public String getCodePostal() {
			return codePostal;
		}

		public void setCodePostal(String codePostal) {
			this.codePostal = codePostal;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public String getPays() {
			return pays;
		}

		public void setPays(String pays) {
			this.pays = pays;
		}

		
	}
