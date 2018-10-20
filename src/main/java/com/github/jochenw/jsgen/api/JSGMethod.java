package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


public class JSGMethod extends JSGSubroutine<JSGMethod> implements IAnnotatable, IProtectable {
	@Nonnull private JSGQName type;
	@Nonnull private IProtectable.Protection protection;
	@Nonnull private String name;
	private boolean isStatic;
	private boolean isAbstract;
	private boolean isFinal;
	private boolean isSynchronized;

	@Override
	protected JSGMethod self() { return this; }

	@Nonnull public JSGMethod returnType(@Nonnull JSGQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	@Nonnull public JSGMethod returnType(@Nonnull Class<?> pType) {
		return returnType(JSGQName.valueOf(pType));
	}

	@Nonnull public JSGMethod returnType(@Nonnull String pType) {
		return returnType(JSGQName.valueOf(pType));
	}

	@Nonnull public JSGQName getReturnType() {
		return type;
	}

	@Nonnull JSGMethod name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	@Nonnull public String getName() {
		return name;
	}

	@Nonnull JSGMethod makeAbstract() {
		return makeAbstract(true);
	}
	
	@Nonnull JSGMethod makeAbstract(boolean pAbstract) {
		assertMutable();
		isAbstract = pAbstract;
		return this;
	}
	
	public boolean isAbstract() {
		return isAbstract;
	}

	@Nonnull JSGMethod makeStatic() {
		return makeStatic(true);
	}
	
	@Nonnull JSGMethod makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Nonnull JSGMethod makeFinal() {
		return makeFinal(true);
	}

	@Nonnull JSGMethod makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Nonnull JSGMethod makeSynchronized() {
		return makeSynchronized(true);
	}

	@Nonnull JSGMethod makeSynchronized(boolean pSynchronized) {
		assertMutable();
		isSynchronized = pSynchronized;
		return this;
	}

	public boolean isSynchronized() {
		return isSynchronized;
	}

	private static final JSGQName OVERRIDE = JSGQName.valueOf(Override.class);

	@Nonnull JSGMethod overriding() {
		return overriding(true);
	}

	@Nonnull JSGMethod overriding(boolean pOverriding) {
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
