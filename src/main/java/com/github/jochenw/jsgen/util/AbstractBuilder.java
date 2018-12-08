package com.github.jochenw.jsgen.util;

public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
	private boolean immutable;

	protected void makeImmutable() {
		immutable = true;
	}

	protected void assertMutable() {
		assertMutable(this);
	}

	public boolean isMutable() {
		return !immutable;
	}

	public T build() {
		makeImmutable();
		return self();
	}

	protected T self() {
		@SuppressWarnings("unchecked")
		final T t = (T) this;
		return t;
	}
	
	public static void assertMutable(AbstractBuilder<?> pBuilder) {
		if (pBuilder.immutable) {
			throw new IllegalStateException("This object is no longer mutable.");
		}
	}
}
