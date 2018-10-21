package com.github.jochenw.jsgen.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.jochenw.jsgen.api.Block;
import com.github.jochenw.jsgen.api.CodeBlock;
import com.github.jochenw.jsgen.api.IProtectable;
import com.github.jochenw.jsgen.api.JSGClass;
import com.github.jochenw.jsgen.api.Comment;
import com.github.jochenw.jsgen.api.Constructor;
import com.github.jochenw.jsgen.api.DoWhileBlock;
import com.github.jochenw.jsgen.api.Field;
import com.github.jochenw.jsgen.api.ForBlock;
import com.github.jochenw.jsgen.api.IfBlock;
import com.github.jochenw.jsgen.api.IImportSorter;
import com.github.jochenw.jsgen.api.InnerClass;
import com.github.jochenw.jsgen.api.Method;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.StaticInitializer;
import com.github.jochenw.jsgen.api.Subroutine;
import com.github.jochenw.jsgen.api.WhileBlock;
import com.github.jochenw.jsgen.api.LocalField;
import com.github.jochenw.jsgen.api.IAnnotatable.Annotation;
import com.github.jochenw.jsgen.api.IAnnotatable.AnnotationSet;
import com.github.jochenw.jsgen.api.Subroutine.Parameter;
import com.github.jochenw.jsgen.util.Objects;


public class DefaultJavaSourceFormatterSave implements JSGSourceFormatter {
	private static class Data {
		private final JSGSourceTarget target;
		private int numIndents;

		Data(JSGSourceTarget pTarget) {
			target = pTarget;
		}

		public void write(Object pObject) {
			if (pObject instanceof JQName) {
				final JQName name = (JQName) pObject;
				if (name.hasQualifiers()) {
					target.write(name);
					target.write("<");
					for (int i = 0;  i < name.getQualifiers().size();  i++) {
						if (i > 0) {
							target.write(",");
						}
						write(name.getQualifiers().get(i));
					}
					target.write(">");
				} else {
					target.write(name);
				}
			} else {
				target.write(pObject);
			}
		}

		public void newLine() {
			target.newLine();
		}

		public void indent() {
			for (int i = 0;  i < numIndents;  i++) {
				target.write("    ");
			}
		}

		public void incIndent() {
			++numIndents;
		}

		public void decIndent() {
			--numIndents;
		}
	}

	@Override
	public void write(Source pSource, JSGSourceTarget pTarget) {
		final Data data = new Data(pTarget);
		data.write("package ");
		data.write(pSource.getType().getPackageName());
		data.write(";");
		data.newLine();
		data.newLine();
		final List<List<JQName>> importLists = getSortedImports();
		if (importLists != null  &&  !importLists.isEmpty()) {
			for (int i = 0;  i < importLists.size();  i++) {
				final List<JQName> importList = importLists.get(i);
				if (importList != null) {
					for (JQName n : importList) {
						data.write("import ");
						data.write(n.getQName());
						data.write(";");
						data.newLine();
					}
					data.newLine();
				}
			}
		}
		write((JSGClass<?>) pSource, data);
	}

	private List<JQName> importedNames;
	private IImportSorter importSorter;

	public List<JQName> getImportedNames() {
		return importedNames;
	}

	public void setImportedNames(List<JQName> importedNames) {
		this.importedNames = importedNames;
	}

	
	public IImportSorter getImportSorter() {
		return importSorter;
	}

	public void setImportSorter(IImportSorter importSorter) {
		this.importSorter = importSorter;
	}

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
	
	protected void write(IProtectable.Protection pProtection, Data pTarget) {
		if (pProtection == null) {
			throw new NullPointerException();
		}
		switch (pProtection) {
		  case PUBLIC: pTarget.write("public "); break;
		  case PROTECTED: pTarget.write("protected "); break;
		  case PRIVATE: pTarget.write("private "); break;
		  case PACKAGE: break;
		  default: throw new IllegalStateException("Invalid protection: " + pProtection);
		}
	}

