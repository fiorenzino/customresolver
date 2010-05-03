package weld.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import weld.model.type.CategoriaAttivita;
import weld.model.type.TipoAttivita;
import weld.model.type.TipoPubblicazione;
import weld.session.CategorieSession;

@Named
@SessionScoped
public class PropertiesHandler implements Serializable {

	@Inject
	CategorieSession categorieSession;

	@Inject
	AttivitaHandler attivitaHandler;

	private SelectItem[] fileTypeItems = new SelectItem[] {};
	private SelectItem[] categorieAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiPubblicazioneItems = new SelectItem[] {};

	public PropertiesHandler() {
		// TODO Auto-generated constructor stub
	}

	public SelectItem[] getFileTypeItems() {
		if (fileTypeItems == null || fileTypeItems.length == 0) {
			fileTypeItems = new SelectItem[4];
			fileTypeItems[0] = new SelectItem(0, "css");
			fileTypeItems[1] = new SelectItem(1, "img");
			fileTypeItems[2] = new SelectItem(2, "flash");
			fileTypeItems[3] = new SelectItem(3, "javascript");
		}
		return fileTypeItems;
	}

	public SelectItem[] getCategorieAttivitaItems() {
		if (categorieAttivitaItems.length == 0) {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null,
					"nessuna categoria");
			List<CategoriaAttivita> categorie = categorieSession
					.getAllCategoriaAttivita();
			if (categorie != null && categorie.size() > 0) {
				categorieAttivitaItems = new SelectItem[categorie.size() + 1];
				categorieAttivitaItems[0] = new SelectItem(null, "categoria");
				int i = 1;
				for (CategoriaAttivita c : categorie) {
					categorieAttivitaItems[i] = new SelectItem(c.getId(), c
							.getCategoria());
					i++;

				}
			}
		}
		return categorieAttivitaItems;
	}

	public void cambioTipo(ActionEvent event) {
		int tipo = attivitaHandler.getTipoId();
		cambioTipoDirect(tipo);
	}

	public void cambioTipoDirect(int tipo) {
		System.out.println("getCategorieByTipoItems: Tipo: " + tipo);
		List<CategoriaAttivita> categorie = categorieSession
				.getAllCategoriaAttivitaByTipo(new Long(tipo));
		if (categorie != null && categorie.size() > 0) {
			categorieAttivitaItems = new SelectItem[categorie.size() + 1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
			int i = 1;
			for (CategoriaAttivita c : categorie) {
				categorieAttivitaItems[i] = new SelectItem(c.getId(), c
						.getCategoria());
				i++;

			}
		}

	}

	public SelectItem[] getCategorieByTipoItems() {
		return categorieAttivitaItems;
	}

	public void resetCategorieAttivitaItems() {
		this.categorieAttivitaItems = new SelectItem[] {};
	}

	public SelectItem[] getTipiAttivitaItems() {
		if (tipiAttivitaItems.length == 0) {
			tipiAttivitaItems = new SelectItem[1];
			tipiAttivitaItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoAttivita> tipi = categorieSession.getAllTipoAttivita();
			if (tipi != null && tipi.size() > 0) {
				tipiAttivitaItems = new SelectItem[tipi.size() + 1];
				tipiAttivitaItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoAttivita c : tipi) {
					tipiAttivitaItems[i] = new SelectItem(c.getId(), c
							.getTipo());
					i++;

				}
			}
		}
		return tipiAttivitaItems;
	}

	public SelectItem[] getTipiPubblicazioneItems() {
		if (tipiPubblicazioneItems.length == 0) {
			tipiPubblicazioneItems = new SelectItem[1];
			tipiPubblicazioneItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoPubblicazione> tipi = categorieSession
					.getAllTipoPubblicazione();
			if (tipi != null && tipi.size() > 0) {
				tipiPubblicazioneItems = new SelectItem[tipi.size() + 1];
				tipiPubblicazioneItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoPubblicazione c : tipi) {
					tipiPubblicazioneItems[i] = new SelectItem(c.getId(), c
							.getNome());
					i++;

				}
			}
		}
		return tipiPubblicazioneItems;
	}

	public void resetTipiAttivitaItems() {
		tipiAttivitaItems = new SelectItem[] {};
	}

	public void resetTipiPubblicazioniItems() {
		tipiPubblicazioneItems = new SelectItem[] {};
	}

}
