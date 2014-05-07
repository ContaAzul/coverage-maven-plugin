package com.contaazul.coverage.cobertura.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "condition")
public class Condition {
	@XmlAttribute(name = "number")
	private int number;

	@XmlAttribute(name = "type")
	private String type;

	@XmlAttribute(name = "coverage")
	private String coverage;

	public Condition() {
	}

	public Condition(int number, String type, String coverage) {
		super();
		this.number = number;
		this.type = type;
		this.coverage = coverage;
	}

	public int getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public String getCoverage() {
		return coverage;
	}

	@Override
	public String toString() {
		return "Condition [number=" + number + ", type=" + type + ", coverage="
				+ coverage + "]";
	}
}
