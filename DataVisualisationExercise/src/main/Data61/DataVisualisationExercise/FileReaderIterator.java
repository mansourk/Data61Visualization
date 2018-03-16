package Data61.DataVisualisationExercise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileReaderIterator implements Iterator<String> {

	private String fileName;
	private String line = null;
	private FileReader fileReader = null;
	private BufferedReader bufferedReader = null;

	public FileReaderIterator(String fileName) {
		this.fileName = fileName;
		read();
	}

	private void read() {
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
	}

	@Override
	public boolean hasNext() {
		try {
			if (bufferedReader.ready() && (line = bufferedReader.readLine()) != null)
				return true;
			bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return false;
	}

	@Override
	public String next() {
		return line;
	}
}
