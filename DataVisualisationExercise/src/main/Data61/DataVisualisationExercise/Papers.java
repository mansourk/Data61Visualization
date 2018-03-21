package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Papers {

	private final static String PAPERS = "papers.csv";

	ConcurrentHashMap<Integer, Paper> papers = new ConcurrentHashMap<Integer, Paper>();

	public Papers() {

	}

	public void load() {

		try {
			FileReaderIterator iterator = new FileReaderIterator(PAPERS);
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			while (iterator.hasNext()) {
				executorService.execute(new PaperBuilder(iterator.next()));
			}
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addAuthor(int paperId, Author author) {
		if (papers.containsKey(paperId))
			papers.get(paperId).addAuthor(author);
	}

	public ConcurrentHashMap<Integer, Paper> getPapers() {
		return papers;
	}

	public class PaperBuilder implements Runnable {

		private String line;

		public PaperBuilder(String line) {
			this.line = line;
		}

		@Override
		public void run() {
			String[] fields = line.split(",");
			if (fields[0].equalsIgnoreCase("Id"))
				return;
			papers.put(Integer.parseInt(fields[0]), new Paper(fields));
		}

	}

}
