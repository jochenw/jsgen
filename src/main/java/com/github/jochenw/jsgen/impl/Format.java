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
	/** Returns the annotation set suffix, which is used to terminate a non-empty set of
	 * annotations. The default implementation is {@link #NEWLINE}.
	 * @return The annotation set prefix, which is used to introduce a non-empty set of
	 *   annotations. The default implementation is {@link #NEWLINE}.
	 */
	public abstract Object getAnnotationSetSuffix();
	/** Returns the annotation set suffix, which is used to terminate a non-empty set of
	 * annotations. The default implementation is " " (a single blank).
	 * @return The annotation set prefix, which is used to introduce a non-empty set of
	 *   annotations. The default implementation is {@link #NEWLINE}.
	 */
	public abstract Object getAnnotationSetSuffixTerse();
	/** Returns the prefix, which is used for introduction of an annotations attributes.
	 * The default implementation is "(".
	 * @return The prefix, which is used for introduction of an annotations attributes.
	 * The default implementation is "(".
	 * @see #getAnnotationValuesSuffix()
	 */
	public abstract Object getAnnotationValuesPrefix();
	/** Returns the separator for an annotations attributes.
	 * The default implementation is ", ".
	 * @return The separator for an annotations attributes.
	 * The default implementation is ", ".
	 */
	public abstract Object getAnnotationValueSeparator();
	/** Returns the separator for an annotation attributes name, and value.
	 * The default implementation is "=".
	 * @return The separator for an annotation attributes name, and value.
	 * The default implementation is "=".
	 */
	public abstract Object getAnnotationValueAssignment();
	/** Returns the suffix, which is used for terminatimg an annotations attributes.
	 * The default implementation is "(".
	 * @return The suffix, which is used for terminating an annotations attributes.
	 * The default implementation is "(".
	 * @see #getAnnotationValuesPrefix()
	 */
	public abstract Object getAnnotationValuesSuffix();
	/** Returns the sequence for terminating an anonymous code block.
	 * This sequence should include, in particular, the values "}",
	 * {@link #NEWLINE}, and {@link #DEC_INDENT}, in a format specific
	 * order. The default implementation is
	 * {@link #DEC_INDENT}, {@link #INDENT}, "}", {@link #NEWLINE}.
	 * 
	 * @return The sequence for terminating an anonymous code block.
	 */
	public abstract Object getBlockTerminator();
	/** Returns the sequence for terminating a terse, anonymous code block.
	 * The default implementation is "}".
	 * 
	 * @return The sequence for terminating an anonymous code block.
	 */
	public abstract Object getBlockTerminatorTerse();
	/** Returns the sequence for terminating an anonymous code block, which
	 * is being followed by another, connected block. This is used, in
	 * particular, before an "else if", or an "else" block.
	 * 
	 * @return The sequence for terminating an anonymous code block, which
	 * is being followed by another, connected block.
	 */
	public abstract Object getBlockTerminatorTemporary();
	/** Returns the sequence for starting a classes code block, This sequence will follow
	 * the declaration of the classes properties, like "public class Foo extends Bar", etc.
	 * The default implementation is " ", "{", {@link #INC_INDENT}, {@link #NEWLINE}
	 * @return The sequence for starting a classes code block.
	 */
	public abstract Object getClassBlockHeader();
	/** Returns the sequence for terminating a classes code block. This sequence will
	 * follow the declrataions of all the classes members, effectively closing the class
	 * specification. The default implementation is {@link #DEC_INDENT},
	 * {@link #INDENT}, "}", {@link #NEWLINE}.
	 * @return The sequence for terminating a classes code block.
	 */
	public abstract Object getClassBlockFooter();
	/** Returns the prefix for a classes comment. The default implementation is
	 * {@link #NOTHING}.
	 * @return The prefix for a classes comment. The default implementation is
	 * {@link #NOTHING}.
	 */
	public abstract Object getClassCommentPrefix();
	/** Returns the suffix for a classes comment. The default implementation is
	 * {@link #NOTHING}.
	 * @return The suffix for a classes comment. The default implementation is
	 * {@link #INDENT}.
	 */
	public abstract Object getClassCommentSuffix();
	/** Returns the prefix of a private comment. The default implementation is
	 * {@link #INDENT}, "/*".
	 * @return The prefix of a private comment. The default implementation is
	 * {@link #INDENT}, "/*".
	 */
	public abstract Object getCommentPrivatePrefix();
	/** Returns the separator for two lines of a private comment. The default
	 * implementation is {@link #NEWLINE}, {@link #INDENT}, "  * ".
	 * @return The separator for two lines of a private comment. The default
	 * implementation is {@link #NEWLINE}, {@link #INDENT}, "  * ".
	 */
	public abstract Object getCommentPrivateSeparator();
	/** Returns the suffix of a private comment. The default implementation is
	 * {@link #NEWLINE}, {@link #INDENT}, {@literal " &ast;/"}, {@link #NEWLINE}.
	 * @return The suffix of a private comment. The default implementation is
     * {@link #NEWLINE}, {@link #INDENT}, {@literal " &ast;/"}, {@link #NEWLINE}.
	 */
	public abstract Object getCommentPrivateSuffix();
	/** Returns the prefix of a public comment. The default implementation is
	 * {@link #INDENT}, "/**".
	 * @return The prefix of a public comment. The default implementation is
	 * {@link #INDENT}, "/**".
	 */
	public abstract Object getCommentPublicPrefix();
	/** Returns the separator for two lines of a public comment. The default
	 * implementation is {@link #NEWLINE}, {@link #INDENT}, "   * ".
	 * @return The separator for two lines of a public comment. The default
	 * implementation is {@link #NEWLINE}, {@link #INDENT}, "   * ".
	 */
	public abstract Object getCommentPublicSeparator();
	/** Returns the suffix of a public comment. The default implementation is
	 * {@link #NEWLINE}, {@link #INDENT}, {@literal "  &ast;/"}, {@link #NEWLINE}.
	 * @return The suffix of a public comment. The default implementation is
     * {@link #NEWLINE}, {@link #INDENT}, {@literal "  &ast;/"}, {@link #NEWLINE}.
	 */
	public abstract Object getCommentPublicSuffix();
	/** Returns a single line comments prefix, default implementation is
	 * {@link #INDENT}, "// ".
	 * @return A single line comments prefix, default implementation is
	 * {@link #INDENT}, "// ".
	 */
	public abstract Object getCommentSingleLinePrefix();
	/** Returns a single line comments suffix, default implementation is
	 * {@link #NEWLINE}, "// ".
	 * @return A single line comments suffix. The default implementation is
	 * {@link #NEWLINE}, "// ".
	 */
	public abstract Object getCommentSingleLineSuffix();
	/** Returns a do ... while ... blocks header, default implementation
	 * is {@link #INDENT}, "do {", {@link #NEWLINE}, {@link #INC_INDENT}
	 * @return A do ... while ... blocks header. The default implementation
	 * is {@link #INDENT}, "do {", {@link #NEWLINE}, {@link #INC_INDENT}
	 */
	public abstract Object getDoWhileBlockHeader();
	/** Returns a do ... while ... blocks terminator, effectively the
	 * separator of the code block, and the condition. The default implementation
	 * is {@link #DEC_INDENT}, {@link #INDENT}, "while (".
	 * @return A do ... while ... blocks terminator, effectively the
	 * separator of the code block, and the condition. The default implementation
	 * is {@link #DEC_INDENT}, {@link #INDENT}, "while (".
	 */
	public abstract Object getDoWhileBlockTerminator();
	/** Returns the terminator of a do ... while ... blocks condition.
	 *The default implementation is ")", {@link #NEWLINE}.
	 * @return The terminator of a do ... while ... blocks condition.
	 *The default implementation is ")", {@link #NEWLINE}.
	 */
	public abstract Object getDoWhileTerminator();
	/** Returns the prefix of a field declaration (default {@link #INDENT}).
	 * @return The prefix of a field declaration (default {@link #INDENT}).
	 */
	public abstract Object getFieldPrefix();
	/** Returns the sequence for starting a fields value assignment
	 * (default " = ").
	 * @return The sequence for starting a fields value assignment
	 * (default " = ").
	 */
	public abstract Object getFieldValueAssignment();
	/** Returns the suffix of a field declaration (default ";", {@link #NEWLINE}).
	 * @return The suffix of a field declaration (default ";", {@link #NEWLINE}).
	 */
	public abstract Object getFieldSuffix();
	/** Returns the prefix of a for ... blocks condition (default
	 * {@link #INDENT}, "for (").
	 * @return The prefix of a for ... blocks condition (default
	 * {@link #INDENT}, "for (").
	 */
	public abstract Object getForConditionPrefix();
	/** Returns the suffix of a for ... blocks condition (default
	 * ")", "{", {@link #INC_INDENT}, {@link #NEWLINE}.
	 * @return The suffix of a for ... blocks condition (default
	 * ")", "{", {@link #INC_INDENT}, {@link #NEWLINE}.
	 */
	public abstract Object getForConditionSuffix();
	/** Returns the prefix of an if ... blocks condition (default
	 * {@link #INDENT}, "if (").
	 * @return The prefix of a for ... blocks condition (default
	 * {@link #INDENT}, "if (").
	 */
	public abstract Object getIfConditionPrefix();
	/** Returns the suffix of an if ... blocks condition (default
	 * ")", "{", {@link #INC_INDENT}, {@link #NEWLINE}.
	 * @return The suffix of a for ... blocks condition (default
	 * ")", "{", {@link #INC_INDENT}, {@link #NEWLINE}.
	 */
	public abstract Object getIfConditionSuffix();
	/** Returns the prefix of an else if ... blocks condition (default
	 * {@link #INDENT}, "else if (").
	 * @return The prefix of a for ... blocks condition (default
	 * {@link #INDENT}, "else if (").
	 */
	public abstract Object getElseIfConditionPrefix();
	/** Returns an else blocks introduction (default
	 * {@link #INDENT}, "else ").
	 * @return An else blocks introduction (default
	 * {@link #INDENT}, "else ").
	 */
	public abstract Object getElseCondition();
	/** Returns the prefix for a static initializer (default
	 * {@link #INDENT}, "{", {@link #INC_INDENT}, {@link #NEWLINE}).
	 * @return The prefix for a static initializer (default
	 * {@link #INDENT}, "{", {@link #INC_INDENT}, {@link #NEWLINE}).
	 */
	public abstract Object getInitializerHeader();
	/** Returns the suffix for a static initializer (default
	 * {@link #DEC_INDENT}, {@link #INDENT}, "}", {@link #NEWLINE}).
	 * @return The suffix for a static initializer (default
	 * {@link #DEC_INDENT}, {@link #INDENT}, "}", {@link #NEWLINE}).
	 */
	public abstract Object getInitializerFooter();
	/** Returns the prefix for a basic line of code
	 * (default {@link #INDENT}).
	 * @return The prefix for a basic line of code
	 *   (default {@link #INDENT}).
	 */
	public abstract Object getLinePrefix();
	/** Returns the suffix for a basic line of code
	 * (default {@link #NEWLINE}).
	 * @return The suffix for a basic line of code
	 *   (default {@link #NEWLINE}).
	 */
	public abstract Object getLineSuffix();
	/** Returns the suffix for a basic line of code
	 * in terse mode
	 * (default "").
	 * @return The suffix for a basic line of code
	 *   in terse mode
	 *   (default "").
	 */
	public abstract Object getLineSuffixTerse();
	/** Returns the suffix for a basic line of code in terse mode,
	 * which needs being
	 *   terminated with a ";" (default ";", {@link #NEWLINE}).
	 * @return The suffix for a basic line of code, which needs being
	 *   terminated with a ";" (default {@link #NEWLINE}).
	 */
	public abstract Object getLineSuffixTerminated();
	/** Returns the suffix for a basic line of code in terse mode,
	 *   which needs being terminated with a ";" (default ";").
	 * @return The suffix for a basic line of code, which needs being
	 *   terminated with a ";" (default ";").
	 */
	public abstract Object getLineSuffixTerminatedTerse();
	/** Returns the prefix for a method declaration (default
	 * {@link #INDENT})
	 * @return The prefix for a method declaration (default
	 * {@link #INDENT})
	 */
	public abstract Object getMethodDeclarationPrefix();
	/** Returns the prefix for a terse method declaration (default
	 * {@link #INDENT})
	 * @return The prefix for a terse method declaration (default
	 * {@link #INDENT})
	 */
	public abstract Object getMethodDeclarationPrefixTerse();
	/** Returns the suffix for a method declaration (default
	 * "{", {@link #INC_INDENT}, {@link #NEWLINE}
	 * @return The suffix for a method declaration (default
	 * "{", {@link #INC_INDENT}, {@link #NEWLINE}
	 */
	public abstract Object getMethodDeclarationSuffix();
	/** Returns the suffix for a terse method declaration (default
	 * "{ ")
	 * @return The suffix for a method declaration (default
	 * "{ ")
	 */
	public abstract Object getMethodDeclarationSuffixTerse();
	/** Returns the prefix of a methods parameter list (default
	 * ")".
	 * @return The prefix of a methods parameter list (default
	 * ")".
	 */
	public abstract Object getMethodParameterPrefix();
	/** Returns the separator for a methods parameter list (default
	 * ", ").
	 * @return The separator for a methods parameter list (default
	 * ", ").
	 */
	public abstract Object getMethodParameterSeparator();
	/** Returns the prefix of a methods parameter list (default
	 * ") ").
	 * @return The prefix of a methods parameter list (default
	 * ") ").
	 */
	public abstract Object getMethodParameterSuffix();
	/** Returns the prefix for a nested block (default
	 * "{", {@link #INC_INDENT}, {@link #NEWLINE}).
	 * @return The prefix for a nested block (default
	 * "{", {@link #INC_INDENT}, {@link #NEWLINE}).
	 */
	public abstract Object getNestedBlockHeader();
	/** Returns the prefix for a nested block (default
	 * {@link #INDENT}, {@link #DEC_INDENT}, {@link #INDENT}, "}",
	 * {@link #NEWLINE}).
	 * @return The prefix for a nested block (default
	 * {@link #INDENT}, {@link #DEC_INDENT}, {@link #INDENT}, "}",
	 * {@link #NEWLINE}).
	 */
	public abstract Object getNestedBlockFooter();
	/** Returns the prefix for the package comment
	 * (default {@link #NOTHING}).
	 * @return The prefix for the package comment
	 * (default {@link #NOTHING}).
	 */
	public abstract Object getPackageCommentPrefix();
	/** Returns the suffix for the package comment
	 * (default {@link #INDENT}).
	 * @return The suffix for the package comment
	 * (default {@link #INDENT}).
	 */
	public abstract Object getPackageCommentSuffix();
	/** Returns the prefix for a "throw new" statement
	 * (default "throw new ").
	 * @return The prefix for a "throw new" statement
	 * (default "throw new ").
	 */
	public abstract Object getThrowsPrefix();
	/** Returns the prefix for a "throw new" statements
	 * list of constructor arguments (default "(").
	 * @return The prefix for a "throw new" statements
	 * list of constructor arguments (default "(").
	 */
	public abstract Object getThrowsConstructorArgsPrefix();
	/** Returns the suffix for a "throw new" statements
	 * list of constructor arguments (default ")").
	 * @return The suffix for a "throw new" statements
	 * list of constructor arguments (default ")").
	 */
	public abstract Object getThrowsConstructorArgsSuffix();
	/** Returns the prefix for a "while ..." blocks
	 * condition (default {@link #INDENT}, "while (").
	 * @return The prefix for a "while ..." blocks
	 * condition (default {@link #INDENT}, "while (").
	 */
	public abstract Object getWhileConditionPrefix();
	/** Returns the suffix for a "while ..." blocks
	 * condition (default ") {", INC_INDENT, NEWLINE).
	 * @return The suffix for a "while ..." blocks
	 * condition (default ") {", INC_INDENT, NEWLINE).
	 */
	public abstract Object getWhileConditionSuffix();

}
