package Data61.DataVisualisationExercise;

import java.util.concurrent.CopyOnWriteArrayList;

public class Paper {

	private final int id;
	private final String dataset;
	private final String title;
	private final String venue;
	private final int year;
	private CopyOnWriteArrayList<Author> authors = new CopyOnWriteArrayList<Author>();

	public Paper(int id, String dataset, String title, String venue, int year) {
		super();
		this.id = id;
		this.dataset = dataset;
		this.title = title;
		this.venue = venue;
		this.year = year;
	}

	public void addAuthor(Author author) {
		
		if (!authors.contains(author))
			authors.add(author);
	}

	public int getId() {
		return id;
	}

	public String getDataset() {
		return dataset;
	}

	public String getTitle() {
		return title;
	}

	public String getVenue() {
		return venue;
	}

	public int getYear() {
		return year;
	}

	public CopyOnWriteArrayList<Author> getAuthors() {
		return authors;
	}

}
