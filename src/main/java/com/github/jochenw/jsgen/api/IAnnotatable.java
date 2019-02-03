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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;


/** Common base interface for all objects, that may be annotated using Java annotations.
 * @param <T> The actual object type. Used to specify the return type for builder methods.
 */
public interface IAnnotatable<T extends IAnnotatable<T>> {
	/**
	 * Representation of an annotation.
	 */
	public static class Annotation {
		private final Map<String,Object> attributes = new HashMap<>();
		private final JQName type;
		/**
		 * Creates a new annotation, with the given type.
		 * @param pType The annotations type.
		 */
		public Annotation(JQName pType) {
			type = pType;
		}
		/** Returns the annotations type.
		 * @return The annotations type.
		 */
		@Nonnull
		public JQName getType() { return type; }
		/**
		 * Returns the annotations attributes, as a map of attribute names, and values.
		 * @return The annotations attributes.
		 */
		@Nonnull public Map<String,Object> getAttributes() { return attributes; }
		/**
		 * Returns the given attributes value.
		 * @param pAttributeName The attribute name, for which to return the value.
		 * @return Attribute value, if present, or null, if the given attribute name
		 *   is unspecified.
		 */
		@Nullable public Object getAttributeValue(@Nonnull String pAttributeName) {
			return getAttributes().get(pAttributeName);
		}
		/**
		 * Returns the given attributes value.
		 * @param pAttributeName The attribute name, for which to return the value.
		 * @return Attribute value, if present. A {@link NoSuchElementException} is
		 * thrown otherwise.
		 * @throws NoSuchElementException No attribute with the given name has been
		 * specified.
		 */
		@Nonnull public Object requireAttributeValue(@Nonnull String pAttributeName) throws NoSuchElementException {
			final Object object = getAttributeValue(pAttributeName);
			if (object == null) {
				throw new NoSuchElementException("No such attribute name: " + pAttributeName);
			}
			return object;
		}
		/** Sets the attribute with the given name to the given value.
		 * @param pName The attributes name.
		 * @param pValue The attributes value.
		 * @return This builder.
		 */
		@Nonnull public Annotation attribute(@Nonnull String pName, @Nonnull Object pValue) {
			attributes.put(pName, pValue);
			return this;
		}
	}
	/** This class represents a set of annotations. The purpose is to simplify the {@link IAnnotatable}
	 * interface.
	 */
	public static class AnnotationSet {
		private final Map<JQName,Annotation> annotations = new HashMap<>();
		/** Returns the annotation with the given type.
		 * @return The annotation with the given name, if present, or null.
		 */
		@Nullable Annotation get(@Nonnull JQName pType) { return annotations.get(pType); }
		/** Adds the given annotation to the annotation set.
		 * @param pAnnotation The annotation to add to the annotation set.
		 */
		public void add(@Nonnull Annotation pAnnotation) {
			annotations.put(pAnnotation.getType(), pAnnotation);
		}
		/** Returns, whether the annotation set is empty.
		 * @return True, if the annotation set is empty. Otherwise false.
		 */
		public boolean isEmpty() {
			return annotations.isEmpty();
		}
		/** Performs the given action for each annotation in the annotation set.
		 * @param pAction The action being invoked.
		 */
		public void forEach(Consumer<Annotation> pAction) {
			annotations.values().forEach(pAction);
		}
		/** Returns the collection of annotations in the set.
		 * @return The collection of annotations.
		 */
		public Collection<Annotation> getAnnotations() {
			return annotations.values();
		}
	}

