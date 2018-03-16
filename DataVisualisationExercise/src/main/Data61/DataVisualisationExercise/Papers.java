package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Papers {

	ConcurrentHashMap<Integer, Paper> papers = new ConcurrentHashMap<Integer, Paper>();

	public Papers() {

	}

	public void load() {

		try {
			FileReaderIterator iterator = new FileReaderIterator("papers.csv");
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

	public void addPaper(Paper paper) {
		if (!papers.containsKey(paper.getId()))
			papers.put(paper.getId(), paper);
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
			papers.put(Integer.parseInt(fields[0]), new Paper(fields));
		}

	}

}
