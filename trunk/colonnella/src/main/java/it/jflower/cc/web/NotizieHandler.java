package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.model.LocalDataModel;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.session.NotizieSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class NotizieHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"+FACES_REDIRECT;
	public static String VIEW = "/private/notizie/scheda-notizia.xhtml"+FACES_REDIRECT;
	public static String LIST = "/private/notizie/lista-notizie.xhtml"+FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/notizie/gestione-notizia.xhtml"+FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	NotizieSession notizieSession;

	private Notizia notizia;
	private boolean editMode;
	private List<Notizia> all;

	public String addNotizia() {
		this.editMode = false;
		this.notizia = new Notizia();
		this.notizia.setTipo( new TipoInformazione() );
		return NEW_OR_EDIT;
	}

	@Transactional
	public String saveNotizia() {
		String idTitle = PageUtils.createPageId(this.notizia.getTitolo());
		String idFinal = testId(idTitle);
		this.notizia.setId(idFinal);
		notizieSession.persist(this.notizia);
		this.all = null;
		return VIEW;
	}

	@Transactional
	public String deleteNotizia() {
		notizieSession.delete(this.notizia.getId());
		this.all = null;
		return LIST;
	}

	public String modNotizia(String id) {
		this.editMode = true;
		this.notizia = notizieSession.find(id);
		return NEW_OR_EDIT;
	}

	@Transactional
	public String updateNotizia() {
		this.notizia = notizieSession.update(this.notizia);
		this.all = null;
		return VIEW;
	}

	public String detailNotizia(String id) {
		this.notizia = notizieSession.find(id);
		return VIEW;
	}

	public Notizia getNotizia() {
		return notizia;
	}

	public void setNotizia(Notizia notizia) {
		this.notizia = notizia;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Notizia> getAll() {
		if (this.all == null)
			this.all = notizieSession.getAllList();
		return all;
	}

	public void setAll(List<Notizia> all) {
		this.all = all;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Notizia notiziaFind = notizieSession.find(idFinal);
			System.out.println("trovato_ " + notiziaFind);
			if (notiziaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}

	// -----------------------------------------------------------

	private LocalDataModel<Notizia> model;
	private String tipo;
	private Integer pageSize;
	
	public LocalDataModel<Notizia> ultimeNotizie(String tipo, int pageSize) {
		if ( model == null || this.tipo == null || this.pageSize == null || ! this.tipo.equals(tipo) || this.pageSize != pageSize ) {
			Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
			ricerca.getOggetto().setTipo( new TipoInformazione() );
			ricerca.getOggetto().getTipo().setNome( tipo );
			model = new LocalDataModel<Notizia>(pageSize, ricerca, notizieSession);
		}
		return model;
	}

}
