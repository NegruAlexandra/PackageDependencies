package ro.practice.mojo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;


import java.util.Arrays;
import java.util.List;


import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.practice.mojo.CyclicDependencyMojo;


public class CyclicDependencyMojoTest {
	@InjectMocks
	private CyclicDependencyMojo cyclicTest = new CyclicDependencyMojo();

	@Mock
	private MavenProject project;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		//		cyclicTest.hasCyclic(Arrays.asList(cyclicTest));
		//		Set<String> sources = new HashSet<String>(Arrays.asList("C:/Users/info/workspace/Hello/src/main/java","src/main/java", "src/generated/java"));
		List<String> sources = Arrays.asList("C:/Users/info/workspace/Hello/src/main/java", "src/generated/java");
		doReturn(sources).when(project).getCompileSourceRoots();
		
	}
	@Test(expected = MojoExecutionException.class)
	public void test() throws MojoExecutionException, MojoFailureException {
		//fail("Not yet implemented");
		cyclicTest.execute();
		assertNotNull("Tester is null", cyclicTest);
	}

}
