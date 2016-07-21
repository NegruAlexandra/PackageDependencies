package ro.practice.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import ro.practice.model.DependencyViolation;
import ro.practice.utils.CyclicDependencyUtils;

@Mojo(name = "checkCyclicDependency")
public class CyclicDependencyMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	public void execute() throws MojoExecutionException, MojoFailureException {
		List<DependencyViolation> allList = new ArrayList<DependencyViolation>();

		for (Object locationObject : project.getCompileSourceRoots()) {
			try {
				List<File> cycList = CyclicDependencyUtils.hasCyclic(new File(locationObject.toString()));
				for (File cyclicFile : cycList) {
					allList.add(new DependencyViolation(cyclicFile));
				}
			} catch (IOException e1) {
				throw new MojoExecutionException(e1.getMessage(), e1);
			}

		}
		if (!allList.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (DependencyViolation violation : allList) {
				builder.append("\nCyclicDependency @ --->  ").append(violation.getClassName()).append("\n");
			}
			throw new MojoExecutionException(builder.toString());
		}

	}
}