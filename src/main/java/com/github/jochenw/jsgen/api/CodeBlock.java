package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;

public abstract class CodeBlock<T1 extends AbstractBuilder<T1>> extends AbstractBuilder<T1> implements IBodyProvider {
	private final Block<?> body = new Block<>();
	private JSGClass<?> sourceClass;

	@Override
	public Block<?> body() {
		return body;
	}

	@Nonnull
	public JSGClass<?> getSourceClass() {
		return sourceClass;
	}

	@Nonnull public T1 sourceClass(JSGClass<?> pClass) {
		assertMutable();
		sourceClass = pClass;
		return self();
	}

	@Nonnull public LocalField newField(@Nonnull JQName pType, @Nonnull String pName) {
		assertMutable();
		return body.newJavaField(pType, pName);
	}

	@Nonnull public LocalField newField(@Nonnull Class<?> pType, @Nonnull String pName) {
		assertMutable();
		return body.newJavaField(pType, pName);
	}

	@Nonnull public LocalField newField(@Nonnull String pType, @Nonnull String pName) {
		assertMutable();
		return body.newJavaField(pType, pName);
	}
}
