package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.Objects;

public class JSGDoWhileBlock extends Block<JSGDoWhileBlock> {
	private Object condition;

	public JSGDoWhileBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	public JSGDoWhileBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	@Nonnull public Object getCondition() {
		return condition;
	}
}
