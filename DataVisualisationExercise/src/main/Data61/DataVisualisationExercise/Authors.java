package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Authors {

	private ApplicationContext context;
	ConcurrentHashMap<String, Author> authors = new ConcurrentHashMap<String, Author>();

	private AtomicInteger id = new AtomicInteger(-1);

	public Authors(ApplicationContext context) {
		this.context = context;
	}

	public void load() {

		try {
			FileReaderIterator iterator = new FileReaderIterator("paperlinks.csv");
			// Not thread safe because two threads might create two authors for
			// one person which might causes error.
			ExecutorService executorService = Executors.newFixedThreadPool(1);
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
				author = new Author(id.incrementAndGet(), fields[2]);
			int paperId1 = Integer.parseInt(fields[0]);
			int paperId2 = Integer.parseInt(fields[1]);

			author.addPaper(context.papers.getPapers().get(paperId1));
			author.addPaper(context.papers.getPapers().get(paperId2));
			authors.put(author.getName(), author);
			context.papers.addAuthor(paperId1, author);
			context.papers.addAuthor(paperId2, author);
		}

	}
}