	protected void write(AnnotationSet pAnnotations, Data pTarget) {
		if (!pAnnotations.isEmpty()) {
			pTarget.indent();
			boolean first = true;
			for (Annotation annotation : pAnnotations.getAnnotations()) {
				if (first) {
					first = false;
				} else {
					pTarget.write(" ");
				}
				write(annotation, pTarget);
			}
		}
	}

	protected void write(Annotation pAnnotation, Data pTarget) {
		pTarget.write("@");
		pTarget.write(pAnnotation.getType());
		final Map<String,Object> map = pAnnotation.getAttributes();
		if (!map.isEmpty()) {
			pTarget.write("(");
			boolean first = true;
			for (Map.Entry<String,Object> en : map.entrySet()) {
				final String key = en.getKey();
				final Object value = en.getValue();
				if (first) {
					first = false;
				} else {
					pTarget.write(", ");
				}
				pTarget.write(key);
				pTarget.write("=");
				write(value, pTarget);
			}
			pTarget.write(")");
		}
	}

	protected void write(CodeBlock<?> pBlock, Data pTarget) {
		if (pBlock instanceof Subroutine) {
			final Subroutine<?> subroutine = (Subroutine<?>) pBlock;
			write(subroutine.getComment(), pTarget);
			write(subroutine.getAnnotations(), pTarget);
			if (!subroutine.getAnnotations().isEmpty()) {
				pTarget.newLine();
			}
			pTarget.indent();
			write(subroutine.getProtection(), pTarget);
			if (subroutine instanceof Method) {
				final Method method = (Method) subroutine;
				if (method.isAbstract()) {
					pTarget.write("abstract ");
				}
				if (method.isStatic()) {
					pTarget.write("static ");
				}
				if (method.isFinal()) {
					pTarget.write("final ");
				}
				if (method.isSynchronized()) {
					pTarget.write("synchronized ");
				}
				pTarget.write(method.getReturnType());
				pTarget.write(" ");
			}
			if (subroutine instanceof Method) {
				final Method method = (Method) subroutine;
				pTarget.write(method.getName());
			} else if (subroutine instanceof Constructor) {
				pTarget.write(subroutine.getSourceClass().getType().getClassName());
			} else {
				throw new IllegalStateException("Invalid subroutine type: " + subroutine.getClass().getName());
			}
			pTarget.write("(");
			final List<Parameter> parameters = subroutine.getParameters();
			for (int i = 0;  i < parameters.size();  i++) {
				final Parameter param = parameters.get(i);
				if (i > 0) {
					pTarget.write(", ");
				}
				write(param.getAnnotations(), pTarget);
				if (!param.getAnnotations().isEmpty()) {
					pTarget.write(" ");
				}
				pTarget.write(param.getType());
				pTarget.write(" ");
				pTarget.write(param.getName());
			}
			pTarget.write(") ");
			final List<JQName> exceptions = subroutine.getExceptions();
			if (!exceptions.isEmpty()) {
				for (int i = 0;  i < exceptions.size();  i++) {
					if (i == 0) {
						pTarget.write("throws ");
					} else {
						pTarget.write(", ");
					}
					pTarget.write(exceptions.get(i));
				}
				pTarget.write(" ");
			}
			pTarget.write("{");
		} else {
			pTarget.indent();
			pTarget.write("{");
		}
		pTarget.incIndent();
		pTarget.newLine();
		write(pBlock.body(), pTarget, false);
		pTarget.decIndent();
		pTarget.indent();
		pTarget.write("}");
		pTarget.newLine();
	}

