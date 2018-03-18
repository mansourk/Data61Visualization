package Data61.DataVisualisationExercise;

public class ApplicationContext {

	Authors authors = new Authors(this);
	Papers papers = new Papers();

	public Network buildNetwork() {

		if (papers.getPapers().isEmpty())
			papers.load();
		if (authors.getAuthors().isEmpty())
			authors.load();

		Network network = new Network();

//		this.authors.getAuthors().values().stream().forEach(author -> {
//			network.addNode(new Node(author.getName(), author.getId()));
//		});
//
//		this.authors.getAuthors().values().stream().forEach(author -> {
//			author.getCoAuthors().stream().forEach(coauthor -> {
//				network.addEdge(author.getId(), coauthor.getId());
//			});
//		});

		this.authors.getAuthors().values().stream().filter(a -> a.getPapers().size() > 8).forEach(author -> {
			network.addNode(new Node(author.getName(), author.getId()));
		});

		this.authors.getAuthors().values().stream().filter(a -> a.getPapers().size() > 8).forEach(author -> {
			author.getCoAuthors().stream().filter(a -> a.getPapers().size() > 8).forEach(coauthor -> {
				network.addEdge(author.getId(), coauthor.getId());
			});
		});

		return network;
	}

}
