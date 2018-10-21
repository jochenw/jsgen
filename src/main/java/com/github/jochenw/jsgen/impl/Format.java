package com.github.jochenw.jsgen.impl;

import com.github.jochenw.jsgen.api.Source;

public abstract class Format {
	public static final Object NEWLINE= new Object();
	public static final Object INC_INDENT= new Object();
	public static final Object DEC_INDENT= new Object();
	public static final Object INDENT = new Object();
	public static final Object NOTHING = new Object[0];

	private final String indentString, lineTerminator;

	protected Format(String pIndentString, String pLineTerminator) {
		indentString = pIndentString;
		lineTerminator = pLineTerminator;
	}

	public String getIndentString() {
		return indentString;
	}

	public String getLineTerminator() {
		return lineTerminator;
	}

	public abstract Object getAnnotationPrefix();
	public abstract Object getAnnotationSeparator();
	public abstract Object getAnnotationSetPrefix();
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
	public abstract Object getPackageCommentPrefix();
	public abstract Object getPackageCommentSuffix();
	public abstract Object getThrowsPrefix();
	public abstract Object getThrowsConstructorArgsPrefix();
	public abstract Object getThrowsConstructorArgsSuffix();
	public abstract Object getWhileConditionPrefix();
	public abstract Object getWhileConditionSuffix();






}
