package com.github.jochenw.jsgen.api;

/** Representation of a "throw new FooException()" statement.
 */
public class Throw {
	private final JQName type;
	private final Object constructorArgs;

	/** Creates a new instance with the given type, and the given
	 * constructor args.
	 * @param pType The exception class being instantiated, and thrown.
	 * @param constructorArgs The constructor arguments (an array, or an
	 *   {@link Iterable} of elements, which are being concatenated.
	 */
	public Throw(JQName pType, Object constructorArgs) {
		this.type = pType;
		this.constructorArgs = constructorArgs;
	}

	/** Returns the exception type.
	 * @return The exception type.
	 */
	public JQName getType() {
		return type;
	}

	/** Returns the constructor arguments.
	 * @return The constructor arguments.
	 */
	public Object getConstructorArgs() {
		return constructorArgs;
	}
}
