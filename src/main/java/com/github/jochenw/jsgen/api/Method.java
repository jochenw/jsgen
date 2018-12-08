package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


public class Method extends Subroutine<Method> implements IAnnotatable, IProtectable<Method>, IStaticable<Method> {
	@Nonnull private JQName type;
	@Nonnull private IProtectable.Protection protection;
	@Nonnull private String name;
	private boolean isStatic;
	private boolean isAbstract;
	private boolean isFinal;
	private boolean isSynchronized;

	@Override
	protected Method self() { return this; }

	@Nonnull public Method returnType(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	@Nonnull public Method returnType(@Nonnull Class<?> pType) {
		return returnType(JQName.valueOf(pType));
	}

	@Nonnull public Method returnType(@Nonnull String pType) {
		return returnType(JQName.valueOf(pType));
	}

	@Nonnull public JQName getReturnType() {
		return type;
	}

	@Nonnull Method name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	@Nonnull public String getName() {
		return name;
	}

	@Nonnull Method makeAbstract() {
		return makeAbstract(true);
	}
	
	@Nonnull Method makeAbstract(boolean pAbstract) {
		assertMutable();
		isAbstract = pAbstract;
		return this;
	}
	
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

	public boolean isSynchronized() {
		return isSynchronized;
	}

	private static final JQName OVERRIDE = JQName.valueOf(Override.class);

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
	
	public boolean isOverriding() {
		return isAnnotatedWith(OVERRIDE);
	}
}
