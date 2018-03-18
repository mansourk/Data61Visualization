package Data61.DataVisualisationExercise;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Author {

	private final int id;
	private final String name;
	private CopyOnWriteArrayList<Paper> papers = new CopyOnWriteArrayList<Paper>();

	public Author(int id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public void addPaper(Paper paper) {
		if (!papers.contains(paper))
			papers.add(paper);
	}

	public String getName() {
		return name;
	}

	public CopyOnWriteArrayList<Paper> getPapers() {
		return papers;
	}

	public boolean equals(Author author) {
		return this.name.equals(author.name);
	}

	public Set<Author> getCoAuthors() {
		java.util.Collection<Author> coauthors = papers.parallelStream().map(p -> p.getAuthors())
				.flatMap(x -> x.stream()).distinct().filter(a -> !a.name.equals(this.name))
				.collect(Collectors.toCollection(HashSet::new));
		return new HashSet<>(coauthors);

	}

	public int getId() {
		return id;
	}
}
