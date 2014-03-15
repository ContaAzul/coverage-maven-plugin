package com.contaazul.coverage.git;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PatchLinePositioner implements LinePositioner {

	private Map<Integer, Integer> lineToPosition = newLinkedHashMap();

	public PatchLinePositioner(String patch) {
		String[] lines = patch.split( "[\\r\\n]+" );
		int position = 0;
		int currentLine = -1;
		for (String line : lines) {
			if (line.startsWith( "@@" ))
				currentLine = getLineNumber( line );

			if (line.startsWith( "+" ))
				lineToPosition.put( currentLine, position );

			if (!line.startsWith( "-" ) && !line.startsWith( "@@" ))
				currentLine++;
			position++;
		}
	}

	@Override
	public int toPosition(int line) {
		if (lineToPosition.containsKey( line ))
			return lineToPosition.get( line );
		return -1;
	}

	@Override
	public Map<Integer, Integer> getLinesAdded() {
		return lineToPosition;
	}

	@Override
	public List<Map<Integer, Integer>> getChunks() {
		final List<Map<Integer, Integer>> chuncks = newArrayList();
		int lastLine = -1;
		for (final Entry<Integer, Integer> line : getLinesAdded().entrySet()) {
			if (line.getKey() == lastLine + 1)
				chuncks.get( chuncks.size() - 1 ).put( line.getKey(), line.getValue() );
			else
				chuncks.add( newHash( line ) );
			lastLine = line.getKey();
		}

		return chuncks;
	}

	private Map<Integer, Integer> newHash(Entry<Integer, Integer> line) {
		final Map<Integer, Integer> hash = newLinkedHashMap();
		hash.put( line.getKey(), line.getValue() );
		return hash;
	}

	private Integer getLineNumber(String line) {
		return new Integer( line.replaceAll( "@@.*\\+", "" ).replaceAll( "\\,.*", "" )
				.replaceAll( "\\D", "" ) );
	}
}
