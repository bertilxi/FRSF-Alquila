/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.logica.gestores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import app.datos.entidades.Archivo;
import javafx.concurrent.Task;

@Service
public class GestorEmail {
	/** Application name. */
	private static final String APPLICATION_NAME =
			"Olimpo";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".credentials/olimpo");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY =
			JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials
	 * at ~/.credentials/gmail-java-quickstart
	 */
	private static final List<String> SCOPES =
			Arrays.asList(GmailScopes.MAIL_GOOGLE_COM, GmailScopes.GMAIL_MODIFY, GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_SEND);

	static{
		try{
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch(Throwable t){
			t.printStackTrace();
			System.exit(1);
		}
	}

	public void enviarEmail(String destinatario, String asunto, String mensaje, Archivo archivo) throws IOException, MessagingException {

		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				// Build a new authorized API client service.
				Gmail service = getGmailService();

				// Print the labels in the user's account.
				String user = "me";

				File archivoTMP = new File("reserva.pdf");
				FileOutputStream fos = new FileOutputStream(archivoTMP);
				fos.write(archivo.getArchivo());
				fos.flush();
				fos.close();
				sendMessage(service, user, createEmailWithAttachment(destinatario, "olimpoagilinmobiliaria2016@gmail.com", asunto, mensaje, archivoTMP));
				return true;
			}
		};
		Thread hiloEnvioDeEmail = new Thread(task);
		hiloEnvioDeEmail.setDaemon(true);
		hiloEnvioDeEmail.start();
	}

	/**
	 * Creates an authorized Credential object.
	 *
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	private static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in =
				GestorEmail.class.getResourceAsStream("/res/client_secret.json");
		GoogleClientSecrets clientSecrets =
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
								.setDataStoreFactory(DATA_STORE_FACTORY)
								.setAccessType("offline")
								.build();
		Credential credential = new AuthorizationCodeInstalledApp(
				flow, new LocalServerReceiver()).authorize("user");
		System.out.println(
				"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Gmail client service.
	 *
	 * @return an authorized Gmail client service
	 * @throws IOException
	 */
	private static Gmail getGmailService() throws IOException {
		Credential credential = authorize();
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	/**
	 * Create a MimeMessage using the parameters provided.
	 *
	 * @param to
	 *            Email address of the receiver.
	 * @param from
	 *            Email address of the sender, the mailbox account.
	 * @param subject
	 *            Subject of the email.
	 * @param bodyText
	 *            Body text of the email.
	 * @param file
	 *            Path to the file to be attached.
	 * @return MimeMessage to be used to send email.
	 * @throws MessagingException
	 */
	private static MimeMessage createEmailWithAttachment(String to,
			String from,
			String subject,
			String bodyText,
			File file)
			throws MessagingException, IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		MimeMessage email = new MimeMessage(session);

		email.setFrom(new InternetAddress(from));
		email.addRecipient(javax.mail.Message.RecipientType.TO,
				new InternetAddress(to));
		email.setSubject(subject);

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(bodyText, "text/plain");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		mimeBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);

		mimeBodyPart.setDataHandler(new DataHandler(source));
		mimeBodyPart.setFileName(file.getName());

		multipart.addBodyPart(mimeBodyPart);
		email.setContent(multipart);

		return email;
	}

	/**
	 * Create a message from an email.
	 *
	 * @param emailContent
	 *            Email to be set to raw of message
	 * @return a message containing a base64url encoded email
	 * @throws IOException
	 * @throws MessagingException
	 */
	private static Message createMessageWithEmail(MimeMessage emailContent)
			throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}

	/**
	 * Send an email from the user's mailbox to its recipient.
	 *
	 * @param service
	 *            Authorized Gmail API instance.
	 * @param userId
	 *            User's email address. The special value "me"
	 *            can be used to indicate the authenticated user.
	 * @param emailContent
	 *            Email to be sent.
	 * @return The sent message
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static Message sendMessage(Gmail service,
			String userId,
			MimeMessage emailContent)
			throws MessagingException, IOException {
		Message message = createMessageWithEmail(emailContent);
		message = service.users().messages().send(userId, message).execute();

		System.out.println("Message id: " + message.getId());
		System.out.println(message.toPrettyString());
		return message;
	}

}