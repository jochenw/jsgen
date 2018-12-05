package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;


/**
 * A code block is a common base class for named code blocks, like methods ({@link Method}, constructors (@link {@link Constructor}},
 * and for static class initializers (@link {@link StaticInitializer}}.
 * @param <T> The blocks actual type. This is used to determine the result type of the builder
 *   methods.
 */
public abstract class CodeBlock<T extends CodeBlock<T>> extends AbstractBuilder<T> implements IBodyProvider, IBlock<T> {
	private final Block<?> body = new Block<>();
	private JSGClass<?> sourceClass;
	private Comment comment;

	/**
	 * Returns the named code blocks body. The body is, where actual Java code is being added to.
	 */
	@Override
	public Block<?> body() {
		return body;
	}

	/**
	 * Returns the Java class, which contains this code block. Note, that this is not necessarily a Java source
	 * object. Instead, it might be an inner class.
	 * @return This code blocks outer Java class.
	 */
	@Nonnull
	public JSGClass<?> getSourceClass() {
		return sourceClass;
	}

	/**
	 * Sets this code blocks Java class, which contains this code block. Note, that this is not necessarily a Java source
	 * object. Instead, it might be an inner class.
	 * @return This code block.
	 */
	@Nonnull T sourceClass(JSGClass<?> pClass) {
		assertMutable();
		sourceClass = pClass;
		return self();
	}

	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code blocks body.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code blocks body.
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String)
	 */
	@Nonnull public LocalField newField(@Nonnull JQName pType, @Nonnull String pName) {
		assertMutable();
		return body.newField(pType, pName);
	}

	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code blocks body.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code blocks body.
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String)
	 */
	@Nonnull public LocalField newField(@Nonnull Class<?> pType, @Nonnull String pName) {
		assertMutable();
		return body.newField(pType, pName);
	}

	/**
	 * Creates a new Java field with the given name, and type. The fields scope is the current
	 * code blocks body.
	 * @param pType The fields type.
	 * @param pName The fields name.
	 * @return The created field, which has been added to the current code blocks body.
	 * @see #newField(Class, String)
	 * @see #newField(JQName, String)
	 */
	@Nonnull public LocalField newField(@Nonnull String pType, @Nonnull String pName) {
		assertMutable();
		return body.newField(pType, pName);
	}

	@Override
	public T line(Iterable<Object> pObjects) {
		assertMutable();
		body.line(pObjects);
		return self();
	}

	@Override
	public T line(Object... pObjects) {
		assertMutable();
		body.line(pObjects);
		return self();
	}

	@Override
	public T tline(Iterable<Object> pObjects) {
		assertMutable();
		body.tline(pObjects);
		return self();
	}

	@Override
	public T tline(Object... pObjects) {
		assertMutable();
		body.line(pObjects);
		return self();
	}

	@Override
	public IfBlock newIf(Object... pCondition) {
		assertMutable();
		return body.newIf(pCondition);
	}

	@Override
	public ForBlock newFor(Object... pCondition) {
		assertMutable();
		return body.newFor(pCondition);
	}

	/**
	 * Returns this code blocks comment.
	 * @return This code blocks, if any, or null.
	 */
	public Comment getComment() {
		return comment;
	}

	/**
	 * Creates a new public comment, and sets it on the current code block. If there already is a comment
	 * on the current code block, the existing comment will be replaced, and discarded.
	 * @param pText The comments text, one string per line. Note, that this deviates from the semantics
	 *   of line elements, which are being concatenated. 
	 * @return This code block, if any, or null.
	 */
	public T newComment(String... pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return self();
	}

	/**
	 * Creates a new public comment, and sets it on the current code block. If there already is a comment
	 * on the current code block, the existing comment will be replaced, and discarded.
	 * @param pText The comments text, one string per line. Note, that this deviates from the semantics
	 *   of line elements, which are being concatenated. 
	 * @return This code block, if any, or null.
	 */
	public T newComment(Iterable<String> pText) {
		assertMutable();
		comment = new Comment().makePublic().text(pText);
		return self();
	}

	@Override
	public IfBlock newIf(Iterable<?> pCondition) {
		return body().newIf(pCondition);
	}

	@Override
	public DoWhileBlock newDoWhile(Object... pCondition) {
		return body().newDoWhile(pCondition);
	}

	@Override
	public DoWhileBlock newDoWhile(Iterable<?> pCondition) {
		return body().newDoWhile(pCondition);
	}

	@Override
	public ForBlock newFor(Iterable<?> pCondition) {
		return body().newFor(pCondition);
	}
}
