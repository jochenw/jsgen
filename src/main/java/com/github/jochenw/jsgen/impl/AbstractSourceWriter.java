package com.github.jochenw.jsgen.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.IImportSorter;
import com.github.jochenw.jsgen.api.ILocation;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.ISourceWriter;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.util.Objects;


/** Abstract base class for implementations of {@link ISourceWriter}.
 */
public abstract class AbstractSourceWriter implements ISourceWriter {
	/** The default value for {@link #getImportSorter}: An instance of
	 * {@link DefaultImportSorter}.
	 */
	public static final IImportSorter DEFAULT_IMPORT_SORTER = new DefaultImportSorter();
	/** The default value for {@link #getFormatter()}: An instance of
	 * {@link DefaultFormat}.
	 */
	public static final SourceSerializer DEFAULT_FORMATTER = new DefaultJavaSourceFormatter(new DefaultFormat("    ", "\n"));
	/** An alternative value for {@link #setFormatter(SourceSerializer)}: An instance of
	 * {@link MavenFormat}.
	 */
	public static final SourceSerializer MAVEN_FORMATTER = new DefaultJavaSourceFormatter(new MavenFormat("    ", "\n"));
	private IImportSorter importSorter = DEFAULT_IMPORT_SORTER;
	private @Nonnull SourceSerializer formatter = DEFAULT_FORMATTER;
	private Supplier<List<JQName>> scopeProvider;

	/** Returns the scope provider: It supplies a list of names, which are in the current scope.
	 * For example, if we would be currently generating the inner class {@link java.util.Map.Entry},
	 * then there's no point in importing "com.foo.app.Map", because {@link java.util.Map} is in
	 * the current scope.
	 * @return The scope provider.
	 */
	public Supplier<List<JQName>> getScopeProvider() {
		return scopeProvider;
	}

	/** Sets the scope provider: It supplies a list of names, which are in the current scope.
	 * For example, if we would be currently generating the inner class {@link java.util.Map.Entry},
	 * then there's no point in importing "com.foo.app.Map", because {@link java.util.Map} is in
	 * the current scope.
	 * @param pScopeProvider The scope provider.
	 */
	public void setScopeProvider(Supplier<List<JQName>> pScopeProvider) {
		scopeProvider = pScopeProvider;
	}

	@Override
	public void write(Source pSource) throws IOException {
		write(asNamedResource(pSource));
	}

	@Override
	public void write(NamedResource pResource) throws IOException {
		try (OutputStream os = open(pResource)) {
			pResource.writeTo(os);
		}
	}

	protected abstract OutputStream open(NamedResource pResource) throws IOException;

	protected NamedResource asNamedResource(Source pSource) throws IOException {
		final ImportCollectingTarget ict = new ImportCollectingTarget();
		ict.setImportingClass(pSource.getType());
		formatter.write(pSource, ict);
		final List<JQName> importedNames = ict.getImportedNames();
		final StringWriter sw = new StringWriter();
		DefaultSerializationTarget target = new DefaultSerializationTarget(sw);
		final SerializationTarget trgt = new SerializationTarget() {
			@Override
			public void write(Object pObject) {
				if (pObject instanceof JQName) {
					final JQName name = (JQName) pObject;
					if (name.isPseudoClass()) {
						target.write(name.getSimpleClassName());
					} else {
						target.write(AbstractSourceWriter.this.toString(pSource.getType(), importedNames, name));
					}
				} else {
					target.write(pObject);
				}
			}
			
			@Override
			public void newLine() {
				target.newLine();
			}
			
			@Override
			public void close() {
				target.close();
			}
		};
		if (formatter instanceof DefaultJavaSourceFormatter) {
			final DefaultJavaSourceFormatter djsf = (DefaultJavaSourceFormatter) formatter;
			djsf.setImportedNames(importedNames);
			djsf.setImportSorter(importSorter);
			scopeProvider = () -> djsf.getScope();
		}
		formatter.write(pSource, trgt);
		final String fileName = pSource.getType().getPackageName().replace('.', '/') + '/' + pSource.getType().getClassName() + ".java";
		final ILocation location = new ILocation() {
			@Override
			public String getQName() {
				return fileName;
			}
		};
		final NamedResource namedResource = new NamedResource() {
			@Override
			public void writeTo(OutputStream pOut) throws IOException {
				pOut.write(sw.toString().getBytes(StandardCharsets.UTF_8));
			}

			@Override
			public ILocation getName() {
				return location;
			}

			@Override
			public boolean isJavaSource() {
				return true;
			}
		};
		return namedResource;
	}

	protected String toString(JQName pSourceName, List<JQName> pImportedNames, JQName pName) {
		JQName name = pName;
		while (name.isInnerClass()) {
			JQName outerName = Objects.requireNonNull(name.getOuterClass(), "Outer Class");
			if (isScopeRestricted(outerName)  ||  pImportedNames.contains(outerName)) {
				return outerName.getSimpleClassName() + "." + pName.getQName().substring(outerName.getQName().length()+1);
			}
			name = outerName;
		}
		if (pImportedNames.contains(pName)  ||  pSourceName.equals(pName)) {
			return pName.getSimpleClassName();
		} else {
			return pName.getQName();
		}
	}

	protected boolean isScopeRestricted(JQName pName) {
		if (scopeProvider != null) {
			final List<JQName> scope = scopeProvider.get();
			if (scope != null) {
				for (JQName name : scope) {
					if (name.getSimpleClassName().equals(pName.getClassName())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns the import sorter.
	 * @return The import sorter.
	 */
	public IImportSorter getImportSorter() {
		return importSorter;
	}

	/**
	 * Sets the import sorter.
	 * @param pImportSorter The import sorter.
	 */
	public void setImportSorter(IImportSorter pImportSorter) {
		this.importSorter = pImportSorter;
	}

	/**
	 * Returns the formatter.
	 * @return The formatter.
	 */
	public SourceSerializer getFormatter() {
		return formatter;
	}

	/**
	 * Sets the formatter.
	 * @param pFormatter The formatter.
	 */
	public void setFormatter(@Nonnull SourceSerializer pFormatter) {
		formatter = pFormatter;
	}
}
