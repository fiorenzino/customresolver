package it.jflower.base.utils;

import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.XlsDoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XlsCreator {

	private static Logger logger = LoggerFactory.getLogger(XlsCreator.class);

	public static XlsDoc createPubblicazioniFile(List<Pubblicazione> list,
			String nomeFile) {
		logger.info("NUM PUBBLICAZIONI: " + list.size());
		logger.info("NOME FILE TEMP: " + nomeFile);
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			XlsDoc doc = new XlsDoc();
			doc.setNome(nomeFile);
			File temp = new File(System.getProperty("java.io.tmpdir") + "/",
					nomeFile);
			WritableWorkbook workbook = Workbook.createWorkbook(temp);

			WritableSheet sheet = null;
			int numPage = 0;
			int numRow = 0;
			for (int i = 0; i < list.size(); i++) {
				logger.info("I: " + i);
				numRow = (i % 65535) + 1;
				if ((i == 0) || (i % 65535 == 0)) {
					logger.info("		page: " + numPage + "");
					numPage++;
					sheet = workbook.createSheet("Pagina " + numPage, numPage);
					Label label = new Label(0, 0, "numero");
					sheet.addCell(label);
					
					label = new Label(1, 0, "anno/num");
					sheet.addCell(label);

					label = new Label(2, 0, "titolo");
					sheet.addCell(label);

					label = new Label(3, 0, "tipo");
					sheet.addCell(label);

					label = new Label(4, 0, "data pubblicazione");
					sheet.addCell(label);

					label = new Label(5, 0, "valido dal");
					sheet.addCell(label);

					label = new Label(6, 0, "valido al");
					sheet.addCell(label);
					
					label = new Label(7, 0, "ente emittente");
					sheet.addCell(label);
					
					
				}
				logger.info("page: " + numPage + " - num: " + numRow);
				Pubblicazione pubblicazione = list.get(i);

				Label label = new Label(0, numRow, "" + numRow);
				sheet.addCell(label);
				
				label = new Label(1, numRow, "" + pubblicazione.getProgressivoRegistro());
				sheet.addCell(label);

				label = new Label(2, numRow, "" + pubblicazione.getTitolo());
				sheet.addCell(label);

				label = new Label(3, numRow, ""
						+ pubblicazione.getTipo().getNome());
				sheet.addCell(label);

				label = new Label(4, numRow, ""
						+ format.format(pubblicazione.getData()));
				sheet.addCell(label);

				label = new Label(5, numRow, ""
						+ format.format(pubblicazione.getDal()));
				sheet.addCell(label);

				label = new Label(6, numRow, ""
						+ format.format(pubblicazione.getAl()));
				sheet.addCell(label);
				
				label = new Label(7, numRow, ""
						+ format.format(pubblicazione.getEnteEmittente()));
				sheet.addCell(label);
				
				
			}
			workbook.write();
			workbook.close();
			doc.setData(FileUtils.readFileToByteArray(temp));
			return doc;
		} catch (RowsExceededException e) {
			logger.info("error:  " + e.getMessage());
			e.printStackTrace();
		} catch (WriteException e) {
			logger.info("error:  " + e.getMessage());
			e.printStackTrace();

		} catch (IOException e) {
			logger.info("error:  " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
