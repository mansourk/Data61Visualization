package Data61.DataVisualisationExercise;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class AuthorsTest {

	ApplicationContext context = new ApplicationContext();

	@Before
	public void setUp() {
		context.papers.getPapers().put(1, new Paper(1, "dataset1", "title1", "venue1", 2000));
		context.papers.getPapers().put(2, new Paper(2, "dataset2", "title2", "venue2", 2001));
		context.papers.getPapers().put(3, new Paper(3, "dataset3", "title3", "venue3", 2002));
		context.papers.getPapers().put(4, new Paper(4, "dataset4", "title4", "venue4", 2003));
	}

	@Test
	public void shouldAddAuthorAndLinkWithPapers() {
		String paperAuthor1 = "1,2,Mansour";
		context.authors.new AuthorBuilder(paperAuthor1).run();
		Assert.assertEquals(1, context.authors.authors.size());
		Assert.assertEquals(2, context.authors.authors.get("Mansour").getPapers().size());
	}
	
	@Test
	public void shouldIgnoreInvalidPapersWhenAddingAuthor() {
		String paperAuthor1 = "0,2,Mansour";
		context.authors.new AuthorBuilder(paperAuthor1).run();
		Assert.assertEquals(1, context.authors.authors.size());
		Assert.assertEquals(1, context.authors.authors.get("Mansour").getPapers().size());
	}
	
	@Test
	public void twoAuthorsAreEqualBecauseHaveTheSameName() {
		Author author1 = new Author(1, "Mansour");
		Author author2 = new Author(2, "Mansour");
		Assert.assertTrue(author1.equals(author2));
		Assert.assertTrue(author2.equals(author1));
	}
	
	@Test
	public void AuthorEqualityShouldReturnFalseIfOneofThemIsNull() {
		Author author1 = new Author(1, "Mansour");
		Author author2 = new Author(1, null);
		Assert.assertFalse(author1.equals(author2));
		Assert.assertFalse(author2.equals(author1));
	}

	@Test
	public void AuthorEqualityShouldReturnFalseEvenIfNameofBothAreNull() {
		Author author1 = new Author(1, null);
		Author author2 = new Author(1, null);
		Assert.assertFalse(author1.equals(author2));
		Assert.assertFalse(author2.equals(author1));
	}


}
