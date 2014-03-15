package com.contaazul.coverage.git;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OneToOneLinePositioner implements LinePositioner {

	@Override
	public int toPosition(int line) {
		return line;
	}

	@Override
	public Map<Integer, Integer> getLinesAdded() {
		return Maps.newHashMap();
	}

	@Override
	public List<Map<Integer, Integer>> getChunks() {
		return Lists.newArrayList();
	}

}
