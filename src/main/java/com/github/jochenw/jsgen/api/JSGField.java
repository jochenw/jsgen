package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


public class JSGField extends AbstractBuilder<JSGField> implements IProtectable, ICommentOwner, IField, IStaticable, IVolatilable {
	private AnnotationSet annotations = new AnnotationSet();
	private @Nonnull Protection protection;
	private @Nonnull JSGQName type;
	private @Nonnull String name;
	private @Nullable Object value;
	private boolean isStatic, isVolatile, isFinal;
	private JSGComment comment;

	@Override
	@Nonnull public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull JSGField protection(@Nonnull Protection pProtection) {
		assertMutable();
		protection = pProtection;
		return this;
	}

	@Nonnull public JSGField makePublic() {
		return protection(Protection.PUBLIC);
	}
	
	@Nonnull public JSGField makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public JSGField makePackageProtected() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public JSGField makePrivate() {
		return protection(Protection.PRIVATE);
	}
	
	@Override
	@Nonnull public Protection getProtection() {
		return protection;
	}

	@Nonnull JSGField type(@Nonnull JSGQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	@Nonnull public JSGQName getType() {
		return type;
	}

	@Nonnull JSGField name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	@Nonnull public JSGField assign(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	@Nonnull public JSGField assign(@Nonnull Iterable<Object> pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	@Nullable public Object getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Nonnull
	public JSGField makeStatic() {
		return makeStatic(true);
	}

	@Nonnull public JSGField makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Nonnull public JSGField makeVolatile() {
		return makeVolatile(true);
	}

	@Nonnull public JSGField makeVolatile(boolean pVolatile) {
		assertMutable();
		isVolatile = pVolatile;
		return this;
	}

	public boolean isVolatile() {
		return isVolatile;
	}

	@Nonnull public JSGField makeFinal() {
		return makeFinal(true);
	}

	@Nonnull public JSGField makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	protected JSGField self() { return this; }

	public JSGField comment(String... pText) {
		assertMutable();
		comment = new JSGComment().makePublic().text(pText);
		return this;
	}

	public JSGField comment(Iterable<String> pText) {
		assertMutable();
		comment = new JSGComment().makePublic().text(pText);
		return this;
	}

	@Nullable public JSGComment getComment() {
		return comment;
	}
}
