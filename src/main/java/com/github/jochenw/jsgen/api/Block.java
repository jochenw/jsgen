package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;

public class Block<T extends AbstractBuilder<T>> extends AbstractBuilder<T> {
	public static class Line {
		private final Object elements;
		private final boolean terminated;
		public Line(Object[] pElements, boolean pTerminated) {
			elements = pElements;
			terminated = pTerminated;
		}
		public Line(Iterable<Object> pElements, boolean pTerminated) {
			elements = pElements;
			terminated = pTerminated;
		}
		public Object getElements() {
			return elements;
		}
		public boolean isTerminated() {
			return terminated;
		}
	}
	private List<Object> contents = new ArrayList<>();

	public T line(Object... pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), false));
		return self();
	}

	public T line(Iterable<Object> pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), false));
		return self();
	}

	public T tline(Object... pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), true));
		return self();
	}

	public T tline(Iterable<Object> pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), true));
		return self();
	}

	public List<Object> getContents() {
		return contents;
	}

	public LocalField newJavaField(String pType, String pName) {
		return newJavaField(JQName.valueOf(pType), pName);
	}

	public LocalField newJavaField(Class<?> pType, String pName) {
		return newJavaField(JQName.valueOf(pType), pName);
	}

	public LocalField newJavaField(JQName pType, String pName) {
		final LocalField ljf = new LocalField().type(pType).name(pName);
		contents.add(ljf);
		return ljf;
	}

	@Nonnull public IfBlock newIf(@Nonnull Object... pCondition) {
		final IfBlock ifBlock = new IfBlock().condition(pCondition);
		contents.add(ifBlock);
		return ifBlock;
	}

	@Nonnull T addThrowNew(@Nonnull JQName pType, @Nonnull Object... pValues) {
		assertMutable();
	    return tline(new Throw(pType, pValues));
	}

	@Nonnull T addThrowNew(@Nonnull Class<? extends Throwable> pType, @Nonnull Object... pValues) {
		return addThrowNew(JQName.valueOf(pType), pValues);
	}

	public T self() {
		@SuppressWarnings("unchecked")
		final T t = (T) this;
		return (T) t;
	}
}
