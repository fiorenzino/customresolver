package by.giava.extensions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.extensions.annotations.Flower;

public class InejctComplessObjExtension implements Extension {

	// http://docs.jboss.org/weld/reference/1.1.0.Final/en-US/html_single/#d0e4939
	
	@Inject
	BeanManager beanManager;

	public InejctComplessObjExtension() {
		// TODO Auto-generated constructor stub
	}

	<X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {

		if (pit.getAnnotatedType().isAnnotationPresent(Named.class)) {
			System.out.println("scanning java class: "
					+ pit.getAnnotatedType().getJavaClass());

			// QUI DOVREI CICLARE SU TUTTI I CAMPI PER CERCARE LE ANNOTAZIONI
			// CHE CONOSCO, e segnarle per poi caricarle a runtime
			for (AnnotatedField<? super X> field : pit.getAnnotatedType()
					.getFields()) {
				System.out.println("campo: " + field.getJavaMember().getName());
				Set<Annotation> annotaz = field.getAnnotations();
				for (Annotation annotation : annotaz) {
					System.out.println("ann: "
							+ annotation.getClass().getSimpleName());
				}
			}
			String fieldName = "flower";
			boolean presente = false;
			if (pit.getAnnotatedType().isAnnotationPresent(Flower.class)) {
				System.out.println("e' presente flower");
				presente = true;
			}

			// wrap this to intercept the component lifecycle
			final InjectionTarget<X> it = pit.getInjectionTarget();
			final Map<Field, Object> configuredValues = new HashMap<Field, Object>();

			// use this to read annotations of the class and its members

			AnnotatedType<X> at = pit.getAnnotatedType();
			System.out.println("CLASS:" + at.getJavaClass().getCanonicalName());
			System.out.println("PK: "
					+ at.getJavaClass().getPackage().getName());
			// read the properties file

			String propsFileName = at.getJavaClass().getSimpleName()
					+ ".properties";
			System.out.println("file name: " + propsFileName);
			// InputStream stream = at.getJavaClass().getResourceAsStream(
			// propsFileName);
			Object stream = new Object();
			if (presente) {
				try {
					try {
						Field field = at.getJavaClass().getDeclaredField(
								fieldName);
						field.setAccessible(true);
						if (field.getType().isAssignableFrom(Flower.class)) {
							System.out.println("configuriamo:" + fieldName
									+ ".");
							configuredValues.put(field, "cosaAruntimeAsse");
						} else {
							// TODO: do type conversion automatically
							pit.addDefinitionError(new InjectionException(
									"field is not of type String: ." + field
											+ "."));
						}
					} catch (NoSuchFieldException nsfe) {
						pit.addDefinitionError(nsfe);
					} finally {
						System.out.println("chiudo lo stream");
					}

				} catch (Exception ioe) {
					pit.addDefinitionError(ioe);

				}

			}

			InjectionTarget<X> wrapped = new InjectionTarget<X>() {

				public void dispose(X instance) {
					it.dispose(instance);
				}

				public Set<InjectionPoint> getInjectionPoints() {
					return it.getInjectionPoints();
				}

				public X produce(CreationalContext<X> ctx) {
					return it.produce(ctx);

				}

				public void inject(X instance, CreationalContext<X> ctx) {
					it.inject(instance, ctx);
					// set the values onto the new instance of the component

					for (Map.Entry<Field, Object> configuredValue : configuredValues
							.entrySet()) {
						try {
							System.out.println("adesso vado inside:"
									+ configuredValue.getKey() + ".");
							configuredValue.getKey().set(instance,
									configuredValue.getValue());
						} catch (Exception e) {
							throw new InjectionException(e);
						}
					}
				}

				public void postConstruct(X instance) {
					it.postConstruct(instance);
				}

				public void preDestroy(X instance) {
					it.dispose(instance);
				}

			};

			pit.setInjectionTarget(wrapped);
		}
	}

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {

		System.out.println("scanning class: "
				+ pat.getAnnotatedType().getJavaClass().getName());

	}

}