package Data61.DataVisualisationExercise;

/*
 * This class loads papers and authors and builds network. 
 */
public class ApplicationContext {

	Authors authors = new Authors(this);
	Papers papers = new Papers();

	private void load() {
		if (papers.getPapers().isEmpty())
			papers.load();
		if (authors.getAuthors().isEmpty())
			authors.load();
	}

	public Network buildNetwork() {

		load();

		Network network = Network.getNetworkInstance();
		// for (int i = 0; i < 12; ++i)
		// network.addNode(new Node(i + "", i));
		// network.addEdge(0, 1);
		// network.addEdge(0, 2);
		// network.addEdge(0, 3);
		// network.addEdge(0, 4);
		// network.addEdge(1, 2);
		// network.addEdge(1, 9);
		// network.addEdge(1, 10);
		// network.addEdge(4, 5);
		// network.addEdge(5, 3);
		// network.addEdge(5, 6);
		// network.addEdge(5, 7);
		// network.addEdge(7, 8);
		// network.addEdge(8, 6);
		// network.addEdge(10, 11);
		// network.addEdge(11, 9);

		this.authors.getAuthors().values().stream().forEach(author -> {
			String lable = author.getName() + "|ART:" + author.getPapers().size() + "|CA:"
					+ author.getCoAuthors().size();
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
