package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;

public class LocalField extends AbstractBuilder<LocalField> implements ICommentOwner, IField {
	private final AnnotationSet annotations = new AnnotationSet();
	private boolean isFinal;
	private JSGQName type;
	private String name;
	private Object value;
	@Nullable private JSGComment comment;

	@Override
	@Nonnull protected LocalField self() {
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Nonnull public JSGQName getType() {
		return type;
	}

	@Nonnull public String getName() {
		return name;
	}

	@Nonnull public LocalField name(@Nonnull String pName) {
		assertMutable();
		name = pName;
		return self();
	}

	@Nonnull public LocalField type(@Nonnull String pType) {
		return type(JSGQName.valueOf(pType));
	}

	@Nonnull public LocalField type(@Nonnull Class<?> pType) {
		return type(JSGQName.valueOf(pType));
	}

	@Nonnull public LocalField type(@Nonnull JSGQName pType) {
		assertMutable();
		type = pType;
		return self();
	}

	@Nonnull public LocalField makeFinal() {
		return makeFinal(true);
	}

	@Nonnull public LocalField makeFinal(boolean pFinal) {
		assertMutable();
		isFinal = pFinal;
		return self();
	}

	@Override
	public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull public LocalField assign(@Nonnull Object... pValues) {
		assertMutable();
		Objects.requireAllNonNull(pValues, "Values");
		if (value != null) {
			throw new IllegalStateException("A value has already been assigned to this field.");
		}
		value = pValues;
		return this;
	}

	@Nonnull public LocalField assign(@Nonnull Iterable<Object> pValues) {
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

	@Nonnull LocalField comment(String... pText) {
		assertMutable();
		comment = new JSGComment().text(pText);
		return this;
	}

	@Nonnull LocalField comment(Iterable<String> pText) {
		assertMutable();
		comment = new JSGComment().text(pText);
		return this;
	}

	@Nullable public JSGComment getComment() {
		return comment;
	}
}
