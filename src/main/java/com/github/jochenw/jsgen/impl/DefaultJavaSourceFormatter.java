package com.github.jochenw.jsgen.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.github.jochenw.jsgen.api.ICommentOwner;
import com.github.jochenw.jsgen.api.IField;
import com.github.jochenw.jsgen.api.IProtectable;
import com.github.jochenw.jsgen.api.IStaticable;
import com.github.jochenw.jsgen.api.IVolatilable;
import com.github.jochenw.jsgen.api.ClassBase;
import com.github.jochenw.jsgen.api.Comment;
import com.github.jochenw.jsgen.api.Constructor;
import com.github.jochenw.jsgen.api.DoWhileBlock;
import com.github.jochenw.jsgen.api.Field;
import com.github.jochenw.jsgen.api.ForBlock;
import com.github.jochenw.jsgen.api.IfBlock;
import com.github.jochenw.jsgen.api.IImportSorter;
import com.github.jochenw.jsgen.api.InnerClass;
import com.github.jochenw.jsgen.api.Method;
import com.github.jochenw.jsgen.api.NestedBlock;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.StaticInitializer;
import com.github.jochenw.jsgen.api.Subroutine;
import com.github.jochenw.jsgen.api.Throw;
import com.github.jochenw.jsgen.api.WhileBlock;
import com.github.jochenw.jsgen.api.LocalField;
import com.github.jochenw.jsgen.api.Block.Line;
import com.github.jochenw.jsgen.api.IAnnotatable.Annotation;
import com.github.jochenw.jsgen.api.IAnnotatable.AnnotationSet;
import com.github.jochenw.jsgen.api.Subroutine.Parameter;
import com.github.jochenw.jsgen.util.Objects;


/** Default implementation of {@link DefaultJavaSourceFormatter}. This
 * implementation uses a so-called {@link Format} object to determine the
 * layout.
 */
public class DefaultJavaSourceFormatter implements SourceSerializer {
	/** Internal data object, which is passed between method calls.
	 */
	public static class Data implements SerializationTarget {
		private final SerializationTarget target;
		private final Format format;
		private int numIndents;

		/** Creates a new instance with the given target object, and
		 * format.
		 * @param pTarget The target object
		 * @param pFormat The format object.
		 */
		public Data(SerializationTarget pTarget, Format pFormat) {
			target = pTarget;
			format = pFormat;
		}

		/**
		 * Increments the number of indented block levels. Basically,
		 * this method is being invoked when a new code block is
		 * opened.
		 */
		public void incIndent() {
			++numIndents;
		}

		/**
		 * Decrements the number of indented block levels. Basically,
		 * this method is being invoked when a code block ends.
		 */
		public void decIndent() {
			--numIndents;
		}

		/** Called to indent a single line. Basically, this uses the
		 * number of indented block levels, and the formatters
		 * {@link Format#getIndentString}, and writes the latter repeatedly
		 * to the target object, once for every indented block level.
		 */
		public void indent() {
			final String indentString = format.getIndentString();
			if (indentString != null) {
				for (int i = 0;  i < numIndents;  i++) {
					write(indentString);
				}
			}
		}

		/** Called to write the given object to the target.
		 */
		@Override
		public void write(Object pObject) {
			target.write(pObject);
		}

		/** Called to terminate a single line by writing the
		 * line terminator to the target.
		 */
		@Override
		public void newLine() {
			write(format.getLineTerminator());
		}

		@Override
		public void close() {
			target.close();
		}
	}

	private final Format format;
	private List<JQName> importedNames;
	private final List<JQName> scope = new ArrayList<JQName>();
	private IImportSorter importSorter;

	/** Creates a new instance with the given format.
	 * @param pFormat The source code layout being applied.
	 */
	public DefaultJavaSourceFormatter(Format pFormat) {
		format = pFormat;
	}

