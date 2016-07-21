package ro.practice.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CyclicDependencyUtils {
	private final static String JAVA_NAME = ".java";
	private final static String IMPORT_NAME = "import ";
	private final static String PACKAGE_NAME = "package ";

	public static void listFilesForFolder(final File folder, List<File> fileList) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry, fileList);
			} else {
				fileList.add(fileEntry);
			}
		}
	}

	public static List<String> getImportsInClass(File file) throws IOException {
		List<String> imports = new ArrayList<String>();
		String line = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
			if (line.startsWith(IMPORT_NAME)) {
				imports.add(line.substring(IMPORT_NAME.length(), line.indexOf(";")));
			} 
		}
		br.close();
		return imports;
	}

	public static Set<File> getClassesInPackage(String packageName, String objectLocation) {
		Set<File> classes = new HashSet<File>();
		File directory = new File(objectLocation + "/" + packageName.replace(".", "/"));
		if (directory.exists()) {
			String[] files = directory.list();
			for (String fileName : files) {
				if (fileName.endsWith(JAVA_NAME)) {
					classes.add(new File(directory, fileName));
				}
			}
		}
		return classes;
	}

	public static List<File> hasCyclic(File rootDirectory) throws IOException {
		List<File> fileList = new ArrayList<File>();
		List<File> fileCyc = new ArrayList<File>();
		if (rootDirectory.exists() && rootDirectory.isDirectory()) {
			listFilesForFolder(rootDirectory, fileList);
			for (File file : fileList) {
				String packageName = retrievePackageName(file);
				String fullName = packageName + "." + file.getName().substring(0, file.getName().indexOf(".java"));
				List<File> convFile = getConvertedJavaFilesFromImports(rootDirectory, file);
				for (File conv : convFile) {
					List<String> importsConv = CyclicDependencyUtils.getImportsInClass(conv);
					for (String imp : importsConv) {
						
						if (imp.equals(fullName)) {
							fileCyc.add(file);
						}
					}
				}
			}
		}
		return fileCyc;
	}

	private static List<File> getConvertedJavaFilesFromImports(File rootDirectory, File file) throws IOException {
		List<String> imports = CyclicDependencyUtils.getImportsInClass(file);
		List<File> convFile = new ArrayList<File>();
		for (String importFile : imports) {
			String path = rootDirectory.getPath() + "\\" + importFile.replace(".", "\\");
			String filesNames = path + JAVA_NAME;
			File convertedFileFromImport = new File(filesNames);
			if (convertedFileFromImport.exists()) {
				convFile.add(convertedFileFromImport);
			}
		}
		return convFile;
	}

	private static String retrievePackageName(File file) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = read.readLine()) != null) {
			if (line.startsWith(PACKAGE_NAME)) {
				return line.substring(PACKAGE_NAME.length(), line.indexOf(";"));
			}
			read.close();
		}
		return null;
	}

}
