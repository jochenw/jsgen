package com.github.jochenw.jsgen.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.util.AbstractBuilder;

public interface IAnnotatable {
	public static class Annotation {
		private final Map<String,Object> attributes = new HashMap<>();
		private final JQName type;
		public Annotation(JQName pType) {
			type = pType;
		}
		@Nonnull
		public JQName getType() { return type; }
		@Nonnull public Map<String,Object> getAttributes() { return attributes; }
		@Nullable public Object getAttributeValue(@Nonnull String pAttributeName) {
			return getAttributes().get(pAttributeName);
		}
		@Nonnull public Object requireAttributeValue(@Nonnull String pAttributeName) throws NoSuchElementException {
			final Object object = getAttributeValue(pAttributeName);
			if (object == null) {
				throw new NoSuchElementException("No such attribute name: " + pAttributeName);
			}
			return object;
		}
		@Nonnull public Annotation attribute(@Nonnull String pName, @Nonnull Object pValue) {
			attributes.put(pName, pValue);
			return this;
		}
	}
	public static class AnnotationSet {
		private final Map<JQName,Annotation> annotations = new HashMap<>();
		@Nullable Annotation get(@Nonnull JQName pName) { return annotations.get(pName); }
		public void add(@Nonnull Annotation pAnnotation) {
			annotations.put(pAnnotation.getType(), pAnnotation);
		}
		public boolean isEmpty() {
			return annotations.isEmpty();
		}
		public void forEach(Consumer<Annotation> pAction) {
			annotations.values().forEach(pAction);
		}
		public Collection<Annotation> getAnnotations() {
			return annotations.values();
		}
	}

	@Nullable public default Annotation getAnnotation(JQName pType) {
		return getAnnotations().get(pType);
	}
	@Nullable public default Annotation getAnnotation(Class<?> pType) {
		return getAnnotation(JQName.valueOf(pType));
	}
	@Nullable public default Annotation getAnnotation(String pType) {
		return getAnnotation(JQName.valueOf(pType));
	}
	@Nonnull public default Annotation requireAnnotation(JQName pType) throws NoSuchElementException {
		final Annotation annotation = getAnnotation(pType);
		if (annotation == null) {
			throw new NoSuchElementException("No such Annotation: " + pType.getQName());
		}
		return annotation;
	}
	@Nonnull public default Annotation requireAnnotation(Class<?> pType) throws NoSuchElementException {
		return requireAnnotation(JQName.valueOf(pType));
	}
	@Nonnull public default Annotation requireAnnotation(String pType) throws NoSuchElementException {
		return requireAnnotation(JQName.valueOf(pType));
	}
	public default boolean isAnnotatedWith(JQName pType) {
		return getAnnotation(pType) != null;
	}
	public default boolean isAnnotatedWith(Class<?> pType) {
		return getAnnotation(pType) != null;
	}
	public default boolean isAnnotatedWith(String pType) {
		return getAnnotation(pType) != null;
	}
	@Nonnull AnnotationSet getAnnotations();
	public default void addAnnotation(@Nonnull Annotation pAnnotation) {
		if (this instanceof AbstractBuilder) {
			AbstractBuilder.assertMutable((AbstractBuilder<?>) this);
		}
		getAnnotations().add(pAnnotation);
	}
	@Nonnull public default Annotation annotation(JQName pType) {
		final Annotation annotation = new Annotation(pType);
		addAnnotation(annotation);
		return annotation;
	}
	@Nonnull public default Annotation annotation(Class<?> pType) {
		return annotation(JQName.valueOf(pType));
	}
	@Nonnull public default Annotation annotation(String pType) {
		return annotation(JQName.valueOf(pType));
	}
}
