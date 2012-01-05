package by.giava.base.service;


import java.io.File;
import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;

import by.giava.base.common.util.Email2Send;
import by.giava.base.common.util.Sender;
import by.giava.base.common.util.Server;
import by.giava.base.model.Configurazione;

@Named
@Stateless
@LocalBean
public class EmailSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(EmailSession.class);

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
