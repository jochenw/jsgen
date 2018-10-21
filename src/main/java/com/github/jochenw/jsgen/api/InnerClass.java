package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

public class InnerClass extends JSGClass<InnerClass> {
	private boolean isStatic;

	public InnerClass(JQName pType) {
		super(pType);
	}

	@Nonnull public InnerClass makeStatic() {
		return makeStatic(true);
	}

	@Nonnull public InnerClass makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Override
	protected InnerClass self() {
		return this;
	}
}
