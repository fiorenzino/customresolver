package it.jflower.base.par;

import java.io.Serializable;

public class Ricerca<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T oggetto;

	public Ricerca(Class<T> t) {
		this.oggetto = init(t);
	}

	public Ricerca(T o) {
		this.oggetto = o;
	}

	private T init(Class<T> t) {
		try {
			return t.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public T getOggetto() {
		return oggetto;
	}

	public void setOggetto(T t) {
		this.oggetto = t;
	}

}
