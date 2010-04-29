package weld.view.utils;

import java.io.File;
import java.io.FileNotFoundException;
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
		if (resultFiles.length > 0) {
			return Arrays.asList(resultFiles);
		}
		return new ArrayList<String>();
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

	public static void createImage(String folder, String image, byte[] data) {
		try {
			FileImageOutputStream imageOutput;
			imageOutput = new FileImageOutputStream(new File(getRealPath()
					+ folder + File.separator + image));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
