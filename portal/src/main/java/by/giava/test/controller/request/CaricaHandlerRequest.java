package by.giava.test.controller.request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.repository.AttivitaRepository;
import by.giava.attivita.repository.CategorieAttivitaRepository;
import by.giava.base.controller.util.PageUtils;
import by.giava.moduli.model.Modulo;
import by.giava.moduli.model.type.TipoModulo;
import by.giava.moduli.repository.ModuliRepository;
import by.giava.moduli.repository.TipoModuloRepository;
import by.giava.news.repository.TipoInformazioniRepository;
import by.giava.notizie.model.Notizia;
import by.giava.notizie.model.type.TipoInformazione;
import by.giava.notizie.repository.NotizieRepository;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;
import by.giava.pubblicazioni.repository.PubblicazioniRepository;
import by.giava.pubblicazioni.repository.TipoPubblicazioneRepository;

@Named
@RequestScoped
public class CaricaHandlerRequest implements Serializable {

	private Logger logger = Logger.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	@Inject
	NotizieRepository notizieRepository;

	@Inject
	TipoInformazioniRepository tipoInformazioniRepository;

	@Inject
	AttivitaRepository attivitaRepository;

	@Inject
	CategorieAttivitaRepository categorieAttivitaRepository;

	@Inject
	PubblicazioniRepository pubblicazioniRepository;

	@Inject
	ModuliRepository moduliRepository;

	@Inject
	TipoPubblicazioneRepository tipoPubblicazioneRepository;

	@Inject
	TipoModuloRepository tipoModuloRepository;

	private String ciao = "ciao flower";

	public CaricaHandlerRequest() {

	}

	@PostConstruct
	public void init() {
		logger.info("INIZIO N" + new Date());
		caricaNews();
		logger.info("INIZIO A" + new Date());
		caricaAttivita();
		logger.info("INIZIO M" + new Date());
		caricaModuli();
		logger.info("INIZIO P" + new Date());
		caricaPubblicazioni();
		logger.info("FINE" + new Date());

	}

	private void caricaNews() {
		String testo = "Antichi palazzi costruiti su un'alta collina, un intreccio di viuzze e scalinate, diverse piazzette caratteristiche, un paronama incantevole, unico, l'aria salubre, fresca, questa è Colonnella."
				+ "Le mie \"estati\" sono abruzzesi e quindi conosco bene "
				+ "dell'Abruzzo il colore e il senso dell'estate, quando "
				+ "dai treni che mi riportavano a casa da lontani paesi, "
				+ "passavano per il Tronto e rivedevo le prime case coloniche "
				+ "coi mazzi di granturco sui tetti, le spiagge libere ancora, "
				+ "i paesi affacciati su quei loro balconi naturali di colline, "
				+ "le più belle che io conosca.";
		TipoInformazione tipo = tipoInformazioniRepository.find(new Long(1));
		for (int i = 0; i < 1000; i++) {
			Notizia notizia = new Notizia();
			notizia.setTitolo("notizie" + i);
			notizia.setAnteprima(testo);
			notizia.setAutore("flower");
			notizia.setAttivo(true);
			notizia.setData(new Date());
			notizia.setContenuto(i + testo);
			notizia.setTipo(tipo);
			String idTitle = PageUtils.createPageId(notizia.getTitolo());
			String idFinal = testNotizieId(idTitle);
			notizia.setId(idFinal);
			notizieRepository.persist(notizia);
		}
	}

	private void caricaAttivita() {
		String testo = "Antichi palazzi costruiti su un'alta collina, un intreccio di viuzze e scalinate, diverse piazzette caratteristiche, un paronama incantevole, unico, l'aria salubre, fresca, questa è Colonnella."
				+ "Le mie \"estati\" sono abruzzesi e quindi conosco bene "
				+ "dell'Abruzzo il colore e il senso dell'estate, quando "
				+ "dai treni che mi riportavano a casa da lontani paesi, "
				+ "passavano per il Tronto e rivedevo le prime case coloniche "
				+ "coi mazzi di granturco sui tetti, le spiagge libere ancora, "
				+ "i paesi affacciati su quei loro balconi naturali di colline, "
				+ "le più belle che io conosca.";
		CategoriaAttivita categoria = categorieAttivitaRepository
				.find(new Long(2));
		for (int i = 0; i < 1000; i++) {
			Attivita attivita = new Attivita();
			attivita.setAttivo(true);
			attivita.setAutore("flower");

			attivita.setCategoria(categoria);
			attivita.setCity("sbt");
			attivita.setData(new Date());
			attivita.setDescrizione(testo);
			attivita.setEmail(i + "_em@it.it");
			attivita.setFax("1223345566");
			attivita.setIndirizzo("via del carimen " + i);
			attivita.setNome("attivita" + i);
			attivita.setOrariEchiusura("weerr");
			attivita.setProvincia("TE");
			attivita.setSitoInternet("http://ind.it");
			attivita.setTelefono("12345678");
			String idTitle = PageUtils.createPageId(attivita.getNome());
			String idFinal = testAttId(idTitle);
			attivita.setId(idFinal);
			attivitaRepository.persist(attivita);
		}
	}

