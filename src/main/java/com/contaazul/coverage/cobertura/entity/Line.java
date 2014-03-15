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
public class Line {

	@XmlAttribute(name = "number")
	private int number;

	@XmlAttribute(name = "hits")
	private int hits;

	@XmlAttribute(name = "branch")
	private boolean branch;

	@XmlAttribute(name = "condition-coverage")
	private String conditionCoverage;

	@XmlElement(name = "condition")
	@XmlElementWrapper(name = "conditions")
	private List<Condition> conditions;

	public Line() {
	}

	public Line(int number, int hits, boolean branch, String conditionCoverage, List<Condition> conditions) {
		super();
		this.number = number;
		this.hits = hits;
		this.branch = branch;
		this.conditionCoverage = conditionCoverage;
		this.conditions = conditions;
	}

	public int getNumber() {
		return number;
	}

	public int getHits() {
		return hits;
	}

	public boolean isBranch() {
		return branch;
	}

	public String getConditionCoverage() {
		return conditionCoverage;
	}

	public List<Condition> getConditions() {
		if (conditions == null)
			conditions = new ArrayList<Condition>();
		return conditions;
	}

	@Override
	public String toString() {
		return "Line [number=" + number + ", hits=" + hits + ", branch=" + branch + ", conditionCoverage="
				+ conditionCoverage + "]";
	}
}
