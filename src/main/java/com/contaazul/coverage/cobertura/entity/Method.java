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
public class Method {

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "signature")
	private String signature;

	@XmlAttribute(name = "line-rate")
	private double lineRate;

	@XmlAttribute(name = "branch-rate")
	private double branchRate;

	@XmlAttribute(name = "complexity")
	private double complexity;

	@XmlElement(name = "line")
	@XmlElementWrapper(name = "lines")
	private List<Line> lines;

	public String getName() {
		return name;
	}

	public String getSignature() {
		return signature;
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

	public List<Line> getLines() {
		if (lines == null)
			lines = new ArrayList<Line>();
		return lines;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", signature=" + signature + ", lineRate=" + lineRate + ", branchRate="
				+ branchRate + ", complexity=" + complexity + "]";
	}

}
