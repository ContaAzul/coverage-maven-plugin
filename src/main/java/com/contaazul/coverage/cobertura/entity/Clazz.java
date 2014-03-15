package com.contaazul.coverage.cobertura.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "class")
public class Clazz {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "filename")
	private String filename;

	@XmlAttribute(name = "line-rate")
	private double lineRate;

	@XmlAttribute(name = "branch-rate")
	private double branchRate;

	@XmlAttribute(name = "complexity")
	private double complexity;

	@XmlElement(name = "method")
	@XmlElementWrapper(name = "methods")
	private List<Method> methods;

	@XmlElement(name = "line")
	@XmlElementWrapper(name = "lines")
	private List<Line> lines;

	public List<Line> getLines() {
		if (lines == null)
			lines = new ArrayList<Line>();
		return lines;
	}

	public String getName() {
		return name;
	}

	public String getFilename() {
		return filename;
	}

	public double getLineRate() {
		return lineRate;
	}

	public double getBranchRate() {
		return branchRate;
	}

	public double getComplexity() {
		return complexity;
	}

	public List<Method> getMethods() {
		if (methods == null)
			methods = new ArrayList<Method>();
		return methods;
	}

	@Override
	public String toString() {
		return "Clazz [name=" + name + ", filename=" + filename + ", lineRate=" + lineRate + ", branchRate="
				+ branchRate + ", complexity=" + complexity + "]";
	}
}