	private void caricaPubblicazioni() {
		String testo = "Antichi palazzi costruiti su un'alta collina, un intreccio di viuzze e scalinate, diverse piazzette caratteristiche, un paronama incantevole, unico, l'aria salubre, fresca, questa è Colonnella."
				+ "Le mie \"estati\" sono abruzzesi e quindi conosco bene "
				+ "dell'Abruzzo il colore e il senso dell'estate, quando "
				+ "dai treni che mi riportavano a casa da lontani paesi, "
				+ "passavano per il Tronto e rivedevo le prime case coloniche "
				+ "coi mazzi di granturco sui tetti, le spiagge libere ancora, "
				+ "i paesi affacciati su quei loro balconi naturali di colline, "
				+ "le più belle che io conosca.";
		Calendar dal = Calendar.getInstance();
		dal.setTime(new Date());
		dal.add(Calendar.MONTH, -1);
		Calendar al = Calendar.getInstance();
		al.setTime(new Date());
		al.add(Calendar.MONTH, 1);
		TipoPubblicazione tipo = tipoPubblicazioneRepository.find(new Long(4));
		for (int i = 0; i < 1000; i++) {
			Pubblicazione pubblicazione = new Pubblicazione();
			pubblicazione.setAl(al.getTime());
			pubblicazione.setDal(dal.getTime());
			pubblicazione.setAttivo(true);
			pubblicazione.setAutore("flower");
			pubblicazione.setDescrizione(testo);
			pubblicazione.setNome("pubblicazione" + i);
			pubblicazione.setTitolo("pubblicazione" + i);
			pubblicazione.setData(new Date());
			pubblicazione.setTipo(tipo);
			String idTitle = PageUtils.createPageId(pubblicazione.getNome());
			String idFinal = testPubbId(idTitle);
			pubblicazione.setId(idFinal);
			pubblicazioniRepository.persist(pubblicazione);
		}
	}

	private void caricaModuli() {
		@SuppressWarnings("unused")
		String testo = "Antichi palazzi costruiti su un'alta collina, un intreccio di viuzze e scalinate, diverse piazzette caratteristiche, un paronama incantevole, unico, l'aria salubre, fresca, questa è Colonnella."
				+ "Le mie \"estati\" sono abruzzesi e quindi conosco bene "
				+ "dell'Abruzzo il colore e il senso dell'estate, quando "
				+ "dai treni che mi riportavano a casa da lontani paesi, "
				+ "passavano per il Tronto e rivedevo le prime case coloniche "
				+ "coi mazzi di granturco sui tetti, le spiagge libere ancora, "
				+ "i paesi affacciati su quei loro balconi naturali di colline, "
				+ "le più belle che io conosca.";
		TipoModulo tipo = tipoModuloRepository.find(new Long(1));
		for (int i = 0; i < 1000; i++) {
			Modulo modulo = new Modulo();
			modulo.setAttivo(true);
			modulo.setAutore("flower");
			modulo.setData(new Date());
			modulo.setNome("modulo" + i);
			modulo.setOggetto("");
			modulo.setTipo(tipo);
			String idTitle = PageUtils.createPageId(modulo.getNome());
			String idFinal = testModId(idTitle);
			modulo.setId(idFinal);
			moduliRepository.persist(modulo);
		}
	}

	public String getCiao() {
		return ciao;
	}

	public void setCiao(String ciao) {
		this.ciao = ciao;
	}

	public String testNotizieId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Notizia notiziaFind = notizieRepository.find(idFinal);
			logger.info("trovato_ " + notiziaFind);
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

	public String testAttId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Attivita attivitaFind = attivitaRepository.find(idFinal);
			if (attivitaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}

	private String testPubbId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			Pubblicazione pubblicazioneFind = pubblicazioniRepository
					.find(idFinal);
			if (pubblicazioneFind != null) {
				if ((pubblicazioneFind.getTitolo() == null)
						|| ("".equals(pubblicazioneFind.getTitolo()))) {
					pubblicazioneFind.setTitolo(pubblicazioneFind.getNome());
					pubblicazioniRepository.update(pubblicazioneFind);
				}
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}
		return "";
	}

	private String testModId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			Modulo moduloFind = moduliRepository.find(idFinal);
			if (moduloFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}
		return "";
	}

}