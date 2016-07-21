package ro.practice.model;

import org.apache.maven.plugins.annotations.Parameter;

public class Rule {
	
	@Parameter
	private String pack;
	
	@Parameter
	private String depends;
	
	@Parameter
	private String info;
	
	
	@Override
	public String toString() {
		return "Rule [pack=" + pack + ", dependency=" + depends + "]"+", info="+ info+"]\n";
	}


	public String getPack() {
		return pack;
	}


	public void setPack(String pack) {
		this.pack = pack;
	}


	public String getDepends() {
		return depends;
	}


	public void setDepends(String depends) {
		this.depends = depends;
	}

	public String getInfo(){
		return info;
	}
	
	public void setInfo(String info){
		this.info = info;
	}
	

}
