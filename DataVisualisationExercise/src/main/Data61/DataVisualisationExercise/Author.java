package Data61.DataVisualisationExercise;

import java.util.concurrent.CopyOnWriteArrayList;

public class Author {

	private final String name;
	private CopyOnWriteArrayList<Paper> papers = new CopyOnWriteArrayList<Paper>();

	public Author(String name) {
		super();
		this.name = name;
	}
	 
    public void addPaper(Paper paper){
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
}
