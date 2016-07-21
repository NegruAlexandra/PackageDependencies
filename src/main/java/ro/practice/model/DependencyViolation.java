package ro.practice.model;

import java.io.File;

public class DependencyViolation {
	private File className;
	private String illegalPackage;
	
	
	public DependencyViolation(File className) {
		super();
		this.className = className;
	}
	public DependencyViolation(File className, String illegalPackage) {
		super();
		this.className = className;
		this.illegalPackage = illegalPackage;
	}
	public File getClassName() {
		return className;
	}
	public void setClassName(File className) {
		this.className = className;
	}
	public String getIllegalPackage() {
		return illegalPackage;
	}
	public void setIllegalPackage(String illegalPackage) {
		this.illegalPackage = illegalPackage;
	}
	@Override
	public String toString() {
		return "DependencyViolation [className=" + className + ", illegalPackage=" + illegalPackage + "]";
	}
	
	

}
