package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

	ConcurrentHashMap<Integer, Paper> papers = new ConcurrentHashMap<Integer, Paper>();
	ConcurrentHashMap<String, Author> authors = new ConcurrentHashMap<String, Author>();

	public void addPaper(Paper paper) {
		if (!papers.containsKey(paper.getId()))
			papers.put(paper.getId(), paper);
	}

	public void addAuthor(Author author) {
		if (!authors.containsKey(author.getName()))
			authors.put(author.getName(), author);
	}

	public ConcurrentHashMap<Integer, Paper> getPapers() {
		return papers;
	}

	public ConcurrentHashMap<String, Author> getAuthors() {
		return authors;
	}
}
