package ro.practice.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import ro.practice.model.DependencyViolation;
import ro.practice.model.Rule;
import ro.practice.utils.CyclicDependencyUtils;

@Mojo(name = "checkRules")
public class RulesMojo extends AbstractMojo {

	@Parameter(property = "checkRules")
	private List<Rule> rules;

	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;

	public void execute() throws MojoExecutionException {
		
		getLog().info(rules.toString());

		List<DependencyViolation> list = new ArrayList<DependencyViolation>();
		
		for (Object locationObject : project.getCompileSourceRoots()) {
			for(Rule rule : rules) {
				String location = locationObject.toString();
				Set<File> classes = CyclicDependencyUtils.getClassesInPackage(rule.getDepends(), location);

				for (File clazz : classes) {
					getLog().info(clazz.getName());
					try {
						List<String> imports = CyclicDependencyUtils.getImportsInClass(clazz);
						for (String imp : imports) {
							getLog().info(imp);
							if (imp.contains(rule.getPack())) {
								list.add(new DependencyViolation(clazz, rule.getPack()));
							}
						}
					} catch (IOException e) {
						throw new MojoExecutionException(e.getMessage(), e);
					}

					
				}
			}
		}
		if (!list.isEmpty()) {
			StringBuilder build = new StringBuilder();
			for (DependencyViolation violation : list) {
				build.append("Illegal dependency ").append(violation.getClassName());
				build.append(" to ").append(violation.getIllegalPackage()).append("\n");
			}
			throw new MojoExecutionException(build.toString());
		}
	}

	public void setRules(List<Rule> rules) {
		this.rules  = rules;
	}
}

