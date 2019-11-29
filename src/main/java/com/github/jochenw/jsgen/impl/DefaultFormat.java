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


/** Default implementation of {@link Format}. It's roughly like the
 * Eclipse default format.
 */
public class DefaultFormat extends Format {
	/** Creates a new instance with the given inden string, and the given
	 * line terminator.
	 * @param pIndentString The string, which is used to indent one block
	 *   level, typically four blanks ("    ").
	 * @param pLineTerminator The string, which is used to terminate a
	 *   single line, typically "\r\n" (Windows), or "\n" (Linux, and
	 *   Unix).
	 */
	public DefaultFormat(String pIndentString, String pLineTerminator) {
		super(pIndentString, pLineTerminator);
	}

	private static final Object ANNOTATION_PREFIX = "@";
	private static final Object ANNOTATION_SEPARATOR = " ";
	private static final Object ANNOTATION_SET_PREFIX = INDENT;
	private static final Object ANNOTATION_SET_SUFFIX = NEWLINE;
	private static final Object ANNOTATION_SET_SUFFIX_TERSE = " ";
	private static final Object ANNOTATION_VALUE_ASSIGNMENT = "=";
	private static final Object ANNOTATION_VALUE_SEPARATOR = ", ";
	private static final Object ANNOTATION_VALUES_PREFIX = "(";
	private static final Object ANNOTATION_VALUES_SUFFIX = ")";
	private static final Object BLOCK_HEADER = new Object[] { "{", INC_INDENT, NEWLINE };
	private static final Object BLOCK_TERMINATOR = new Object[] {DEC_INDENT, INDENT, "}", NEWLINE};
	private static final Object BLOCK_TERMINATOR_TERSE = new Object[] {" }", NEWLINE};
	private static final Object BLOCK_TERMINATOR_TEMPORARY = new Object[] {DEC_INDENT, INDENT, "} "};
	private static final Object CLASS_COMMENT_PREFIX = NOTHING;
	private static final Object CLASS_COMMENT_SUFFIX = new Object[] { INDENT };
	private static final Object COMMENT_SINGLE_LINE_PREFIX = new Object[] { INDENT, "// " };
	private static final Object COMMENT_SINGLE_LINE_SUFFIX = new Object[] { NEWLINE };
	private static final Object COMMENT_PUBLIC_PREFIX = new Object[] { INDENT, "/** " };
	private static final Object COMMENT_PRIVATE_PREFIX = new Object[] { INDENT, "/* " };
	private static final Object COMMENT_PUBLIC_SEPARATOR = new Object[] { NEWLINE, INDENT, "  * " };
	private static final Object COMMENT_PRIVATE_SEPARATOR = new Object[] { NEWLINE, INDENT, " * " };
	private static final Object COMMENT_PUBLIC_SUFFIX = new Object[] { NEWLINE, INDENT, "  */", NEWLINE };
	private static final Object COMMENT_PRIVATE_SUFFIX = new Object[] { NEWLINE, INDENT, " */", NEWLINE };
	private static final Object CLASS_BLOCK_HEADER = new Object[] { " ", BLOCK_HEADER };
	private static final Object CLASS_BLOCK_FOOTER = new Object[] { DEC_INDENT, INDENT, "}", NEWLINE };
	private static final Object DO_WHILE_BLOCK_HEADER = new Object[] { INDENT, "do {", NEWLINE, INC_INDENT };
	private static final Object DO_WHILE_BLOCK_TERMINATOR = new Object[] { DEC_INDENT, INDENT, "while (" };
	private static final Object DO_WHILE_TERMINATOR = new Object[] { ")", NEWLINE };
	private static final Object ELSE_CONDITION = new Object[] { "else {", NEWLINE, INC_INDENT };
	private static final Object ELSE_IF_CONDITION_PREFIX = new Object[] { "else if (" };
	private static final Object FIELD_PREFIX = INDENT;
	private static final Object FIELD_VALUE_ASSIGNMENT = " = ";
	private static final Object FIELD_SUFFIX = new Object[] { ";", NEWLINE };
	private static final Object FOR_CONDITION_PREFIX = new Object[] { INDENT, "for (" };
	private static final Object FOR_CONDITION_SUFFIX = new Object[] { ") ", BLOCK_HEADER };
	private static final Object IF_CONDITION_PREFIX = new Object[] { INDENT, "if (" };
	private static final Object IF_CONDITION_SUFFIX = new Object[] { ") ", BLOCK_HEADER };
	private static final Object INITIALIZER_HEADER = new Object[] { INDENT, BLOCK_HEADER };
	private static final Object INITIALIZER_FOOTER = new Object[] { DEC_INDENT, INDENT, "}", NEWLINE };
	private static final Object LINE_PREFIX = INDENT;
	private static final Object LINE_SUFFIX = NEWLINE;
	private static final Object LINE_SUFFIX_TERSE = "";
	private static final Object LINE_SUFFIX_TERMINATED = new Object[] { ";", NEWLINE };
	private static final Object LINE_SUFFIX_TERMINATED_TERSE = ";";
	private static final Object METHOD_DECLARATION_PREFIX = INDENT;
	private static final Object METHOD_DECLARATION_PREFIX_TERSE = "";
	private static final Object METHOD_DECLARATION_SUFFIX = BLOCK_HEADER;
	private static final Object METHOD_DECLARATION_SUFFIX_TERSE = "{ ";
	private static final Object METHOD_PARAMETER_PREFIX = "(";
	private static final Object METHOD_PARAMETER_SEPARATOR = ", ";
	private static final Object METHOD_PARAMETER_SUFFIX = ") ";
	private static final Object NESTED_BLOCK_FOOTER = new Object[] { INDENT, BLOCK_TERMINATOR };
	private static final Object NESTED_BLOCK_HEADER = BLOCK_HEADER;
	private static final Object PACKAGE_COMMENT_PREFIX = NOTHING;
	private static final Object PACKAGE_COMMENT_SUFFIX = new Object[] { INDENT };
	private static final Object THROWS_PREFIX = new Object[] { "throw new " };
	private static final Object THROWS_CONSTRUCTOR_ARGS_PREFIX = "(";
	private static final Object THROWS_CONSTRUCTOR_ARGS_SUFFIX = ")";
	private static final Object WHILE_CONDITION_PREFIX = new Object[] { INDENT, "while (" };
	private static final Object WHILE_CONDITION_SUFFIX = new Object[] { ") ", BLOCK_HEADER };

