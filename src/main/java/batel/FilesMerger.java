package batel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import javax.swing.JFileChooser;

public class FilesMerger {

	public static void main(String[] args) throws IOException {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.showDialog(null, "open");
		File selectedFile = jFileChooser.getSelectedFile();
		FileWriter fw=new FileWriter(new File("myTest.txt"));
		Path path = selectedFile.toPath();
		int len = path.toString().length();
		FileVisitor<Path>sfv=new FileVisitor<Path>() {
			
			@Override
			public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
				fw.write("file: "+arg0.toString().substring(len)+"\n");
				List<String> readAllLines = Files.readAllLines(arg0);
				for (String line : readAllLines) {
					if (line.trim().length()>0)
						fw.write(line+"\n");
				}
				fw.write("endFile: \n");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
				fw.write("dir: "+ arg0.toString().substring(len)+"\n");
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
				fw.write("endDir---\n");
				return FileVisitResult.CONTINUE;
			}
		};
		Path walkFileTree = Files.walkFileTree(path,sfv);
		fw.close();
		
	}

}
