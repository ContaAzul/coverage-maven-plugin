package com.contaazul.coverage.cobertura;

import com.contaazul.coverage.cobertura.entity.Clazz;

public interface ClazzMapper {
	Clazz getClazz(String filename);
}