	protected void write(Block<?> pBlock, Data pTarget, boolean pIndent) {
		if (pIndent) {
			pTarget.indent();
		}
		final String blockPrefix, blockSuffix;
		final Object blockPrefixCondition, blockSuffixCondition;
		if (pBlock instanceof IfBlock) {
			blockPrefix = "if ";
			blockPrefixCondition = ((IfBlock) pBlock).getCondition();
			blockSuffix = null;
			blockSuffixCondition = null;
		} else if (pBlock instanceof WhileBlock) {
			blockPrefix = "while ";
			blockPrefixCondition = ((WhileBlock) pBlock).getCondition();
			blockSuffix = null;
			blockSuffixCondition = null;
		} else if (pBlock instanceof ForBlock) {
			blockPrefix = "for ";
			blockPrefixCondition = ((ForBlock) pBlock).getCondition();
			blockSuffix = null;
			blockSuffixCondition = null;
		} else if (pBlock instanceof DoWhileBlock) {
			blockPrefix = "do ";
			blockPrefixCondition = null;
			blockSuffix = "while ";
			blockSuffixCondition = ((DoWhileBlock) pBlock).getCondition();
		} else {
			blockPrefix = blockSuffix = null;
			blockPrefixCondition = blockSuffixCondition = null;
		}
		if (blockPrefix != null) {
			pTarget.write(blockPrefix);
			pTarget.write("(");
			write(blockPrefixCondition, pTarget);
			pTarget.write(") {");
		} else {
			if (pIndent) {
				pTarget.write("{");
			}
		}
		if (pIndent) {
			pTarget.incIndent();
			pTarget.newLine();
		}
		final List<Object> content = pBlock.getContents();
		content.forEach((o) -> {
			if (o instanceof Object[]) {
				writeLine(o, pTarget);
			} else if (o instanceof Iterable) {
				writeLine(o, pTarget);
			} else if (o instanceof CodeBlock) {
				final CodeBlock<?> codeBlock = (CodeBlock<?>) o;
				write(codeBlock, pTarget);
			} else if (o instanceof Block) {
				final Block<?> block = (Block<?>) o;
				write(block, pTarget, true);
			} else if (o instanceof LocalField) {
				final LocalField f = (LocalField) o;
				write(f, pTarget);
			}
		});
		if (blockSuffix == null) {
			if (pIndent) {
				pTarget.decIndent();
				pTarget.indent();
				pTarget.write("}");
				pTarget.newLine();
			}
		}
		if (blockSuffix != null) {
			pTarget.write(blockSuffix);
			pTarget.write("(");
			write(blockSuffixCondition, pTarget);
			pTarget.write(");");
		}
	}
	
	protected void writeLine(Object pObject, Data pTarget) {
		pTarget.indent();
		write(pObject, pTarget);
		pTarget.newLine();
	}

	protected void write(Object pObject, Data pTarget) {
		Objects.requireNonNull(pObject, "Object");
		if (pObject.getClass().isArray()) {
			final Object[] array = (Object[]) pObject;
			for (int i = 0;  i < array.length;  i++) {
				write(array[i], pTarget);
			}
		} else if (pObject instanceof Iterable) {
			@SuppressWarnings("unchecked")
			final Iterable<Object> iterable = (Iterable<Object>) pObject;
			for (Object o : iterable) {
				write(o, pTarget);
			}
		} else if (pObject instanceof JQName) {
			pTarget.write(pObject);
		} else if (pObject instanceof Class<?>) {
			final Class<?> cl = (Class<?>) pObject;
			pTarget.write(JQName.valueOf(cl));
		} else if (pObject instanceof LocalField) {
			pTarget.write(((LocalField) pObject).getName());
		} else if (pObject instanceof Field) {
			pTarget.write(((Field) pObject).getName());
		} else if (pObject instanceof Parameter) {
			pTarget.write(((Parameter) pObject).getName());
		} else if (pObject instanceof CodeBlock) {
			final CodeBlock<?> cb = (CodeBlock<?>) pObject;
			write(cb, pTarget);
		} else {
			pTarget.write(pObject.toString());
		}
	}
	
	protected void write(Field pField, Data pTarget) {
		write(pField.getComment(), pTarget);
		write(pField.getAnnotations(), pTarget);
		if (!pField.getAnnotations().isEmpty()) {
			pTarget.newLine();
		}
		pTarget.indent();
		write(pField.getProtection(), pTarget);
		if (pField.isStatic()) {
			pTarget.write("static ");
		}
		if (pField.isFinal()) {
			pTarget.write("final ");
		}
		if (pField.isVolatile()) {
			pTarget.write("volatile ");
		}
		pTarget.write(pField.getType());
		pTarget.write(" ");
		pTarget.write(pField.getName());
		final Object value = pField.getValue();
		if (value != null) {
			pTarget.write(" = ");
			write(value, pTarget);
		}
		pTarget.write(";");
		pTarget.newLine();
	}

