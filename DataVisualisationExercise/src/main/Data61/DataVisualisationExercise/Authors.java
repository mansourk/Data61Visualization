package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Authors {

	private ApplicationContext context;

	public Authors(ApplicationContext context) {
		this.context = context;
	}

	ConcurrentHashMap<String, Author> authors = new ConcurrentHashMap<String, Author>();

	public void load() {

		try {
			FileReaderIterator iterator = new FileReaderIterator("paperlinks.csv");
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			while (iterator.hasNext()) {
				executorService.execute(new AuthorBuilder(iterator.next()));
			}
			executorService.shutdown();
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ConcurrentHashMap<String, Author> getAuthors() {
		return authors;
	}

	public class AuthorBuilder implements Runnable {

		private String line;

		public AuthorBuilder(String line) {
			this.line = line;
		}

		@Override
		public void run() {

			String[] fields = line.split(",");
			if (fields[0].equalsIgnoreCase("Source"))
				return;
			Author author = authors.get(fields[2]);
			if (author == null)
				author = new Author(fields[2]);
			author.addPaper(context.papers.getPapers().get(Integer.parseInt(fields[0])));
			author.addPaper(context.papers.getPapers().get(Integer.parseInt(fields[1])));
			authors.put(fields[2], author);
			context.papers.getPapers().get(Integer.parseInt(fields[0])).addAuthor(author);
			context.papers.getPapers().get(Integer.parseInt(fields[1])).addAuthor(author);

		}

	}
}