	/** Returns the annotation with the given type.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any, or null.
	 */
	@Nullable public default Annotation getAnnotation(JQName pType) {
		return getAnnotations().get(pType);
	}
	/** Returns the annotation with the given type. Equivalent to
	 * {@code getAnnotation(JQName.valueOf(pType))}.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any, or null.
	 */
	@Nullable public default Annotation getAnnotation(Class<? extends java.lang.annotation.Annotation> pType) {
		return getAnnotation(JQName.valueOf(pType));
	}
	/** Returns the annotation with the given type. Equivalent to
	 * {@code getAnnotation(JQName.valueOf(pType))}.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any, or null.
	 */
	@Nullable public default Annotation getAnnotation(String pType) {
		return getAnnotation(JQName.valueOf(pType));
	}
	/** Returns the annotation with the given type.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any. Throws a {@link NoSuchElementException},
	 *   if no such annotation is present.
	 * @throws NoSuchElementException No such annotation is present.
	 */
	@Nonnull public default Annotation requireAnnotation(JQName pType) throws NoSuchElementException {
		final Annotation annotation = getAnnotation(pType);
		if (annotation == null) {
			throw new NoSuchElementException("No such Annotation: " + pType.getQName());
		}
		return annotation;
	}
	/** Returns the annotation with the given type. Equivalent to
	 * {@code requireAnnotation(JQName.valueOf(pType))}.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any. Throws a {@link NoSuchElementException},
	 *   if no such annotation is present.
	 * @throws NoSuchElementException No such annotation is present.
	 */
	@Nonnull public default Annotation requireAnnotation(Class<? extends java.lang.annotation.Annotation> pType) throws NoSuchElementException {
		return requireAnnotation(JQName.valueOf(pType));
	}
	/** Returns the annotation with the given type. Equivalent to
	 * {@code requireAnnotation(JQName.valueOf(pType))}.
	 * @param pType The annotations type.
	 * @return The annotation with the requested type, if any. Throws a {@link NoSuchElementException},
	 *   if no such annotation is present.
	 * @throws NoSuchElementException No such annotation is present.
	 */
	@Nonnull public default Annotation requireAnnotation(String pType) throws NoSuchElementException {
		return requireAnnotation(JQName.valueOf(pType));
	}
	/** Returns, whether an annotation with the given type is present.
	 * @param pType The annotation type.
	 * @return True, if an annotation with the given type is present. Otherwise false.
	 */
	public default boolean isAnnotatedWith(JQName pType) {
		return getAnnotation(pType) != null;
	}
	/** Returns, whether an annotation with the given type is present. Equivalent to
	 * {@code isAnnotatedWith(JQName.valueOf(pType))}.
	 * @param pType The annotation type.
	 * @return True, if an annotation with the given type is present. Otherwise false.
	 */
	public default boolean isAnnotatedWith(Class<? extends java.lang.annotation.Annotation> pType) {
		return getAnnotation(pType) != null;
	}
	/** Returns, whether an annotation with the given type is present. Equivalent to
	 * {@code isAnnotatedWith(JQName.valueOf(pType))}.
	 * @param pType The annotation type.
	 * @return True, if an annotation with the given type is present. Otherwise false.
	 */
	public default boolean isAnnotatedWith(String pType) {
		return getAnnotation(pType) != null;
	}
	/** Returns the set of annotations.
	 * @return The set of annotations, possibly empty.
	 */
	@Nonnull AnnotationSet getAnnotations();
	/** Adds the given annotation to the set of annotations.
	 * @param pAnnotation The annotation to add.
	 */
	public default void addAnnotation(@Nonnull Annotation pAnnotation) {
		if (this instanceof AbstractBuilder) {
			AbstractBuilder.assertMutable((AbstractBuilder<?>) this);
		}
		getAnnotations().add(pAnnotation);
	}
	/** Creates an annotation with the given type, and adds it to the set of annotations.
	 * @param pType The type of the annotation, which is being created.
	 * @return An annotation with the given type, and no attribute, which has been
	 *   created, and added to this {@link IAnnotatable}.
	 */
	@Nonnull public default Annotation annotation(JQName pType) {
		final Annotation annotation = new Annotation(pType);
		addAnnotation(annotation);
		return annotation;
	}
	/** Creates an annotation with the given type, and adds it to the set of annotations.
	 * Equivalent to {@code annotation(JQName.valueOf(pType))}.
	 * @param pType The type of the annotation, which is being created.
	 * @return An annotation with the given type, and no attribute, which has been
	 *   created, and added to this {@link IAnnotatable}.
	 */
	@Nonnull public default Annotation annotation(Class<? extends java.lang.annotation.Annotation> pType) {
		return annotation(JQName.valueOf(pType));
	}
	/** Creates an annotation with the given type, and adds it to the set of annotations.
	 * Equivalent to {@code annotation(JQName.valueOf(pType))}.
	 * @param pType The type of the annotation, which is being created.
	 * @return An annotation with the given type, and no attribute, which has been
	 *   created, and added to this {@link IAnnotatable}.
	 */
	@Nonnull public default Annotation annotation(String pType) {
		return annotation(JQName.valueOf(pType));
	}
}
