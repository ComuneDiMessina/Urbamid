package it.eng.tz.urbamid.wrappergeo.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.util.Assert;


/**
 * Utility Class per effettuare l'unzip di un file in una directory
 * 
 * @author Antonio Grimaldi
 *
 */
public class UnZipUtils {
	
	private static final String SHP_EXTENSION = ".shp";

	public static void unzip(File zipFile, String targetDirectory)
			throws FileNotFoundException, IOException {
		
	    InputStream targetStream = new FileInputStream(zipFile);
	    unzip(targetStream, targetDirectory);
	}
	
	public static Collection<File> unzippedFiles(File zipFile, String targetDirectory)
			throws FileNotFoundException, IOException {
		
	    InputStream targetStream = new FileInputStream(zipFile);
		return unzip(targetStream, targetDirectory);
	}
	
	/**
	 * 
	 * @param inputStream - InputStream del file da unzippare
	 * @param targetDirectory - directory in cui unzippare il file
	 * @return - Lista file contenuti nel file .zip
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Collection<File> unzip(InputStream inputStream, String targetDirectory)
			throws FileNotFoundException, IOException {
		
		List<File> list = new ArrayList<File>();
		byte[] buffer = new byte[1024];
		try (ZipInputStream zis = new ZipInputStream(inputStream)) {
			ZipEntry zipEntry;
			while ((zipEntry = zis.getNextEntry()) != null) {
				File newFile = newFile(targetDirectory, zipEntry);
				if (zipEntry.isDirectory()) {
//					System.out.println("zip entry is a directory: " + zipEntry.getName());
//					System.out.println("new destination directory is " + newFile.getParentFile().getAbsolutePath());
					Assert.isTrue(newFile.exists() || newFile.mkdirs(), "the directory must exist");
				} else {

					Assert.isTrue(newFile.getParentFile().exists() || newFile.getParentFile().mkdirs(),
							"the parent directory " + newFile.getParentFile().getAbsolutePath() + " must exist.");
					boolean already = (newFile.exists());
					if (!already) {
						/*
						 * newFile.delete(); } else {
						 */
						Path tmpExpandedFile = Files.createTempFile("expanded", newFile.getName());
						try (BufferedOutputStream fos = new BufferedOutputStream(
								new FileOutputStream(tmpExpandedFile.toFile()))) {
							int len = 0;
							while ((len = zis.read(buffer)) > 0) {
								fos.write(buffer, 0, len);
							}
						}
						Assert.isTrue(newFile.getParentFile().exists() || newFile.getParentFile().mkdirs(),
								"the directory to which you're writing must exist");
						Files.move(tmpExpandedFile, newFile.toPath());
					}

					list.add(newFile);
				}
			}
			zis.closeEntry();
		}
		return list;
	}
	
	/**
	 * Recupera il primo file .shp presente nella directory passata.
	 * 
	 * @param folder - cartella in cui ricercare il file
	 * @return File .shp presente nella directory passata
	 * @throws IOException - se il file non è presente
	 */
	public static File findShapefile(String folder) throws IOException
    {
		return findFile(folder, SHP_EXTENSION);
    }
	
	/**
	 * Recupera il primo file con estensione passata presente nella directory passata.
	 * 
	 * @param folder - cartella in cui ricercare il file
	 * @param extension - estensione del file da ricercare
	 * @return File presente nella directory passata
	 * @throws IOException - se il file non è presente
	 */
	private static File findFile(String folder, String extension) throws IOException{
		File file = new File("");
        // Find first extension file in the folder
        File[] unzippedFiles = new File(folder).listFiles();
        for (int i = 0; i < unzippedFiles.length; i++)
        {
            if (unzippedFiles[i].getName().endsWith(extension))
            {
            	file = new File(folder + File.separator  + unzippedFiles[i].getName());
                break;
            }
        }
        if (file.toString() == "")
        {
            throw new IOException("Nessuno file "+ extension +" presente in '" + folder + "'.");
        }
        return file;
	}

	private static File newFile(String targetDirectory, ZipEntry zipEntry) throws IOException {
		File destinationDir = new File(targetDirectory);
		File destFile = new File(destinationDir, zipEntry.getName());
		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();
		boolean entryIsInTargetDirectory = destFilePath.startsWith(destDirPath + File.separator);
		Assert.isTrue(entryIsInTargetDirectory, "Entry is outside of the target dir: " + zipEntry.getName());
		return destFile;
	}

}
