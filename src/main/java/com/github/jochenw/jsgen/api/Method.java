package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


/** Representation of a method, that is a member of a class.
 */
public class Method extends Subroutine<Method>
        implements IAbstractable<Method>, IProtectable<Method>, IStaticable<Method> {
	@Nonnull private JQName type;
	@Nonnull private IProtectable.Protection protection;
	@Nonnull private String name;
	private boolean isStatic;
	private boolean isAbstract;
	private boolean isFinal;
	private boolean isSynchronized;

	@Override
	protected Method self() { return this; }

	/** Sets the methods return type.
	 * @param pType The methods return type. "void" for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	/** Sets the methods return type.
	 * @param pType The methods return type. {@link Void#TYPE} for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull Class<?> pType) {
		return returnType(JQName.valueOf(pType));
	}

	/** Sets the methods return type.
	 * @param pType The methods return type. {@link JQName#VOID_TYPE} for
	 *   void methods.
	 * @return This builder.
	 */
	@Nonnull public Method returnType(@Nonnull String pType) {
		return returnType(JQName.valueOf(pType));
	}

	/** Returns the methods return type.
	 * @return The methods return type. {@link JQName#VOID_TYPE} for
	 *   void methods.
	 */
	@Nonnull public JQName getReturnType() {
		return type;
	}

	@Nonnull Method name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	/** Returns the methods name.
	 * @return The methods name.
	 */
	@Nonnull public String getName() {
		return name;
	}

	
	@Nonnull public Method makeAbstract() {
		return makeAbstract(true);
	}
	
	@Nonnull public Method makeAbstract(boolean pAbstract) {
		assertMutable();
		isAbstract = pAbstract;
		return this;
	}

	/** Returns, whether this method is abstract.
	 * @return True, if this method is abstract, otherwise false.
	 */
	public boolean isAbstract() {
		return isAbstract;
	}

	@Nonnull Method makeStatic() {
		return makeStatic(true);
	}
	
	@Nonnull Method makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Nonnull Method makeFinal() {
		return makeFinal(true);
	}

	@Nonnull Method makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	@Nonnull public Method makePublic() {
		return protection(Protection.PUBLIC);
	}
	
	@Nonnull public Method makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public Method makePackagePrivate() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public Method makePrivate() {
		return protection(Protection.PUBLIC);
	}

	@Nonnull public Method protection(Protection pProtection) {
		protection = pProtection;
		return this;
	}

	@Nonnull public Protection getProtection() {
		return protection;
	}

	/** Returns, whether this method is final.
	 * @return True, if this method is final, otherwise false.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	@Nonnull Method makeSynchronized() {
		return makeSynchronized(true);
	}

	@Nonnull Method makeSynchronized(boolean pSynchronized) {
		assertMutable();
		isSynchronized = pSynchronized;
		return this;
	}

	/** Returns, whether this method is synchronized.
	 * @return True, if this method is synchronized, otherwise false.
	 */
	public boolean isSynchronized() {
		return isSynchronized;
	}

	private static final JQName OVERRIDE = JQName.valueOf(Override.class);

	/** Specified, that this method is being annotated with {@link Override}.
	 * @return This builder.
	 */
	@Nonnull
	public Method overriding() {
		return overriding(true);
	}

	@Nonnull Method overriding(boolean pOverriding) {
		assertMutable();
		if (pOverriding) {
			annotation(OVERRIDE);
		}
		return this;
	}

	/** Returns, whether this method is annotated with {@link Override}.
	 * @return True, if this method is annotated with {@link Override},
	 *   otherwise false.
	 */
	public boolean isOverriding() {
		return isAnnotatedWith(OVERRIDE);
	}
}