	/** Creates a new instance with the default format, the indent string
	 * "    " (four blanks), and the Linux/Unix line terminator ("\n").
	 */
	public DefaultJavaSourceFormatter() {
		this(new DefaultFormat("    ", "\n"));
	}

	/** Returns the list of types, which are being imported.
	 * @return The list of types, which are being imported.
	 */
	public List<JQName> getImportedNames() {
		return importedNames;
	}

	/** Sets the list of types, which are being imported.
	 * @param pImportedNames The list of types, which are being imported.
	 */
	public void setImportedNames(List<JQName> pImportedNames) {
		importedNames = pImportedNames;
	}

	/** Returns the current scope (a list of classes, which are
	 * currently being generated).
	 * @return The current scope.
	 */
	public List<JQName> getScope() {
		return scope;
	}

	/** Returns the import sorter.
	 * @return The import sorter.
	 */
	public IImportSorter getImportSorter() {
		return importSorter;
	}

	/** Sets the import sorter.
	 * @param pImportSorter The import sorter.
	 */
	public void setImportSorter(IImportSorter pImportSorter) {
		importSorter = pImportSorter;
	}

	/** Returns the sorted list of imports: A list of lists, one outer
	 * list for every category, and the inner list sorted by a comparator.
	 * The {@link IImportSorter import sorter} is used to build the
	 * category lists, and sort them.
	 * @return List of categories: Each category is a sorted list of imported
	 * names. (Empty categories are omitted.)
	 */
	protected List<List<JQName>> getSortedImports() {
		final List<List<JQName>> lists = new ArrayList<>();
		if (importedNames != null) {
			if (importSorter == null) {
				lists.add(importedNames);
			} else {
				for (JQName name : importedNames) {
					final int category = importSorter.getCategory(name);
					while(lists.size() < category+1) {
						lists.add(null);
					}
					List<JQName> names = lists.get(category);
					if (names == null) {
						names = new ArrayList<>();
						lists.set(category, names);
					}
					names.add(name);
				}
			}
		}
		for (List<JQName> list : lists) {
			if (list != null) {
				final Comparator<JQName> comparator;
				if (importSorter == null) {
					comparator = (n1, n2) -> n1.getQName().compareToIgnoreCase(n2.getQName());
				} else {
					comparator = (n1, n2) -> importSorter.compare(n1, n2);
				}
				Collections.sort(list, comparator);
			}
		}
		return lists;
	}

	/** Called to write the given Java source object to the given
	 * target.
	 */
	@Override
	public void write(Source pSource, SerializationTarget pTarget) {
		scope.clear();
		scope.add(pSource.getType());
		final Data data = new Data(pTarget, format);
		final JQName type = pSource.getType();
		write(pSource.getPackageComment(), data);
		final String packageName = type.getPackageName();
		if (packageName != null) {
			writeObject("package ", data);
			writeObject(packageName, data);
			writeObject(";", data);
			data.newLine();
			data.newLine();
		}
		final List<List<JQName>> importLists = getSortedImports();
		if (importLists != null  &&  !importLists.isEmpty()) {
			for (int i = 0;  i < importLists.size();  i++) {
				final List<JQName> importList = importLists.get(i);
				if (importList != null) {
					for (JQName n : importList) {
						writeObject("import ", data);
						writeObject(n.getQName(), data);
						writeObject(";", data);
						data.newLine();
					}
					data.newLine();
				}
			}
		}
		writeClass((ClassBase<?>) pSource, data);
	}