	@Override
	public Object getAnnotationPrefix() {
		return ANNOTATION_PREFIX;
	}

	@Override
	public Object getAnnotationSeparator() {
		return ANNOTATION_SEPARATOR;
	}

	@Override
	public Object getAnnotationSetPrefix() {
		return ANNOTATION_SET_PREFIX;
	}

	@Override
	public Object getAnnotationSetSuffix() {
		return ANNOTATION_SET_SUFFIX;
	}

	@Override
	public Object getAnnotationSetSuffixTerse() {
		return ANNOTATION_SET_SUFFIX_TERSE;
	}

	@Override
	public Object getAnnotationValuesPrefix() {
		return ANNOTATION_VALUES_PREFIX;
	}

	@Override
	public Object getAnnotationValueSeparator() {
		return ANNOTATION_VALUE_SEPARATOR;
	}

	@Override
	public Object getAnnotationValueAssignment() {
		return ANNOTATION_VALUE_ASSIGNMENT;
	}

	@Override
	public Object getAnnotationValuesSuffix() {
		return ANNOTATION_VALUES_SUFFIX;
	}

	@Override
	public Object getBlockTerminator() {
		return BLOCK_TERMINATOR;
	}

	@Override
	public Object getBlockTerminatorTerse() {
		return BLOCK_TERMINATOR_TERSE;
	}

	@Override
	public Object getBlockTerminatorTemporary() {
		return BLOCK_TERMINATOR_TEMPORARY;
	}

	@Override
	public Object getClassBlockHeader() {
		return CLASS_BLOCK_HEADER;
	}

	@Override
	public Object getClassBlockFooter() {
		return CLASS_BLOCK_FOOTER;
	}

	@Override
	public Object getClassCommentPrefix() {
		return CLASS_COMMENT_PREFIX;
	}

	@Override
	public Object getClassCommentSuffix() {
		return CLASS_COMMENT_SUFFIX;
	}

	@Override
	public Object getCommentPrivatePrefix() {
		return COMMENT_PRIVATE_PREFIX;
	}

	@Override
	public Object getCommentPrivateSeparator() {
		return COMMENT_PRIVATE_SEPARATOR;
	}

	@Override
	public Object getCommentPrivateSuffix() {
		return COMMENT_PRIVATE_SUFFIX;
	}

	@Override
	public Object getCommentPublicPrefix() {
		return COMMENT_PUBLIC_PREFIX;
	}

	@Override
	public Object getCommentPublicSeparator() {
		return COMMENT_PUBLIC_SEPARATOR;
	}

