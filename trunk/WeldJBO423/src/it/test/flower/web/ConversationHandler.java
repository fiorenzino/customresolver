package it.test.flower.web;

import it.test.flower.ejb3.TestFlowerSession;
import it.test.flower.par.Utente;
import it.test.flower.web.annotations.Log;
import it.test.flower.web.annotations.TestFlower;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named
public class ConversationHandler implements Serializable {

	public ConversationHandler() {
		// TODO Auto-generated constructor stub
	}

	private Utente utente;

	@Inject
	@TestFlower
	TestFlowerSession test;

	@Inject
	@Log
	transient Logger log;

	// @Inject
	// Conversation conversation;

	@PostConstruct
	public void initialize() {

		log.info("create: " + this.getClass().getSimpleName());
		test.helloWorld("flower");
	}

	@PreDestroy
	public void delete() {
		log.info("destroy: " + this.getClass().getSimpleName());
	}

	public String addUser1() {
		// conversation.begin();
		this.utente = new Utente();
		return "addUser1";
	}

	public String addUser2() {

		return "addUser2";
	}

	public String addUser3() {
		// conversation.end();
		return "addUser3";
	}

	public Utente getUtente() {
		if (this.utente == null)
			this.utente = new Utente();
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}