	/** Called to write the given Java class object to the given
	 * target.
	 */
	protected void writeClass(ClassBase<?> pClass, Data pTarget) {
		writeObject(format.getClassCommentPrefix(), pTarget);
		write(pClass.getComment(), pTarget);
		writeObject(format.getClassCommentSuffix(), pTarget);
		write(pClass.getAnnotations(), pTarget);
		write(pClass.getProtection(), pTarget);
		if (pClass instanceof InnerClass  &&  ((InnerClass) pClass).isStatic()) {
			writeObject("static ", pTarget);
		}
		if (pClass.isInterface()) {
			writeObject("interface ", pTarget);
		} else {
			writeObject("class ", pTarget);
		}
		writeObject(pClass.getType().getSimpleClassName(), pTarget);
		final List<JQName> extendedClasses = pClass.getExtendedClasses();
		if (!extendedClasses.isEmpty()) {
			for (int i = 0;  i < extendedClasses.size();  i++) {
				if (i == 0) {
					writeObject(" extends ", pTarget);
				} else {
					writeObject(", ", pTarget);
				}
				writeObject(extendedClasses.get(i), pTarget);
			}
		}
		final List<JQName> implementedInterfaces = pClass.getImplementedInterfaces();
		if (!implementedInterfaces.isEmpty()) {
			for (int i = 0;  i < implementedInterfaces.size();  i++) {
				if (i == 0) {
					writeObject(" implements", pTarget);
				} else {
					writeObject(", ", pTarget);
				}
				writeObject(implementedInterfaces.get(i), pTarget);
			}
		}
		writeObject(format.getClassBlockHeader(), pTarget);
		writeList(pClass.getContent(), pTarget);
		writeObject(format.getClassBlockFooter(), pTarget);
	}

	/** Called to write the given field objects declaration to the given
	 * target.
	 */
	protected void writeFieldDeclaration(IField<?> pField, Data pTarget) {
		if (pField instanceof ICommentOwner) {
			final Comment comment = ((ICommentOwner) pField).getComment();
			write(comment, pTarget);
		}
		write(pField.getAnnotations(), pTarget);
		writeObject(format.getFieldPrefix(), pTarget);
		if (pField instanceof IProtectable) {
			final IProtectable<?> protectable = (IProtectable<?>) pField;
			final IProtectable.Protection protection = protectable.getProtection();
			write(protection, pTarget);
		}
		if (pField instanceof IStaticable) {
			final IStaticable<?> staticable = (IStaticable<?>) pField;
			if (staticable.isStatic()) {
				writeObject("static ", pTarget);
			}
		}
		if (pField.isFinal()) {
			writeObject("final ", pTarget);
		}
		if (pField instanceof IVolatilable) {
			final IVolatilable<?> volatilable = (IVolatilable<?>) pField;
			if (volatilable.isVolatile()) {
				writeObject("volatile ", pTarget);
			}
		}
		writeObject(pField.getType(), pTarget);
		writeObject(" ", pTarget);
		writeObject(pField.getName(), pTarget);
		final Object value = pField.getValue();
		if (value != null) {
			writeObject(format.getFieldValueAssignment(), pTarget);
			writeObject(value, pTarget);
		}
		writeObject(format.getFieldSuffix(), pTarget);
	}

	protected void write(IProtectable.Protection pProtection, Data pTarget) {
		if (pProtection == null) {
			throw new NullPointerException();
		}
		switch (pProtection) {
		  case PUBLIC: writeObject("public ", pTarget); break;
		  case PROTECTED: writeObject("protected ", pTarget); break;
		  case PRIVATE: writeObject("private ", pTarget); break;
		  case PACKAGE: break;
		  default: throw new IllegalStateException("Invalid protection: " + pProtection);
		}
	}

	protected void write(AnnotationSet pAnnotations, Data pTarget) {
		if (!pAnnotations.isEmpty()) {
			writeObject(format.getAnnotationSetPrefix(), pTarget);
			boolean first = true;
			for (Annotation annotation : pAnnotations.getAnnotations()) {
				if (first) {
					first = false;
				} else {
					writeObject(format.getAnnotationSeparator(), pTarget);
				}
				write(annotation, pTarget);
			}
			writeObject(format.getAnnotationSetSuffix(), pTarget);
		}
	}

