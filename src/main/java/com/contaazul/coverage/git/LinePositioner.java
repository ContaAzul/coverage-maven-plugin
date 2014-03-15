package com.contaazul.coverage.git;

import java.util.List;
import java.util.Map;

public interface LinePositioner {

	int toPosition(int line);

	Map<Integer, Integer> getLinesAdded();

	List<Map<Integer, Integer>> getChunks();

}
