package it.jflower.cc.session;

import it.jflower.base.utils.Email2Send;
import it.jflower.base.utils.Sender;
import it.jflower.base.utils.Server;
import it.jflower.cc.par.Configurazione;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class EmailSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(EmailSession.class);

	@Inject
	EntityManager em;

	public String sendEmail(String from, String body, String title,
			String[] to, String[] cc, String[] bcc, File file) {

		Email2Send email = new Email2Send();
		email.setMittente(from);
		email.setCorpo(body);
		email.setOggetto(title);
		// GESTIRE DESTINATARIO O MITTENTE VUOTI/NULLI
		if (to != null) {
			for (String toDest : to) {
				email.addDestinatari(toDest);
			}
		}
		if (cc != null) {
			for (String ccDest : cc) {
				email.addCc(ccDest);
			}
		}
		if (bcc != null) {
			for (String bccDest : bcc) {
				email.addBcc(bccDest);
			}
		}
		if (file != null) {
			email.addAllegati(file);
		}
		try {
			String msgId = "";
			if (from != null && to != null) {
				msgId = Sender.send(email, getServer());
				logger.info("sendEmail: msgId:" + msgId);
			} else {
				logger.info("sendEmail: EMAIL NON INVIABILE");
			}
			return msgId;
		} catch (Exception e) {
			e.printStackTrace();

			return "";
		}

	}

	private Server getServer() {
		Configurazione conf = getConfigurazione();
		Server server = new Server();
		server.setAddress(conf.getSmtp());
		server.setUser(conf.getUsername());
		server.setPwd(conf.getPassword());
		server.setPort("" + conf.getServerPort());
		server.setAuth(conf.isWithAuth());
		server.setSsl(conf.isWithSsl());
		server.setDebug(conf.isWithDebug());
		return server;

	}

	public Configurazione getConfigurazione() {
		try {
			return em.find(Configurazione.class, 1L);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
