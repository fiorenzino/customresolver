package it.test.flower.web.producer;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.naming.InitialContext;

import it.test.flower.ejb3.TestFlowerSession;
import it.test.flower.web.annotations.Log;
import it.test.flower.web.annotations.TestFlower;

@ApplicationScoped
@Named
public class JNDIProducer {

	private static final long serialVersionUID = 1L;

	private static TestFlowerSession testFlowerSession = null;

	@Produces
	@Log
	Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}

	@Produces
	@TestFlower
	public TestFlowerSession getTestFlowerSessionBean() {
		if (testFlowerSession != null) {
			return testFlowerSession;
		} else {
			try {
				InitialContext ctx = new InitialContext();
				testFlowerSession = (TestFlowerSession) ctx
						.lookup("weld1/TestFlowerSessionBean/local");
				return testFlowerSession;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