	protected void write(Annotation pAnnotation, Data pTarget) {
		writeObject(format.getAnnotationPrefix(), pTarget);
		writeObject(pAnnotation.getType(), pTarget);
		final Map<String,Object> map = pAnnotation.getAttributes();
		if (!map.isEmpty()) {
			writeObject(format.getAnnotationValuesPrefix(), pTarget);
			boolean first = true;
			for (Map.Entry<String,Object> en : map.entrySet()) {
				final String key = en.getKey();
				final Object value = en.getValue();
				if (first) {
					first = false;
				} else {
					writeObject(format.getAnnotationValueSeparator(), pTarget);
				}
				writeObject(key, pTarget);
				writeObject(format.getAnnotationValueAssignment(), pTarget);
				writeObject(value, pTarget);
			}
			writeObject(format.getAnnotationValuesSuffix(), pTarget);
		}
	}

	protected void writeMethod(Subroutine<?> pBlock, Data pTarget) {
		final Subroutine<?> subroutine = (Subroutine<?>) pBlock;
		write(subroutine.getComment(), pTarget);
		write(subroutine.getAnnotations(), pTarget);
		if (!subroutine.getAnnotations().isEmpty()) {
			pTarget.newLine();
		}
		writeObject(format.getMethodDeclarationPrefix(), pTarget);
		write(subroutine.getProtection(), pTarget);
		if (subroutine instanceof Method) {
			final Method method = (Method) subroutine;
			if (method.isAbstract()) {
				writeObject("abstract ", pTarget);
			}
			if (method.isStatic()) {
				writeObject("static ", pTarget);
			}
			if (method.isFinal()) {
				writeObject("final ", pTarget);
			}
			if (method.isSynchronized()) {
				writeObject("synchronized ", pTarget);
			}
			writeObject(method.getReturnType(), pTarget);
			writeObject(" ", pTarget);
		}
		if (subroutine instanceof Method) {
			final Method method = (Method) subroutine;
			writeObject(method.getName(), pTarget);
		} else if (subroutine instanceof Constructor) {
			writeObject(subroutine.getSourceClass().getType().getClassName(), pTarget);
		} else {
			throw new IllegalStateException("Invalid subroutine type: " + subroutine.getClass().getName());
		}
		writeObject(format.getMethodParameterPrefix(), pTarget);
		final List<Parameter> parameters = subroutine.getParameters();
		for (int i = 0;  i < parameters.size();  i++) {
			final Parameter param = parameters.get(i);
			if (i > 0) {
				writeObject(format.getMethodParameterSeparator(), pTarget);
			}
			write(param.getAnnotations(), pTarget);
			if (!param.getAnnotations().isEmpty()) {
				writeObject(" ", pTarget);
			}
			writeObject(param.getType(), pTarget);
			writeObject(" ", pTarget);
			writeObject(param.getName(), pTarget);
		}
		writeObject(format.getMethodParameterSuffix(), pTarget);
		final List<JQName> exceptions = subroutine.getExceptions();
		if (!exceptions.isEmpty()) {
			for (int i = 0;  i < exceptions.size();  i++) {
				if (i == 0) {
					writeObject("throws ", pTarget);
				} else {
					writeObject(", ", pTarget);
				}
				writeObject(exceptions.get(i), pTarget);
			}
			writeObject(" ", pTarget);
		}
		writeObject(format.getMethodDeclarationSuffix(), pTarget);
		writeList(pBlock.body().getContents(), pTarget);
		writeObject(format.getBlockTerminator(), pTarget);
	}

	protected void writeInitializer(StaticInitializer pInitializer, Data pTarget) {
		write(pInitializer.getComment(), pTarget);
		writeObject(format.getInitializerHeader(), pTarget);
		writeList(pInitializer.body().getContents(), pTarget);
		writeObject(format.getInitializerFooter(), pTarget);
	}

