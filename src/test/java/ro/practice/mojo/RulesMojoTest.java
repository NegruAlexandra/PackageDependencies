package ro.practice.mojo;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.practice.model.Rule;
import ro.practice.mojo.RulesMojo;
public class RulesMojoTest {

	@InjectMocks
	private RulesMojo tester = new RulesMojo();

	@Mock
	private Rule rule;

	@Mock
	private MavenProject project;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		tester.setRules(Arrays.asList(rule));
		
		
		List<String> sources = Arrays.asList("C:/Users/info/workspace/Hello/src/main/java","src/main/java", "src/generated/java");
		doReturn(sources).when(project).getCompileSourceRoots();
		doReturn("ro.practica.business").when(rule).getDepends();
		doReturn("ro.practica.ui").when(rule).getPack();
		}

	@Test(expected = MojoExecutionException.class)
	public void testExecute() throws MojoExecutionException {
		//fail("Not yet implemented");

		tester.execute();
		assertNotNull("Tester is null", tester);
	}


}

