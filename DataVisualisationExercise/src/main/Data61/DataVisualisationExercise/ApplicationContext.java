package Data61.DataVisualisationExercise;

public class ApplicationContext {

	Authors authors = new Authors(this);
	Papers papers = new Papers();

	public void load(){
		if (papers.getPapers().isEmpty())
			papers.load();
		if (authors.getAuthors().isEmpty())
			authors.load();
	}
	
	public Network buildNetwork() {

		Network network = new Network();

		this.authors.getAuthors().values().stream().forEach(author -> {
			String lable = author.getName() + "|ART:" + author.getPapers().size() 
					+ "|CA:" + author.getCoAuthors().size();
			network.addNode(new Node(lable, author.getId(), true));
		});

		this.authors.getAuthors().values().stream().forEach(author -> {
			author.getCoAuthors().stream().forEach(coauthor -> {
				network.addEdge(author.getId(), coauthor.getId());
			});
		});
		return network;
	}

}
