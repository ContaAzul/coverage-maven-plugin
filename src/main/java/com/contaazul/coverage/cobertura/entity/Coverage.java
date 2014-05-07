package com.contaazul.coverage.cobertura.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coverage")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coverage {

	@XmlElement(name = "package")
	@XmlElementWrapper(name = "packages")
	private List<Package> packages;

	public List<Package> getPackages() {
		if (packages == null)
			packages = new ArrayList<Package>();
		return packages;
	}

	public Package getPackage(String name) {
		for (Package pack : getPackages())
			if (pack.getName().equals(name))
				return pack;
		return null;
	}
}
