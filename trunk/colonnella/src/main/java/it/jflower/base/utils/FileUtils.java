package it.jflower.base.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileUtils {

	public static List<String> getFilesName(String directory,
			String[] extensions) {
		File rootDir = new File(getRealPath() + directory);
		IOFileFilter filesFilter = new SuffixFileFilter(extensions);
		IOFileFilter notDirectory = new NotFileFilter(
				DirectoryFileFilter.INSTANCE);
		FilenameFilter fileFilter = new AndFileFilter(filesFilter, notDirectory);
		String[] resultFiles = rootDir.list(fileFilter);
		Arrays.sort(resultFiles);
		if (resultFiles.length > 0) {
			return Arrays.asList(resultFiles);
		}
		return new ArrayList<String>();
	}

	public static List<String> getPdfFiles() {
		return getFilesName("docs", new String[] { "pdf", "PDF" });
	}

	public static List<String> getCssFiles() {
		return getFilesName("css", new String[] { "css", "CSS" });
	}

	public static List<String> getJsFiles() {
		return getFilesName("js", new String[] { "js" });
	}

	public static List<String> getImgFiles() {
		return getFilesName("img", new String[] { "gif", "GIF", "jpg", "JPG",
				"jpeg", "JPEG" });
	}

	public static List<String> getFlashFiles() {

		return getFilesName("swf", new String[] { "swf" });
	}

	public static String createImage_(String folder, String imageFileName, byte[] data) {
		try {
			String actualFileName = getUniqueName(
					getRealPath() + folder, imageFileName);
			FileImageOutputStream imageOutput;
			imageOutput = new FileImageOutputStream(new File(actualFileName));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			return actualFileName.substring(actualFileName.lastIndexOf(File.separator)+1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String createFile_(String folder, String fileName, byte[] data) {
		try {
			String actualFileName = getUniqueName(
					getRealPath() + folder, fileName);
			FileOutputStream fos = new FileOutputStream(new File(actualFileName));
			fos.write(data);
			fos.close();
			return actualFileName.substring( actualFileName.lastIndexOf(File.separator)+1 );
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getUniqueName(String folder, String fileName) {
		String est = fileName.substring(fileName.indexOf(".") + 1);
		String nome = fileName.substring(0, fileName.indexOf("."));
		String finalName = fileName;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("finalName: " + finalName);
			File file = new File(folder + File.separator + finalName);
			System.out.println("trovato_ " + finalName);
			if (file != null && file.exists()) {
				i++;
				finalName = nome + "_" + i + "." + est;
			} else {
				trovato = true;
				return folder + File.separator + finalName;
			}
		}
		return folder + File.separator + finalName;
	}

	public static String getRealPath() {
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String folder = servletContext.getRealPath("") + File.separator;
		return folder;
	}

	public static String getFileContent(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
