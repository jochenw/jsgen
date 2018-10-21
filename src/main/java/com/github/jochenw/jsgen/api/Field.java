package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


public class Field extends AbstractBuilder<Field> implements IProtectable, ICommentOwner, IField, IStaticable, IVolatilable {
	private AnnotationSet annotations = new AnnotationSet();
	private @Nonnull Protection protection;
	private @Nonnull JQName type;
	private @Nonnull String name;
	private @Nullable Object value;
	private boolean isStatic, isVolatile, isFinal;
	private Comment comment;

	@Override
	@Nonnull public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull Field protection(@Nonnull Protection pProtection) {
		assertMutable();
		protection = pProtection;
		return this;
	}

	@Nonnull public Field makePublic() {
		return protection(Protection.PUBLIC);
	}
	
	@Nonnull public Field makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public Field makePackageProtected() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public Field makePrivate() {
		return protection(Protection.PRIVATE);
	}
	
	@Override
	@Nonnull public Protection getProtection() {
		return protection;
	}

	@Nonnull Field type(@Nonnull JQName pType) {
		assertMutable();
		type = pType;
		return this;
	}

	@Nonnull public JQName getType() {
		return type;
	}

	@Nonnull Field name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return this;
	}

	@Nonnull public Field assign(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	@Nonnull public Field assign(@Nonnull Iterable<Object> pValues) {
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
	public Field makeStatic() {
		return makeStatic(true);
	}

	@Nonnull public Field makeStatic(boolean pStatic) {
		assertMutable();
		isStatic = pStatic;
		return this;
	}

	public boolean isStatic() {
		return isStatic;
	}

	@Nonnull public Field makeVolatile() {
		return makeVolatile(true);
	}

	@Nonnull public Field makeVolatile(boolean pVolatile) {
		assertMutable();
		isVolatile = pVolatile;
		return this;
	}

	public boolean isVolatile() {
		return isVolatile;
	}

	@Nonnull public Field makeFinal() {
		return makeFinal(true);
	}

	@Nonnull public Field makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	protected Field self() { return this; }

	public Field comment(String... pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return this;
	}

	public Field comment(Iterable<String> pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return this;
	}

	@Nullable public Comment getComment() {
		return comment;
	}
}
