package it.test;
import it.jflower.base.utils.HtmlUtils;

public class Test {
	public static void main(String[] args) {
		String content = "Gentile operatore&#044; " +
				"grazie al nostro team di sviluppo e ai vostri apprezzati suggerimenti abbiamo rilasciato e messo in produzione la nuova versione di GISO&#046; " +
				"Ecco alcune delle novita&#039;&#058; &#045; " +
				"introduzione del sistema sincrono per i nomi a dominio della gerarchia " +
				"&#046;it&#046; Il nuovo sistema permette di effettuare la registrazione " +
				"ed altre operazioni in modo immediato senza la necessita&#039; " +
				"di supporti cartacei&#046; " +
				"Fino a quando la migrazione non sara&#039; completata per tutti i " +
				"domini&#044; abbiamo lasciato i comandi relativi al vecchio sistema " +
				"&#040;asincrono&#041;&#059; &#045; comunicazione per i domini trasferiti " +
				"ad altro registrar&#059; &#045; eliminazione automatica dei domini " +
				"trasferiti ad altro registrar&#059; &#045; " +
				"comunicazione sulle imminenti scadenze&#046; " +
				"Come potrai immaginare GISO e&#039; costantemente oggetto di interventi " +
				"sul codice necessari per gli adeguamenti alle norme stabilite " +
				"dai diversi registri e per una sempre maggiore usabilita&#039; " +
				"e rispondenza alle tue esigenze&#046; " +
				"Per questo ti invitiamo a contattare il nostro supporto tecnico per " +
				"chiarire eventuali dubbi&#044; per chiedere delucidazioni sulle " +
				"nuove funzionalita&#039; o per inoltrarci suggerimenti e " +
				"segnalazioni&#046; " +
				"Grazie per aver scelto TopneT Cordiali saluti &#045;&#045; " +
				"TopneT Support Staff";
		String result = HtmlUtils.normalizeHtml(content);
		System.out.println(result);
	}
}