	protected void write(LocalField pField, Data pTarget) {
		write(pField.getComment(), pTarget);
		pTarget.indent();
		write(pField.getAnnotations(), pTarget);
		if (!pField.getAnnotations().isEmpty()) {
			pTarget.newLine();
		}
		if (pField.isFinal()) {
			pTarget.write("final ");
		}
		pTarget.write(pField.getType());
		pTarget.write(" ");
		pTarget.write(pField.getName());
		final Object value = pField.getValue();
		if (value != null) {
			pTarget.write(" = ");
			write(value, pTarget);
		}
		pTarget.write(";");
		pTarget.newLine();
	}

	protected void write(@Nullable Comment pComment, Data pTarget) {
		if (pComment != null) {
			final boolean isPublic = pComment.isPublic();
			final List<String> text = pComment.getText();
			switch (text.size()) {
			case 0:
				return;
			case 1:
				if (isPublic) {
					// Intentionally no break, same handling than text.size() > 1
				} else {
					pTarget.indent();
					pTarget.write("// ");
					pTarget.write(text.get(0));
					pTarget.newLine();
					break;
				}
			default:
				for (int i = 0;  i < text.size();  i++) {
					pTarget.indent();
					if (i == 0) {
						if (isPublic) {
							pTarget.write("/** ");
						} else {
							pTarget.write("/* ");
						}
					} else {
						if (isPublic) {
							pTarget.write("  * ");
						} else {
							pTarget.write(" * ");
						}
					}
					pTarget.write(text.get(i));
					pTarget.newLine();
				}
				pTarget.indent();
				if (isPublic) {
					pTarget.write("  */");
				} else {
					pTarget.write(" */");
				}
				pTarget.newLine();
			}
		}
	}

	protected void write(JSGClass<?> pClass, Data pTarget) {
		write(pClass.getComment(), pTarget);
		pTarget.indent();
		write(pClass.getAnnotations(), pTarget);
		if (!pClass.getAnnotations().isEmpty()) {
			pTarget.newLine();
		}
		write(pClass.getProtection(), pTarget);
		if (pClass instanceof InnerClass  &&  ((InnerClass) pClass).isStatic()) {
			pTarget.write("static ");
		}
		if (pClass.isInterface()) {
			pTarget.write("interface ");
		} else {
			pTarget.write("class");
		}
		pTarget.write(" ");
		pTarget.write(pClass.getType().getSimpleClassName());
		final List<JQName> extendedClasses = pClass.getExtendedClasses();
		if (!extendedClasses.isEmpty()) {
			for (int i = 0;  i < extendedClasses.size();  i++) {
				if (i == 0) {
					pTarget.write(" extends ");
				} else {
					pTarget.write(", ");
				}
				pTarget.write(extendedClasses.get(i));
			}
		}
		final List<JQName> implementedInterfaces = pClass.getImplementedInterfaces();
		if (!implementedInterfaces.isEmpty()) {
			for (int i = 0;  i < implementedInterfaces.size();  i++) {
				if (i == 0) {
					pTarget.write(" implements");
				} else {
					pTarget.write(", ");
				}
				pTarget.write(implementedInterfaces.get(i));
			}
		}
		pTarget.write(" {");
		pTarget.incIndent();
		pTarget.newLine();
		pClass.getContent().forEach((o) -> {
			if (o instanceof Method) {
				write((Method) o, pTarget);
			} else if (o instanceof Constructor) {
				write((Constructor) o, pTarget);
			} else if (o instanceof Field) {
				write((Field) o, pTarget);
			} else if (o instanceof InnerClass) {
				write((InnerClass) o, pTarget);
			} else if (o instanceof StaticInitializer) {
				write((StaticInitializer) o, pTarget);
			} else {
				throw new IllegalStateException("Invalid object type: " + o.getClass().getName());
			}
		});
		pTarget.decIndent();
		pTarget.write("}");
		pTarget.newLine();
	}
}
