package batel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileExtractor {

	public static void main(String[] args) throws IOException {
		File direc= new File(JOptionPane.showInputDialog("insert directory"));
		direc.mkdir();
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.showDialog(null, "open");
		File selectedFile = jFileChooser.getSelectedFile();
		List<String> readAllLines = Files.readAllLines(selectedFile.toPath());
		FileWriter fw= null;
		for (String line : readAllLines) {
			if (line.startsWith("dir: ")) {
				new File(direc.getAbsoluteFile().getAbsolutePath()+line.substring(4)).mkdir();
			}
			else if (line.startsWith("file: ")) {
				File file = new File(direc.getAbsoluteFile().getAbsolutePath()+line.substring(5));
				fw = new FileWriter(file);
			}
			else if (line.startsWith("endFile: ")) {
				fw.close();
			}
			else if (line.startsWith("endDir---")) {
				continue;
			}
			else {
				fw.write(line+"\n");
			}
		}
	}
}
