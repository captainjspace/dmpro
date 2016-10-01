package dmpro.utils;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static List<Path> listSourceFiles(Path dir) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*")) {
			for (Path entry: stream) {
				result.add(entry);
			}
		} catch (DirectoryIteratorException ex) {
			

			throw ex.getCause();
		}
		return result;
	}

}
