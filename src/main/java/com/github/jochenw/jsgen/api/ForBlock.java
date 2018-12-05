package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.Objects;

/**
 * Representation of a for(...) block.
 */
public class ForBlock extends Block<ForBlock> {
	private Object condition;

	/** Sets the blocks condition, as an array of elements. The actual condition is
	 * built by concatenating the elements.
	 * @param pValues The blocks condition.
	 * @return This builder.
	 */
	ForBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Sets the blocks condition, as an {@link Iterable} of elements. The actual condition is
	 * built by concatenating the elements.
	 * @param pValues The blocks condition.
	 * @return This builder.
	 */
	public ForBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Returns the for blocks condition.
	 * @return The for blocks condition, as an array, or {@link Iterable} of elements.
	 */
	@Nonnull public Object getCondition() {
		return condition;
	}
}
