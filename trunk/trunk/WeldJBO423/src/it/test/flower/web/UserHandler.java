package it.test.flower.web;

import it.test.flower.ejb3.TestFlowerSession;
import it.test.flower.par.Utente;
import it.test.flower.web.annotations.TestFlower;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class UserHandler implements Serializable {

	private Utente utente;

	@Inject
	@TestFlower
	TestFlowerSession test;

	@PostConstruct
	public void initialize() {
		System.out
				.println(this.getClass().getSimpleName() + " was constructed");
		test.helloWorld("flower");
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
