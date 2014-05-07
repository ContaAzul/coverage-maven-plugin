package com.contaazul.coverage.git;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PatchLinePositioner implements LinePositioner {

	private Map<Integer, Integer> lineToPosition = newLinkedHashMap();

	public PatchLinePositioner(String patch) {
		String[] lines = patch.split("[\\r\\n]+");
		int position = 0;
		int currentLine = -1;
		for (String line : lines) {
			if (line.startsWith("@@"))
				currentLine = getLineNumber(line);

			if (line.startsWith("+"))
				lineToPosition.put(currentLine, position);

			if (!line.startsWith("-") && !line.startsWith("@@"))
				currentLine++;
			position++;
		}
	}

	@Override
	public int toPosition(int line) {
		if (lineToPosition.containsKey(line))
			return lineToPosition.get(line);
		return -1;
	}

	@Override
	public Map<Integer, Integer> getLinesAdded() {
		return lineToPosition;
	}

	@Override
	public List<Map<Integer, Integer>> getChunks() {
		final List<Map<Integer, Integer>> chunks = newArrayList();
		int lastLine = -1;
		for (final Entry<Integer, Integer> line : getLinesAdded().entrySet()) {
			addLine(chunks, lastLine, line);
			lastLine = line.getKey();
		}
		return chunks;
	}

	private void addLine(final List<Map<Integer, Integer>> chunks,
			int lastLine, final Entry<Integer, Integer> line) {
		if (line.getKey() == lastLine + 1)
			chunks.get(chunks.size() - 1).put(line.getKey(), line.getValue());
		else
			chunks.add(newChunk(line));
	}

	private Map<Integer, Integer> newChunk(Entry<Integer, Integer> line) {
		final Map<Integer, Integer> hash = newLinkedHashMap();
		hash.put(line.getKey(), line.getValue());
		return hash;
	}

	private Integer getLineNumber(String line) {
		return Integer.valueOf(line.replaceAll("@@.*\\+", "")
				.replaceAll("\\,.*", "")
				.replaceAll("\\D", ""));
	}
}
