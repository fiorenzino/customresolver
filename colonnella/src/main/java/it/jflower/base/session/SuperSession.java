package it.jflower.base.session;

import it.jflower.base.par.Ricerca;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public abstract class SuperSession<T> {

	abstract public EntityManager getEm();
	abstract public void setEm(EntityManager em);

	// --- Logger -------------------------------
	
	protected Logger logger = Logger.getLogger(getClass().getName());

	// --- Mandatory logic --------------------------------
	
	protected abstract String getOrderBy();

	protected abstract Class<T> getEntityType();

	// --- CRUD --------------

	public T persist(T object) {
		try {
			object = prePersist(object);
			getEm().persist(object);
			return object;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	/**
	 * Override this if needed
	 * @param object
	 * @return
	 */
	protected T prePersist(T object) {
		return object;
	}

	public T find(Object key) {
		try {
			return getEm().find(getEntityType(), key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public T fetch(Object key) {
		try {
			T found = getEm().find(getEntityType(), key);
			if (found == null)
				return found;
			else {
				this.fetchChildren(found);
				return found;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public T update(T object) {
		try {
			object = preUpdate(object);
			getEm().merge(object);
			return object;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Override this if needed
	 * @param object
	 * @return
	 */
	protected T preUpdate(T object) {
		return object;
	}

	public void delete(Object key) {
		try {
			T obj = getEm().find(getEntityType(), key);
			if (obj != null) {
				Field activeField = getActiveField(obj.getClass());
				if (activeField != null) {
					activeField.setAccessible(true);
					activeField.set(obj, false);
					getEm().merge(obj);
				} else {
					getEm().remove(obj);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// --- LIST ------------------------------------------

	public List<T> getAllList() {
		List<T> result = new ArrayList<T>();
		try {
			boolean count = false;
			Ricerca ricercaNulla = new Ricerca(getEntityType());
			ricercaNulla.setOggetto(null);
			result = (List<T>) getRestrictions(ricercaNulla, getOrderBy(),
					count).getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public List<T> getList(Ricerca<T> ricerca, int startRow, int pageSize) {
		List<T> result = new ArrayList<T>();
		try {
			boolean count = false;
			Query res = getRestrictions(ricerca, getOrderBy(), count);
			if (res == null)
				return result;
			if (startRow >= 0) {
				res.setFirstResult(startRow);
			}
			if (pageSize > 0) {
				res.setMaxResults(pageSize);
			}
			if (res.getResultList() != null)
				result = (List<T>) res.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public int getListSize(Ricerca<T> ricerca) {
		Long result = new Long(0);
		try {
			boolean count = true;
			Query res = getRestrictions(ricerca, getOrderBy(), count);
			if ((res != null) && (res.getSingleResult() != null))
				result = (Long) res.getSingleResult();
			return result.intValue();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public List<T> getList(int startRow, int pageSize, Query res) {
		List<T> result = new ArrayList<T>();
		try {
			if (res.getResultList() != null)
				result = (List<T>) res.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	// --- metodi di utilità interni e per le sotto classi ---------------

	protected String getBaseList(Class<? extends Object> clazz, String alias,
			boolean count) {
		if (count) {
			return "select count(" + alias + ") from " + clazz.getSimpleName()
					+ " " + alias + " ";
		} else {
			return "select " + alias + " from " + clazz.getSimpleName() + " "
					+ alias + " ";
		}
	}

	/**
	 * criteri di default, comuni a tutti, ma specializzabili da ogni EJB
	 * tramite overriding
	 */
	protected Query getRestrictions(Ricerca<T> ricerca, String orderBy,
			boolean count) {
		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		Field activeField = getActiveField(getEntityType());
		if (activeField != null) {
			sb.append(" where ").append(alias).append(".attivo = :attivo");
		} else {
			logger.info("activeField = null");
		}
		if (!count) {
			sb.append(" order by ").append(alias).append(".").append(orderBy);
		} else {
			logger.info("order by null");
		}
		logger.info(sb.toString());
		Query q = getEm().createQuery(sb.toString());
		if (activeField != null) {
			q.setParameter("attivo", true);
		}
		return q;
	}

	/**
	 * sperimentale: fetch via reflection, che esclude gli oggetti non attivi
	 * ...attualmente sembra che la fetch funzioni a me (Pisi) e non a Fiore.
	 * ...il filtraggio attivi/passivi, in ogni caso, non funziona: 
	 * provare a creare una seconda lista 'detached' e riassegnarla
	 * 
	 */
	private void fetchChildren(Object object) throws Exception {
		if (object == null)
			return;

		Class objectClass = object.getClass();
		Field[] objectFields = objectClass.getDeclaredFields();
		for (Field objectField : objectFields) {
//			System.out.println(objectClass.getSimpleName() + "."
//					+ objectField.getName());
			objectField.setAccessible(true);

			if (objectField.getType().isAssignableFrom(List.class)) {
				List list = (List) objectField.get(object);

				if (list != null) {

					// scarto i passivi
					int indexOfPassivato = -2;
					do {
						indexOfPassivato = -1;
						for (int i = 0; i < list.size(); i++) {

							Field attivoField = getActiveField(list.get(i)
									.getClass());
							if (attivoField != null) {
								attivoField.setAccessible(true);
								if (!(Boolean) attivoField.get(list.get(i))) {
									indexOfPassivato = i;
									break;
								}
							}
						}
						if (indexOfPassivato > 0) {
							// TODO
							// TO FIX.... non sembra avere effetti sulla
							// persistent bag
							list.remove(indexOfPassivato);
						}
					} while (indexOfPassivato > 0);

					// fetcho i rimanenti
					for (Object listItem : list) {
						// ricorsivamente!
						// fetch(listItem);
						// o no!
						listItem.toString();
					}

				}
			} else {
				Object notList = objectField.get(object);
				if (notList != null) {
					notList.toString();
				}
			}
		}

	}

	// --- utilita --------------------------

	protected String likeParam(String param) {
		return "%" + param + "%";
	}

	protected Field getActiveField(Class clazz) {
		try {
			return clazz.getDeclaredField("attivo");
		} catch (Exception e) {
			e.printStackTrace();
			// puo' darsi che anziché nullo dia eccezione se il campo non c'è
			// ... ora non ricordo esattamente quale comportamento bisogna attendere
			return null;
		}
	}
}

