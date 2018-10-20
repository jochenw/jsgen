package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

public class JSGInnerClass extends JSGClass<JSGInnerClass> {
	private boolean isStatic;

	public JSGInnerClass(JSGQName pType) {
		super(pType);
	}

	@Nonnull public JSGInnerClass makeStatic() {
		return makeStatic(true);
	}

	@Nonnull public JSGInnerClass makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Override
	protected JSGInnerClass self() {
		return this;
	}
}
