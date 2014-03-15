package com.contaazul.coverage.cobertura;

import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.cobertura.entity.Package;

public class ClazzMapperImpl implements ClazzMapper {
	private final Coverage coverage;
	private final String srcFolder;

	public ClazzMapperImpl(Coverage coverage, String srcFolder) {
		this.coverage = coverage;
		this.srcFolder = srcFolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contaazul.coverage.cobertura.ClazzMapper#getClazz(java.lang.String)
	 */
	@Override
	public Clazz getClazz(String filename) {
		if (!filename.startsWith( srcFolder ))
			return null;
		return extractClazz( getClazzName( filename ) );
	}

	private Clazz extractClazz(String name) {
		final Package pack = extractPackage( name );
		if (pack == null)
			return null;
		return pack.getClass( name );
	}

	private Package extractPackage(String clazzName) {
		return coverage.getPackage( getPackageName( clazzName ) );
	}

	private String getPackageName(String clazzName) {
		return clazzName.substring( 0, clazzName.lastIndexOf( "." ) );
	}

	private String getClazzName(String filename) {
		final String relativeFilename = filename.substring( srcFolder.length() + 1 );
		return relativeFilename.replaceAll( "/", "." ).replace( ".java", "" );
	}
}
