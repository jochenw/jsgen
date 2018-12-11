package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.Objects;

/**
 * Representation of a while(...) block.
 */
public class WhileBlock extends Block<WhileBlock> {
	private Object condition;

	/** Creates a new while block with the given condition.
	 * @param pValues The condition, an array of elements, which are being concatenated
	 *   to build the actual condition.
	 * @return The while block, a builder, which can be used for further configuration.
	 */
	public WhileBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Creates a new while block with the given condition.
	 * @param pValues The condition, an {@link Iterable} of elements, which are being concatenated
	 *   to build the actual condition.
	 * @return The while block, a builder, which can be used for further configuration.
	 */
	public WhileBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Returns the while blocks condition.
	 * @return The condition; an array, or {@link Iterable} of elements, which
	 *   are being concatenated.
	 */
	@Nonnull public Object getCondition() {
		return condition;
	}
}