	protected void writeNestedBlock(NestedBlock pBlock, Data pTarget) {
		writeObject(format.getNestedBlockHeader(), pTarget);
		writeList(pBlock.getContents(), pTarget);
		writeObject(format.getNestedBlockFooter(), pTarget);
	}
	protected void writeIfBlock(IfBlock pIfBlock, Data pTarget) {
		writeObject(format.getIfConditionPrefix(), pTarget);
		writeObject(pIfBlock.getCondition(), pTarget);
		writeObject(format.getIfConditionSuffix(), pTarget);
		writeList(pIfBlock.getContents(), pTarget);
		writeObject(format.getBlockTerminator(), pTarget);
	}

	protected void writeWhileBlock(WhileBlock pWhileBlock, Data pTarget) {
		writeObject(format.getWhileConditionPrefix(), pTarget);
		writeObject(pWhileBlock.getCondition(), pTarget);
		writeObject(format.getWhileConditionSuffix(), pTarget);
		writeList(pWhileBlock.getContents(), pTarget);
		writeObject(format.getBlockTerminator(), pTarget);
	}

	protected void writeForBlock(ForBlock pForBlock, Data pTarget) {
		writeObject(format.getForConditionPrefix(), pTarget);
		writeObject(pForBlock.getCondition(), pTarget);
		writeObject(format.getForConditionSuffix(), pTarget);
		writeList(pForBlock.getContents(), pTarget);
		writeObject(format.getBlockTerminator(), pTarget);
	}

	protected void writeDoWhileBlock(DoWhileBlock pDoWhileBlock, Data pTarget) {
		writeObject(format.getDoWhileBlockHeader(), pTarget);
		writeList(pDoWhileBlock.getContents(), pTarget);
		writeObject(format.getDoWhileBlockTerminator(), pTarget);
		writeObject(pDoWhileBlock.getCondition(), pTarget);
		writeObject(format.getDoWhileTerminator(), pTarget);
	}

	protected void writeList(List<Object> pList, Data pTarget) {
		boolean previousObjectWasField = false;
		boolean first = true;
		for (Object o : pList) {
			if (o == null) {
				throw new NullPointerException("A list element must not be null.");
			}
			if (o instanceof Field) {
				if (!first  &&  !previousObjectWasField) {
					pTarget.newLine();
				}
				writeFieldDeclaration((Field) o, pTarget);
				previousObjectWasField = true;
			} else if (o instanceof LocalField) {
				if (!first  &&  !previousObjectWasField) {
					pTarget.newLine();
				}
				writeFieldDeclaration((LocalField) o, pTarget);
				previousObjectWasField = true;
			} else if (o instanceof Method) {
				writeMethod((Method) o, pTarget);
			} else if (o instanceof Constructor) {
				writeMethod((Constructor) o, pTarget);
			} else if (o instanceof InnerClass) {
				final ClassBase<?> clazz = (ClassBase<?>) o;
				scope.add(clazz.getType());
				writeClass(clazz, pTarget);
				scope.remove(scope.size()-1);
			} else if (o instanceof StaticInitializer) {
				writeInitializer((StaticInitializer) o, pTarget);
			} else if (o instanceof IfBlock) {
				writeIfBlock((IfBlock) o, pTarget);
			} else if (o instanceof ForBlock) {
				writeForBlock((ForBlock) o, pTarget);
			} else if (o instanceof WhileBlock) {
				writeWhileBlock((WhileBlock) o, pTarget);
			} else if (o instanceof DoWhileBlock) {
				writeDoWhileBlock((DoWhileBlock) o, pTarget);
			} else if (o instanceof Line) {
				writeLine((Line) o, pTarget);
			} else if (o instanceof Comment) {
				write((Comment) o, pTarget);
			} else if (o instanceof NestedBlock) {
				writeNestedBlock((NestedBlock) o, pTarget);
			} else {
				throw new IllegalStateException("Invalid object type: " + o.getClass().getName());
			}
		}
	}

