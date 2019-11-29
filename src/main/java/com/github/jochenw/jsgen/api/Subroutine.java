/**
 * Copyright 2018 Jochen Wiedmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


/** Abstract base class for named code blocks, like methods, and constructors.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public abstract class Subroutine<T extends Subroutine<T>> extends CodeBlock<T> implements IAnnotatable<T>, IProtectable<T>, ICommentOwner {
	/** Representation of a method, or constructor parameter.
	 */
	public static class Parameter extends AbstractBuilder<Parameter> implements IField<Parameter> {
		private final AnnotationSet annotations = new AnnotationSet();
		private JQName type;
		private String name;
		private boolean isFinal;
	
		@Override
		public AnnotationSet getAnnotations() {
			return annotations;
		}

		/** Sets the parameters name.
		 * @param pName The parameters name.
		 * @return This builder.
		 */
		public Parameter name(String pName) {
			assertMutable();
			name = pName;
			return this;
		}
	
		public String getName() {
			return name;
		}
	
		/** Sets the parameters type.
		 * @param pType The parameters type.
		 * @return This builder.
		 */
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
	private boolean terse;

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

	/** Returns the list of parameters.
	 * @return The list of parameters, possibly empty. Never null.
	 * @see #parameter(JQName, String)
	 * @see #parameter(Class, String)
	 * @see #parameter(String, String)
	 */
	@Nonnull public List<Parameter> getParameters() {
		return parameters;
	}

	/** Declares am exception, which is thrown by this method, or constructor.
	 * @param pType The exceptions type.
	 * @return This builder.
	 * @see #exception(JQName)
	 * @see #exception(Class)
	 * @see #getExceptions()
	 */
	@Nonnull public T exception(@Nonnull String pType) {
		return exception(JQName.valueOf(pType));
	}

	/** Declares am exception, which is thrown by this method, or constructor.
	 * @param pType The exceptions type.
	 * @return This builder.
	 * @see #exception(JQName)
	 * @see #exception(String)
	 * @see #getExceptions()
	 */
	@Nonnull public T exception(@Nonnull Class<? extends Throwable> pType) {
		return exception(JQName.valueOf(pType));
	}

	/** Declares am exception, which is thrown by this method, or constructor.
	 * @param pType The exceptions type.
	 * @return This builder.
	 * @see #exception(String)
	 * @see #exception(Class)
	 * @see #getExceptions()
	 */
	@Nonnull public T exception(@Nonnull JQName pType) {
		assertMutable();
		exceptions.add(Objects.requireNonNull(pType, "Exception"));
		return self();
	}

	/** Returns the list of thrown exceptions.
	 * @return The list of thrown exceptions, possibly empty. Never null.
	 */
	@Nonnull public List<JQName> getExceptions() {
		return exceptions;
	}

	/** Creates a new parameter with the given type, and name, and adds it to the
	 * methods, or constructors signatures.
	 * @param pType The parameters type.
	 * @param pName The parameters name.
	 * @return The created parameter, a builder object, which can be used fo
	 *   futher configuration.
	 * @see #parameter(JQName, String)
	 * @see #parameter(String, String)
	 * @see #getParameters()
	 */
	@Nonnull public Parameter parameter(@Nonnull Class<?> pType, @Nonnull String pName) {
		return parameter(JQName.valueOf(pType), pName);
	}

	/** Creates a new parameter with the given type, and name, and adds it to the
	 * methods, or constructors signatures.
	 * @param pType The parameters type.
	 * @param pName The parameters name.
	 * @return The created parameter, a builder object, which can be used fo
	 *   futher configuration.
	 * @see #parameter(JQName, String)
	 * @see #parameter(Class, String)
	 * @see #getParameters()
	 */
	public Parameter parameter(@Nonnull String pType, @Nonnull String pName) {
		return parameter(JQName.valueOf(pType), pName);
	}

	/** Creates a new parameter with the given type, and name, and adds it to the
	 * methods, or constructors signatures.
	 * @param pType The parameters type.
	 * @param pName The parameters name.
	 * @return The created parameter, a builder object, which can be used fo
	 *   futher configuration.
	 * @see #parameter(JQName, String)
	 * @see #parameter(String, String)
	 * @see #getParameters()
	 */
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
	
	/** Sets, whether this method, or constructor is annotated with an
	 * {@link SuppressWarnings} annotation with the given value.
	 * @param pValue The warning type, which is being suppressed.
	 * @param pSuppressing True, if this warning type is being suppressed, otherwise
	 *   false (in which case the warning type may be removed).
	 * @return This builder.
	 */
	@Nonnull public T suppressWarning(String pValue, boolean pSuppressing) {
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

	/** Specifies, that this method, or constructor is annotated with an
	 * {@link SuppressWarnings} annotation with the given value. Equivalent
	 * to {@code suppressWarning(pValue, true)}.
	 * @param pValue The warning type, which is being suppressed.
	 * @return This builder.
	 * @see #suppressWarning(String, boolean)
	 * @see #isSuppressing(String)
	 */
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

	/** Sets, that this object should be formatted in a terse style.
	 * Equivalent to {@link #terse(boolean) terse(true)}.
	 */
	@Nullable public T terse() {
		return terse(true);
	}

	/** Sets, whether this object should be formatted in a terse style.
	 * @param pTerse True, if this object should be formatted in a terse style,
	 *   otherwise false.
	 */
	@Nullable public T terse(boolean pTerse) {
		assertMutable();
		if (pTerse &&  body().hasContent(true)) {
			throw new IllegalStateException("A terse method must not have multiple lines.");
		}
		terse = pTerse;
		return self();
	}

	/** Returns, whether this object should be formatted in a terse style.
	 * @return True, if this object should be formatted in a terse style,
	 *   otherwise false.
	 */
	public boolean isTerse() {
		return terse;
	}

	@Override
	public T line(Iterable<Object> pObjects) {
		if (isTerse()  &&  body().hasContent(false)) {
			throw new IllegalStateException("A terse method must not have multiple lines.");
		} else {
			return super.line(pObjects);
		}
	}

	@Override
	public T line(Object... pObjects) {
		if (isTerse()  &&  body().hasContent(false)) {
			throw new IllegalStateException("A terse method must not have multiple lines.");
		} else {
			return super.line(pObjects);
		}
	}

	@Override
	public T tline(Iterable<Object> pObjects) {
		if (isTerse()  &&  body().hasContent(false)) {
			throw new IllegalStateException("A terse method must not have multiple lines.");
		} else {
			return super.tline(pObjects);
		}
	}

	@Override
	public T tline(Object... pObjects) {
		if (isTerse()  &&  body().hasContent(false)) {
			throw new IllegalStateException("A terse method must not have multiple lines.");
		} else {
			return super.tline(pObjects);
		}
	}

	

}