	@Override
	public Object getCommentPublicSuffix() {
		return COMMENT_PUBLIC_SUFFIX;
	}

	@Override
	public Object getCommentSingleLinePrefix() {
		return COMMENT_SINGLE_LINE_PREFIX;
	}

	@Override
	public Object getCommentSingleLineSuffix() {
		return COMMENT_SINGLE_LINE_SUFFIX;
	}

	@Override
	public Object getDoWhileBlockHeader() {
		return DO_WHILE_BLOCK_HEADER;
	}

	@Override
	public Object getDoWhileBlockTerminator() {
		return DO_WHILE_BLOCK_TERMINATOR;
	}

	@Override
	public Object getDoWhileTerminator() {
		return DO_WHILE_TERMINATOR;
	}

	@Override
	public Object getFieldPrefix() {
		return FIELD_PREFIX;
	}

	@Override
	public Object getFieldValueAssignment() {
		return FIELD_VALUE_ASSIGNMENT;
	}

	@Override
	public Object getFieldSuffix() {
		return FIELD_SUFFIX;
	}

	@Override
	public Object getForConditionPrefix() {
		return FOR_CONDITION_PREFIX;
	}

	@Override
	public Object getForConditionSuffix() {
		return FOR_CONDITION_SUFFIX;
	}

	@Override
	public Object getIfConditionPrefix() {
		return IF_CONDITION_PREFIX;
	}

	@Override
	public Object getElseIfConditionPrefix() {
		return ELSE_IF_CONDITION_PREFIX;
	}

	@Override
	public Object getElseCondition() {
		return ELSE_CONDITION;
	}

	@Override
	public Object getIfConditionSuffix() {
		return IF_CONDITION_SUFFIX;
	}

	@Override
	public Object getInitializerHeader() {
		return INITIALIZER_HEADER;
	}

	@Override
	public Object getInitializerFooter() {
		return INITIALIZER_FOOTER;
	}

	@Override
	public Object getLinePrefix() {
		return LINE_PREFIX;
	}

	@Override
	public Object getLineSuffix() {
		return LINE_SUFFIX;
	}

	@Override
	public Object getLineSuffixTerse() {
		return LINE_SUFFIX_TERSE;
	}

	@Override
	public Object getLineSuffixTerminated() {
		return LINE_SUFFIX_TERMINATED;
	}

	@Override
	public Object getLineSuffixTerminatedTerse() {
		return LINE_SUFFIX_TERMINATED_TERSE;
	}

	@Override
	public Object getMethodDeclarationPrefix() {
		return METHOD_DECLARATION_PREFIX;
	}

	@Override
	public Object getMethodDeclarationPrefixTerse() {
		return METHOD_DECLARATION_PREFIX_TERSE;
	}

	@Override
	public Object getMethodParameterPrefix() {
		return METHOD_PARAMETER_PREFIX;
	}

	@Override
	public Object getMethodParameterSeparator() {
		return METHOD_PARAMETER_SEPARATOR;
	}

	@Override
	public Object getMethodParameterSuffix() {
		return METHOD_PARAMETER_SUFFIX;
	}

	@Override
	public Object getMethodDeclarationSuffix() {
		return METHOD_DECLARATION_SUFFIX;
	}

	@Override
	public Object getMethodDeclarationSuffixTerse() {
		return METHOD_DECLARATION_SUFFIX_TERSE;
	}

	@Override
	public Object getNestedBlockFooter() {
		return NESTED_BLOCK_FOOTER;
	}

	@Override
	public Object getNestedBlockHeader() {
		return NESTED_BLOCK_HEADER;
	}

	@Override
	public Object getPackageCommentPrefix() {
		return PACKAGE_COMMENT_PREFIX;
	}

	@Override
	public Object getThrowsPrefix() {
		return THROWS_PREFIX;
	}

	@Override
	public Object getThrowsConstructorArgsPrefix() {
		return THROWS_CONSTRUCTOR_ARGS_PREFIX;
	}

	@Override
	public Object getThrowsConstructorArgsSuffix() {
		return THROWS_CONSTRUCTOR_ARGS_SUFFIX;
	}

	@Override
	public Object getPackageCommentSuffix() {
		return PACKAGE_COMMENT_SUFFIX;
	}

	@Override
	public Object getWhileConditionPrefix() {
		return WHILE_CONDITION_PREFIX;
	}

	@Override
	public Object getWhileConditionSuffix() {
		return WHILE_CONDITION_SUFFIX;
	}
}
