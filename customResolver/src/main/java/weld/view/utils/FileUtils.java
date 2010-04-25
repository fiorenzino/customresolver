package weld.view.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileUtils {

	private static List<String> cssFiles;

	private static List<String> jsFiles;

	private static List<String> imgFiles;

	private static List<String> flashFiles;

	public static List<String> getFilesName(String directory,
			String[] extensions) {
		File rootDir = new File(directory);
		IOFileFilter filesFilter = new SuffixFileFilter(extensions);
		IOFileFilter notDirectory = new NotFileFilter(
				DirectoryFileFilter.INSTANCE);
		FilenameFilter fileFilter = new AndFileFilter(filesFilter, notDirectory);
		String[] resultFiles = rootDir.list(fileFilter);
		if (resultFiles.length > 0) {
			Arrays.asList(resultFiles);
		}
		return new ArrayList<String>();
	}

	public static List<String> getCssFiles() {
		if (cssFiles == null) {
			cssFiles = getFilesName("img", new String[] { "css", "CSS" });
		}
		return cssFiles;
	}

	public static List<String> getJsFiles() {
		if (jsFiles == null) {
			jsFiles = getFilesName("js", new String[] { "js" });
		}
		return jsFiles;
	}

	public static List<String> getImgFiles() {
		if (imgFiles == null) {
			imgFiles = getFilesName("", new String[] { "gif", "GIF", "jpg",
					"JPG", "jpeg", "JPEG" });
		}
		return imgFiles;
	}

	public static List<String> getFlashFiles() {
		if (flashFiles == null) {
			flashFiles = getFilesName("swf", new String[] { "swf" });
		}
		return flashFiles;
	}

	public static String getFileContent(String fileName) {

		return "";
	}

}