	protected void writeLine(Line pLine, Data pTarget) {
		writeObject(format.getLinePrefix(), pTarget);
		writeObject(pLine.getElements(), pTarget);
		if (pLine.isTerminated()) {
			writeObject(format.getLineSuffixTerminated(), pTarget);
		} else {
			writeObject(format.getLineSuffix(), pTarget);
		}
	}

	protected void writeThrows(Throw pThrows, Data pTarget) {
		writeObject(format.getThrowsPrefix(), pTarget);
		writeObject(pThrows.getType(), pTarget);
		writeObject(format.getThrowsConstructorArgsPrefix(), pTarget);
		writeObject(pThrows.getConstructorArgs(), pTarget);
		writeObject(format.getThrowsConstructorArgsSuffix(), pTarget);
	}

	protected void writeObject(Object pValue, Data pTarget) {
		final Object v = Objects.requireNonNull(pValue, "Value");
		if (v == Format.INC_INDENT) {
			pTarget.incIndent();
		} else if (v == Format.DEC_INDENT) {
			pTarget.decIndent();
		} else if (v == Format.INDENT) {
			pTarget.indent();
		} else if (v == Format.NEWLINE) {
			pTarget.newLine();
		} else if (v instanceof Object[]) {
			final Object[] array = (Object[]) v;
			for (Object o : array) {
				writeObject(o, pTarget);
			}
		} else if (v instanceof Iterable) {
			@SuppressWarnings("unchecked")
			final Iterable<Object> iterable = (Iterable<Object>) v;
			for (Object o : iterable) {
				writeObject(o, pTarget);
			}
		} else if (v instanceof JQName) {
			final JQName name = (JQName) v;
			if (name.hasQualifiers()) {
				pTarget.write(name);
				pTarget.write("<");
				for (int i = 0;  i < name.getQualifiers().size();  i++) {
					if (i > 0) {
						pTarget.write(",");
					}
					pTarget.write(name.getQualifiers().get(i));
				}
				pTarget.write(">");
			} else {
				pTarget.write(name);
			}
			if (name.isArray()) {
				pTarget.write("[]");
			}
		} else if (v instanceof IField) {
			final IField<?> field = (IField<?>) v;
			writeObject(field.getName(), pTarget);
		} else if (v instanceof Throw) {
			writeThrows(((Throw) v), pTarget);
		} else if (v instanceof Class) {
			final Class<?> cl = (Class<?>) v;
			writeObject(JQName.valueOf(cl), pTarget);
		} else if (v instanceof String) {
			pTarget.write(v);
		} else if (v instanceof Number) {
			pTarget.write(v.toString());
		} else {
			throw new IllegalStateException("Invalid object type: " + v.getClass().getName());
		}
	}

	protected void write(Comment pComment, Data pTarget) {
		if (pComment != null) {
			final List<String> text = pComment.getText();
			switch (text.size()) {
			case 0:
				return;
			case 1:
				if (pComment.isPublic()) {
					// Intentionally no break, same handling than text.size() > 1
				} else {
					writeObject(format.getCommentSingleLinePrefix(), pTarget);
					writeObject(text.get(0), pTarget);
					writeObject(format.getCommentSingleLineSuffix(), pTarget);
					break;
				}
			default:
				final Object prefix, separator, suffix;
				if (pComment.isPublic()) {
					prefix = format.getCommentPublicPrefix();
					separator = format.getCommentPublicSeparator();
					suffix = format.getCommentPublicSuffix();
				} else {
					prefix = format.getCommentPrivatePrefix();
					separator = format.getCommentPrivateSeparator();
					suffix = format.getCommentPrivateSuffix();
				}
				for (int i = 0;  i < text.size();  i++) {
					if (i == 0) {
						writeObject(prefix, pTarget);
					} else {
						writeObject(separator, pTarget);
					}
					writeObject(text.get(i), pTarget);
				}
				writeObject(suffix, pTarget);
			}
		}
	}
}
