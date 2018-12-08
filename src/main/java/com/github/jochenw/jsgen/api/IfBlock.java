package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.Objects;


/**
 * Representation of an if(...) block.
 */
public class IfBlock extends Block<IfBlock> {
	private Object condition;

	/** Specifies the if blocks condition. The array elements will be concatenated.
	 * @param pValues The if blocks condition, as an array of elements. 
	 * @return This builder.
	 */
	public IfBlock condition(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Specifies the if blocks condition. The {@link Iterable iterable} will be concatenated.
	 * @param pValues The if blocks condition, as an {@link Iterable iterable} of elements. 
	 * @return This builder.
	 */
	public IfBlock condition(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (condition != null) {
			throw new IllegalStateException("A condition has already been assigned.");
		}
		condition = pValues;
		return this;
	}

	/** Returns the blocks condition.
	 * @return The blocks condition, an array, or an iterable of elements.
	 */
	@Nonnull public Object getCondition() {
		return condition;
	}
}
