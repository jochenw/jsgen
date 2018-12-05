package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;


/**
 * A block is an unnamed piece of Java code, like a loop (for, while, do..while), or a branch
 * of an if statement. Methods, constructors, static initializers are not blocks themselves.
 * Instead, they contain a block, which can be accessed via the respective {@code body()}
 * method, for example {@link Method#body()}.
 *
 * A block allows adding free form lines via {@link #line(Object...)}, or {@link #line(Iterable)}.
 * It also supports embedding inner blocks via {@link #newBlock()}, {@link #newFor(Object...)},
 * or {@link #newIf(Object...)}. These inner blocks are
 * being indented properly.
 * @param <T> The blocks actual type. This is used to determine the result type of the builder
 *   methods.
 */
public class Block<T extends Block<T>> extends AbstractBuilder<T> implements IBlock<T> {
	/**
	 * This class implements a single line of code. Do not mismatch lines, and statements:
	 * A single line may contain multiple statements. On the other hand, a statement may
	 * extend over multiple lines.
	 */
	public static class Line {
		private final Object elements;
		private final boolean terminated;
		/**
		 * Creates a new line, with the given elements. 
		 * @param pElements The elements, that make up the line. Such elements may be strings,
		 *   numbers, class names (instances of {@link JQName}, or {@link Class}), arrays,
		 *   or collections.
		 * @param pTerminated True, if a ";" as the statement separator should be added at the
		 * end of the line. Otherwise false.
		 */
		public Line(Object[] pElements, boolean pTerminated) {
			elements = pElements;
			terminated = pTerminated;
		}
		/**
		 * Creates a new line, with the given elements. The actual line will be created by concatenating
		 * the elements.
		 * @param pElements The elements, that make up the line. Such elements may be strings,
		 *   numbers, class names (instances of {@link JQName}, or {@link Class}), arrays,
		 *   or collections.
		 * @param pTerminated True, if a ";" as the statement separator should be added at the
		 * end of the line. Otherwise false.
		 */
		public Line(Iterable<Object> pElements, boolean pTerminated) {
			elements = pElements;
			terminated = pTerminated;
		}
		/** Returns the lines elements. The actual line will be created by concatenating
		 * the elements.
		 * 
		 * @return The lines elements. Typically either an object array, or a collection
		 *   of objects, depending on the constructor, which has been used.
		 */
		public Object getElements() {
			return elements;
		}
		/** Whether the line already contains a line terminator.
		 * @return True, if the line already contains a line feed, and no line
		 *   terminator shall be added. Otherwise false.
		 */
		public boolean isTerminated() {
			return terminated;
		}
	}
	private List<Object> contents = new ArrayList<>();

	@Override
	public T line(Object... pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), false));
		return self();
	}

	@Override
	public T line(Iterable<Object> pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), false));
		return self();
	}

	@Override
	public T tline(Object... pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), true));
		return self();
	}

	@Override
	public T tline(Iterable<Object> pObjects) {
		assertMutable();
		contents.add(new Line(Objects.requireAllNonNull(pObjects, "Objects"), true));
		return self();
	}

	/**
	 * Returns the blocks contents: Instances of {@link Line} (lines, created via {@link #line(Object...)},
	 * {@link #line(Iterable)}, {@link #tline(Object...)}, or {@link #tline(Iterable)}), {@link Block} (nested
	 * blocks, created via {@link #newBlock()}, {@link #newFor(Object...)}, or {@link #newIf(Object...)},
	 * {@link LocalField} (local fields, created via {@link #newField(JQName, String)}).
	 * @return The blocks contents: Instances of {@link Line} (lines), {@link Block} (nested
	 * blocks), {@link LocalField} (local fields).
	 */
	public List<Object> getContents() {
		return contents;
	}

	@Override
	public LocalField newField(String pType, String pName) {
		return newField(JQName.valueOf(pType), pName);
	}

	@Override
	public LocalField newField(Class<?> pType, String pName) {
		return newField(JQName.valueOf(pType), pName);
	}

	@Override
	public LocalField newField(JQName pType, String pName) {
		final LocalField ljf = new LocalField().type(pType).name(pName);
		contents.add(ljf);
		return ljf;
	}

	@Override
	@Nonnull public IfBlock newIf(@Nonnull Object... pCondition) {
		final IfBlock ifBlock = new IfBlock().condition(pCondition);
		contents.add(ifBlock);
		return ifBlock;
	}

	@Override
	@Nonnull public IfBlock newIf(@Nonnull Iterable<?> pCondition) {
		final IfBlock ifBlock = new IfBlock().condition(pCondition);
		contents.add(ifBlock);
		return ifBlock;
	}

	@Override
	@Nonnull public DoWhileBlock newDoWhile(@Nonnull Object... pCondition) {
		final DoWhileBlock block = new DoWhileBlock().condition(pCondition);
		contents.add(block);
		return block;
	}

	@Override
	@Nonnull public DoWhileBlock newDoWhile(@Nonnull Iterable<?> pCondition) {
		final DoWhileBlock block = new DoWhileBlock().condition(pCondition);
		contents.add(block);
		return block;
	}

	@Override
	@Nonnull public ForBlock newFor(@Nonnull Object... pCondition) {
		final ForBlock forBlock = new ForBlock().condition(pCondition);
		contents.add(forBlock);
		return forBlock;
	}
	
	@Override
	@Nonnull public ForBlock newFor(@Nonnull Iterable<?> pCondition) {
		final ForBlock forBlock = new ForBlock().condition(pCondition);
		contents.add(forBlock);
		return forBlock;
	}
	
	/** Creates a line with a new "throw new SomeClass();" statement.
	 * @param pType Type of the exception, which is being thrown in the statement.
	 * @param pValues Constructor arguments, if any.
	 * @return This code block.
	 */
	@Nonnull T addThrowNew(@Nonnull JQName pType, @Nonnull Object... pValues) {
		assertMutable();
	    return tline(new Throw(pType, pValues));
	}

	/** Creates a line with a new "throw new SomeClass();" statement.
	 * @param pType Type of the exception, which is being thrown in the statement.
	 * @param pValues Constructor arguments, if any.
	 * @return This code block.
	 */
	@Nonnull T addThrowNew(@Nonnull Class<? extends Throwable> pType, @Nonnull Object... pValues) {
		return addThrowNew(JQName.valueOf(pType), pValues);
	}

	/**
	 * Returns this code block.
	 */
	public T self() {
		@SuppressWarnings("unchecked")
		final T t = (T) this;
		return (T) t;
	}

	/** Creates a non-public, single-line comment, and adds it to the current code line.
	 * @param pText The comments text.
	 * @return This code block.
	 */
	public T comment(String pText) {
		assertMutable();
		final Comment comment = new Comment().text(pText).makePublic(false);
		contents.add(comment);
		return self();
	}

	@Override
	public T comment(String... pText) {
		assertMutable();
		final Comment comment = new Comment().text(pText).makePublic(false);
		contents.add(comment);
		return self();
	}

	@Override
	public T comment(Iterable<String> pText) {
		assertMutable();
		final Comment comment = new Comment().text(pText).makePublic(false);
		contents.add(comment);
		return self();
	}

	/**
	 * Creates a new, nested, block, and adds it to the current block.
	 * @return The created block, which has been added to the current code block.
	 */
	public NestedBlock newBlock() {
		assertMutable();
		final NestedBlock block = new NestedBlock();
		contents.add(block);
		return block;
	}
}
