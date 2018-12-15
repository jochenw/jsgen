package com.github.jochenw.jsgen.util;


/** Abstract base class for creating builders.
 * @param <T> The builders actual class, for use as
 *   the result type in builder methods.
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
	private boolean immutable;

	protected void makeImmutable() {
		immutable = true;
	}

	/** Asserts, that this builder is still mutable.
	 * An {@link IllegalStateException} is thrown otherwise.
	 * @throws IllegalStateException The builder is no longer
	 *   mutable.
	 */
	protected void assertMutable() throws IllegalStateException {
		assertMutable(this);
	}

	/** Returns, whether is builder is still mutable.
	 * @return True, if the builder is still mutable.
	 */
	public boolean isMutable() {
		return !immutable;
	}

	/** Makes the builder immutable, and creates the
	 * target object. The default implementation returns
	 * the builder itself.
	 * @return This builder.
	 */
	public T build() {
		makeImmutable();
		return self();
	}

	protected T self() {
		@SuppressWarnings("unchecked")
		final T t = (T) this;
		return t;
	}
	
	/** Asserts, that the given builder is still mutable.
	 * An {@link IllegalStateException} is thrown otherwise.
	 * @param pBuilder The builder, which is being checked
	 *   for mutability.
	 * @throws IllegalStateException The builder is no longer
	 *   mutable.
	 */
	public static void assertMutable(AbstractBuilder<?> pBuilder) {
		if (pBuilder.immutable) {
			throw new IllegalStateException("This object is no longer mutable.");
		}
	}
}
