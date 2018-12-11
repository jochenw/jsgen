package com.github.jochenw.jsgen.impl;


/** A format object is the specification of a Java source code layout.
 * By choosing a particular format object, you choose how the generated
 * Java source files will look like.
 * The format object has no impact on the actual contents. It is only
 * used to control questions like indentation, where to place opening, and
 * closing braces, and so on.
 */
public abstract class Format {
	/** An object, which causes a line terminator being written.
	 */
	public static final Object NEWLINE= new Object();
	/** An object, which causes an increment of the block indentation level.
	 */
	public static final Object INC_INDENT= new Object();
	/** An object, which causes a decrement of the block indentation level.
	 */
	public static final Object DEC_INDENT= new Object();
	/** An object, which causes the indentation of the current line.
	 */
	public static final Object INDENT = new Object();
	/** An object, which causes that nothing is being written.
	 */
	public static final Object NOTHING = new Object[0];

	private final String indentString, lineTerminator;

	protected Format(String pIndentString, String pLineTerminator) {
		indentString = pIndentString;
		lineTerminator = pLineTerminator;
	}

	/** Returns the string, which is used to indent one level.
	 * Example: "    " (four blanks).
	 * @return The string, which is used to indent one level.
	 */
	public String getIndentString() {
		return indentString;
	}

	/** Returns the string, which is used to terminate a single line (the line separator).
	 * Examples: "\r\n" (Windows), or "\n" (Linux, or Unix)
	 * @return The string, which is used to terminate a single line (the line separator).
	 */
	public String getLineTerminator() {
		return lineTerminator;
	}

	/** Returns the annotation prefix, typically "@".
	 * @return The annotation prefix, typically "@".
	 */
	public abstract Object getAnnotationPrefix();
	/** Returns the annotation separator, which is used to separate two different annotations.
	 * The default implementation is " ".
	 * @return The annotation separator, which is used to separate two different annotations.
	 *   The default implementation is " ".
	 */
	public abstract Object getAnnotationSeparator();
	/** Returns the annotation set prefix, which is used to introduce a non-empty set of
	 * annotations. The default implementation is {@link #INDENT}.
	 * @return The annotation set prefix, which is used to introduce a non-empty set of
	 *   annotations. The default implementation is {@link #INDENT}.
	 */
	public abstract Object getAnnotationSetPrefix();
	/** Returns the annotation set prefix, which is used to introduce a non-empty set of
	 * annotations. The default implementation is {@link #NEWLINE}.
	 * @return The annotation set prefix, which is used to introduce a non-empty set of
	 *   annotations. The default implementation is {@link #NEWLINE}.
	 */
	public abstract Object getAnnotationSetSuffix();
	public abstract Object getAnnotationValuesPrefix();
	public abstract Object getAnnotationValueSeparator();
	public abstract Object getAnnotationValueAssignment();
	public abstract Object getAnnotationValuesSuffix();
	public abstract Object getBlockTerminator();
	public abstract Object getClassBlockHeader();
	public abstract Object getClassBlockFooter();
	public abstract Object getClassCommentPrefix();
	public abstract Object getClassCommentSuffix();
	public abstract Object getCommentPrivatePrefix();
	public abstract Object getCommentPrivateSeparator();
	public abstract Object getCommentPrivateSuffix();
	public abstract Object getCommentPublicPrefix();
	public abstract Object getCommentPublicSeparator();
	public abstract Object getCommentPublicSuffix();
	public abstract Object getCommentSingleLinePrefix();
	public abstract Object getCommentSingleLineSuffix();
	public abstract Object getDoWhileBlockHeader();
	public abstract Object getDoWhileBlockTerminator();
	public abstract Object getDoWhileTerminator();
	public abstract Object getFieldPrefix();
	public abstract Object getFieldValueAssignment();
	public abstract Object getFieldSuffix();
	public abstract Object getForConditionPrefix();
	public abstract Object getForConditionSuffix();
	public abstract Object getIfConditionPrefix();
	public abstract Object getIfConditionSuffix();
	public abstract Object getInitializerHeader();
	public abstract Object getInitializerFooter();
	public abstract Object getLinePrefix();
	public abstract Object getLineSuffix();
	public abstract Object getLineSuffixTerminated();
	public abstract Object getMethodDeclarationPrefix();
	public abstract Object getMethodDeclarationSuffix();
	public abstract Object getMethodParameterPrefix();
	public abstract Object getMethodParameterSeparator();
	public abstract Object getMethodParameterSuffix();
	public abstract Object getNestedBlockFooter();
	public abstract Object getNestedBlockHeader();
	public abstract Object getPackageCommentPrefix();
	public abstract Object getPackageCommentSuffix();
	public abstract Object getThrowsPrefix();
	public abstract Object getThrowsConstructorArgsPrefix();
	public abstract Object getThrowsConstructorArgsSuffix();
	public abstract Object getWhileConditionPrefix();
	public abstract Object getWhileConditionSuffix();
}
