package com.contaazul.coverage.pullrequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CoberturaMapperTest {
	@Test
	public void testMapEmpty() {
		List<Cobertura> coberturas = Lists.newArrayList();
		Cobertura cobertura = CoberturaMapper.map( coberturas );
		assertNotNull( cobertura );
		assertEquals( 100, cobertura.getCoverage(), 0.001 );
		assertEquals( 0, cobertura.getLastLine() );
	}

	@Test
	public void testMapList() {
		Cobertura c1 = mock( Cobertura.class );
		when( c1.getCoverage() ).thenReturn( 50.0 );
		Cobertura c2 = mock( Cobertura.class );
		when( c2.getCoverage() ).thenReturn( 100.0 );
		Cobertura c3 = mock( Cobertura.class );
		when( c3.getCoverage() ).thenReturn( 20.0 );

		List<Cobertura> coberturas = Arrays.asList( c1, c2, c3 );
		Cobertura cobertura = CoberturaMapper.map( coberturas );
		assertNotNull( cobertura );
		assertEquals( 56.66, cobertura.getCoverage(), 0.01 );
		assertEquals( 0, cobertura.getLastLine() );
	}
}
