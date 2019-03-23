package fr.adaming.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.model.Client;
import fr.adaming.model.Produit;

@Service("pdfService")
@Transactional

public class PDFService implements IPDFService {

	// Méthode pour créer le PDF et le placer à l'emplacement ci-dessus
	// public int creerPDF(Produit prod) {
	public int creerPDF(List<Produit> produitListe, Client client, int numero) {
		String chemin = "C:\\Users\\IN-BR-006\\PDFeCommerce\\FicheProduit" + Integer.toString(numero) + ".pdf";

		// Vérificateur de fonctionnement
		int verifPDF = 0;

		Document doc = new Document();
		try {
			// créer le pdf à l'endroit voulu sur le pc (chemin)
			PdfWriter.getInstance(doc, new FileOutputStream(chemin));

			// ouvrir le doc pour faire des modifs
			doc.open();

			// Ajout du contenu
			doc.addTitle("Fiche de commande");
			doc.add(new Paragraph(client.toString()));
			doc.add(new Paragraph("Bonjour,"));
			doc.add(new Paragraph(
					"Voici les différents produits que vous avez commandé, le prix est le prix total : "));
			doc.add(new Paragraph(" "));

			// ajout du tableau (méthode en dessous)
			for (int i = 0; i < produitListe.size(); i++) {
				Produit prodTab = produitListe.get(i);
				doc.add(addTableau(prodTab));
				doc.add(new Paragraph(" "));
			}
			doc.add(new Paragraph(
					"Nous vous remerçions d'avoir passé votre commande chez Surf'n Sail, en espérant vous revoir bientôt"));
			verifPDF++;

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException eio) {
			eio.printStackTrace();
		}

		// Fermeture du document
		doc.close();
		numero++;

		// 1 si tout va bien
		return verifPDF;
	}

	public int creerPDFEchec(List<Produit> produitListe, Client client, int numero) {
		String chemin = "C:\\Users\\IN-BR-006\\PDFeCommerce\\FicheProduit" + Integer.toString(numero) + ".pdf";

		// Vérificateur de fonctionnement
		int verifPDF = 0;

		Document doc = new Document();
		try {
			// créer le pdf à l'endroit voulu sur le pc (chemin)
			PdfWriter.getInstance(doc, new FileOutputStream(chemin));

			// ouvrir le doc pour faire des modifs
			doc.open();

			// Ajout du contenu
			doc.addTitle("Fiche Echec d'envoi de la commande ");
			doc.add(new Paragraph("Bonjour,"));
			doc.add(new Paragraph(
					"Un client n'a pas pu valider sa commande, car son mail ne fonctionnait pas, elle a donc été annulé. Recontactez le "));
			doc.add(new Paragraph(" "));
			doc.add(new Paragraph(" Les produits qu'il voulait acheter sont ci-dessous :  "));

			// ajout du tableau (méthode en dessous)
			for (int i = 0; i < produitListe.size(); i++) {
				Produit prodTab = produitListe.get(i);
				doc.add(addTableau(prodTab));
				doc.add(new Paragraph(" "));
			}
			doc.add(new Paragraph("Le client était:"));
			doc.add(new Paragraph(client.toString()));
			verifPDF++;

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException eio) {
			eio.printStackTrace();
		}

		// Fermeture du document
		doc.close();
		numero++;

		// 1 si tout va bien
		return verifPDF;
	}

	// Méthode pour créer un tableau dans le pdf
	public static PdfPTable addTableau(Produit prod) {

		// créer un tableau (deux colonnes)
		PdfPTable tableau = new PdfPTable(2);

		// créer un objet cellule
		PdfPCell cellule;

		// Fusion des cellules de la premiere ligne
		cellule = new PdfPCell(new Phrase("Descriptif du produit"));
		cellule.setColspan(2);
		tableau.addCell(cellule);

		// Remplissage du tableau
		// Tableau de deux colonnes, donc on rempli la colonne de gauche, puis
		// celle de droite.
		tableau.addCell("Désignation");
		tableau.addCell(prod.getDesignation());

		tableau.addCell("Description");
		tableau.addCell(prod.getDescription());

		tableau.addCell("Image");
		tableau.addCell(prod.getImg());

		tableau.addCell("Quantité");
		tableau.addCell(Integer.toString(prod.getQuantite()));

		// Transformer double en String
		double prix = prod.getPrix() * prod.getQuantite();
		String prix1 = String.valueOf(prix);
		tableau.addCell("Prix");
		tableau.addCell(prix1);

		tableau.addCell("Nom Catégorie");
		tableau.addCell(prod.getCategorie().getNomCategorie());

		return tableau;
	}

}
