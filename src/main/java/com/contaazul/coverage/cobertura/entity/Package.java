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
@XmlRootElement(name = "package")
public class Package {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "line-rate")
	private double lineRate;

	@XmlAttribute(name = "branch-rate")
	private double branchRate;

	@XmlAttribute(name = "complexity")
	private double complexity;

	@XmlElement(name = "class")
	@XmlElementWrapper(name = "classes")
	private List<Clazz> classes;

	public String getName() {
		return name;
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

	public List<Clazz> getClasses() {
		if (classes == null)
			classes = new ArrayList<Clazz>();
		return classes;
	}

	@Override
	public String toString() {
		return "Package [name=" + name + ", lineRate=" + lineRate
				+ ", branchRate=" + branchRate + ", complexity="
				+ complexity + "]";
	}

	public Clazz getClass(String name) {
		for (Clazz clazz : getClasses())
			if (clazz.getName().equals(name))
				return clazz;
		return null;
	}

}
