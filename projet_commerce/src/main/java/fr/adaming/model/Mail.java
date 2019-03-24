package fr.adaming.model;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import fr.adaming.managedBeans.ListeCommandeManagedBean;

public class Mail {
	public static void send(String from, String password, String to, String sub, String msg) {
		// Ici on configure les paramètres de ta boite mail
		// Ils sont réglés pour gmail, donc utilise gmail si tu peux, en créant
		// un compte de merde.
		// De plus si tu utilises un compte gmail, il faut cocher ceci sur gmail
		// : "https://myaccount.google.com/lesssecureapps"
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// Ici, on crée ce qu'il y aura dans le message, pas besoin de modifier
		try {
			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setText(msg);

			// Multipart
			Multipart multipart = new MimeMultipart();

			// Corps du message
			BodyPart partieMessage = new MimeBodyPart();

			// Ajouter du texte au message
			partieMessage.setText(msg);
			multipart.addBodyPart(partieMessage);

			// Pièces jointes
			String chemin = "C:\\Users\\IN-BR-006\\PDFeCommerce\\FicheProduit"
					+ Integer.toString(ListeCommandeManagedBean.getNumero()) + ".pdf";	
			partieMessage = new MimeBodyPart();
			DataSource source = new FileDataSource(chemin);
			partieMessage.setDataHandler(new DataHandler(source));
			partieMessage.setFileName("Commande.pdf");
			multipart.addBodyPart(partieMessage);
			message.setContent(multipart);

			// send message
			Transport.send(message);
			// Décommenter pour vérifier que le message est envoyé
			System.out.println("Message envoyé");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}