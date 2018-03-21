package Data61.DataVisualisationExercise;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Author {

	private final int id;
	private final String name;
	private CopyOnWriteArrayList<Paper> papers = new CopyOnWriteArrayList<Paper>();
	private HashSet<Author> coauthors = null;

	public Author(int id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public void addPaper(Paper paper) {
		if (paper != null && !papers.contains(paper))
			papers.add(paper);
	}

	public String getName() {
		return name;
	}

	public CopyOnWriteArrayList<Paper> getPapers() {
		return papers;
	}

	public boolean equals(Author author) {
		return this.name == null ? false : this.name.equals(author.name);
	}

	public Set<Author> getCoAuthors() {

		if (coauthors != null)
			return coauthors;

		java.util.Collection<Author> coAuthors = papers.stream().map(p -> p.getAuthors()).flatMap(x -> x.stream())
				.filter(a -> !a.name.equals(this.name)).collect(Collectors.toCollection(HashSet::new));

		coauthors = new HashSet<>(coAuthors);
		return coauthors;
	}

	public int getId() {
		return id;
	}
}
