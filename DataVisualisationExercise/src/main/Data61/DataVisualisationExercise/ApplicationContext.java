package Data61.DataVisualisationExercise;

public class ApplicationContext {

	Authors authors = new Authors(this);
	Papers papers = new Papers();
	
	public void load() {
		papers.load();
		authors.load();
	}
}

