package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

/** Representation of an inner class.
 */
public class InnerClass extends ClassBase<InnerClass> implements IStaticable<InnerClass> {
	private boolean isStatic;

	/** Creates a new instance with the given type.
	 * @param pType The inner classes type. (Must have {@link JQName#isInnerClass()}=true.)
	 */
	public InnerClass(JQName pType) {
		super(pType);
	}

	/** Declares this inner class to be static. Equivalent to
	 * {@code makeStatic(true}.
	 * @return This builder.
	 */
	@Nonnull public InnerClass makeStatic() {
		return makeStatic(true);
	}

	/** Declares this inner class to be static, or not.
	 * @param pStatic True, if this inner class should be static, otherwise false.
	 * @return This builder.
	 */
	@Nonnull public InnerClass makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	/** Returns, whether this inner class is static.
	 * @return True, if this inner class is static, otherwise false.
	 */
	public boolean isStatic() {
		return isStatic;
	}
}
