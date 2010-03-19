package it.test.flower.ejb3;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(TestFlowerSession.class)
public class TestFlowerSessionBean implements TestFlowerSession {

	public void helloWorld(String nome) {
		System.out.println("hello " + nome);

	}

}
