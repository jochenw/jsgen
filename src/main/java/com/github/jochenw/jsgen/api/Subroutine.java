package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


public abstract class Subroutine<T extends Subroutine<T>> extends CodeBlock<T> implements IAnnotatable, IProtectable<T>, ICommentOwner {
	public static class Parameter extends AbstractBuilder<Parameter> implements IField {
		private final AnnotationSet annotations = new AnnotationSet();
		private JQName type;
		private String name;
		private boolean isFinal;
	
		@Override
		public AnnotationSet getAnnotations() {
			return annotations;
		}
	
		public Parameter name(String pName) {
			assertMutable();
			name = pName;
			return this;
		}
	
		public String getName() {
			return name;
		}
	
		public Parameter type(JQName pType) {
			assertMutable();
			type = pType;
			return this;
		}
	
		public JQName getType() {
			return type;
		}

		/**
		 * Returns null, because no value can be assigned to a parameter.
		 * @return The value null.
		 */
		@Override
		public Object getValue() { return null; }
		@Override
		protected Parameter self() { return this; }

		@Nonnull
		Parameter makeFinal() {
			return makeFinal(true);
		}

		@Nonnull
		Parameter makeFinal(boolean pFinal) {
			assertMutable();
			isFinal = pFinal;
			return this;
		}

		@Override
		public boolean isFinal() {
			return isFinal;
		}
	}
	private final AnnotationSet annotations = new AnnotationSet();
	private final List<Parameter> parameters = new ArrayList<>();
	private final List<JQName> exceptions = new ArrayList<>();
	private Protection protection;
	private Comment comment;

	@Override
	public AnnotationSet getAnnotations() {
		return annotations;
	}

	@Nonnull public T protection(@Nonnull IProtectable.Protection pProtection) {
		assertMutable();
		protection = pProtection;
		return self();
	}

	@Nonnull public T makePublic() {
		return protection(Protection.PUBLIC);
	}

	@Nonnull public T makeProtected() {
		return protection(Protection.PROTECTED);
	}

	@Nonnull public T makePackagePrivate() {
		return protection(Protection.PACKAGE);
	}

	@Nonnull public T makePrivate() {
		return protection(Protection.PRIVATE);
	}

	@Override
	public Protection getProtection() {
		return protection;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	@Nonnull T exception(@Nonnull String pType) {
		return exception(JQName.valueOf(pType));
	}

	@Nonnull T exception(@Nonnull Class<?> pType) {
		return exception(JQName.valueOf(pType));
	}

	@Nonnull T exception(@Nonnull JQName pType) {
		assertMutable();
		exceptions.add(Objects.requireNonNull(pType, "Exception"));
		return self();
	}

	public List<JQName> getExceptions() {
		return exceptions;
	}

	public Parameter parameter(@Nonnull Class<?> pType, @Nonnull String pName) {
		return parameter(JQName.valueOf(pType), pName);
	}

	public Parameter parameter(@Nonnull String pType, @Nonnull String pName) {
		return parameter(JQName.valueOf(pType), pName);
	}

	public Parameter parameter(@Nonnull JQName pType, @Nonnull String pName) {
		assertMutable();
		final Parameter pb = new Parameter().type(pType).name(pName);
		parameters.add(pb);
		return pb;
	}

	@Nonnull T suppressWarning(String pValue) {
		return suppressWarning(pValue, true);
	}

	private static final JQName SUPPRESSWARNINGS = JQName.valueOf(SuppressWarnings.class);
	
	@Nonnull T suppressWarning(String pValue, boolean pSuppressing) {
		final Annotation annotation = getAnnotation(SUPPRESSWARNINGS);
		if (annotation == null) {
			if (pSuppressing) {
				annotation(SUPPRESSWARNINGS).attribute("unchecked", pValue);
			}
		} else {
			List<String> values = new ArrayList<>();
			final Object uncheckedValue = annotation.getAttributeValue("unchecked");
			if (uncheckedValue != null) {
				if (uncheckedValue instanceof String) {
					values.add((String) uncheckedValue);
				} else if (uncheckedValue instanceof String[]) {
					final String[] array = (String[]) uncheckedValue;
					values.addAll(Arrays.asList(array));
				}
			}
			final boolean modified;
			if (values.contains(pValue)) {
				if (!pSuppressing) {
					values.remove(pValue);
					modified = true;
				} else {
					modified = false;
				}
			} else {
				if (pSuppressing) {
					values.add(pValue);
					modified = true;
				} else {
					modified = false;
				}
			}
			if (modified) {
				annotation.attribute("unchecked", values.toArray(new String[values.size()]));
			}
		}
		return self();
	}

	public boolean isSuppressing(String pValue) {
		final Annotation annotation = getAnnotation(SUPPRESSWARNINGS);
		if (annotation == null) {
			return false;
		} else {
			final Object uncheckedValue = annotation.getAttributeValue("unchecked");
			if (uncheckedValue == null) {
				return false;
			} else if (uncheckedValue instanceof String) {
				return pValue.equals(uncheckedValue);
			} else if (uncheckedValue instanceof String[]) {
				final String[] values = (String[]) uncheckedValue;
				for (String v : values) {
					if (pValue.equals(v)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	@Nonnull public T comment(String... pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return self();
	}

	@Nonnull public T comment(Iterable<String> pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return self();
	}

	@Nullable public Comment getComment() {
		return comment;
	}
}
