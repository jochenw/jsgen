package com.github.jochenw.jsgen.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JSGImportSorter;
import com.github.jochenw.jsgen.api.JSGLocation;
import com.github.jochenw.jsgen.api.JSGQName;
import com.github.jochenw.jsgen.api.JSGSource;
import com.github.jochenw.jsgen.api.JSGSourceWriter;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.util.Objects;


public abstract class AbstractSourceWriter implements JSGSourceWriter {
	public static final JSGImportSorter DEFAULT_IMPORT_SORTER = new DefaultImportSorter();
	public static final JSGSourceFormatter DEFAULT_FORMATTER = new DefaultJavaSourceFormatter(new DefaultFormat("    ", "\n"));
	public static final JSGSourceFormatter MAVEN_FORMATTER = new DefaultJavaSourceFormatter(new MavenFormat("    ", "\n"));
	private JSGImportSorter importSorter = DEFAULT_IMPORT_SORTER;
	private @Nonnull JSGSourceFormatter formatter = DEFAULT_FORMATTER;

	@Override
	public void write(JSGSource pSource) throws IOException {
		write(asNamedResource(pSource));
	}

	@Override
	public void write(NamedResource pResource) throws IOException {
		try (OutputStream os = open(pResource)) {
			pResource.writeTo(os);
		}
	}

	protected abstract OutputStream open(NamedResource pResource) throws IOException;

	protected NamedResource asNamedResource(JSGSource pSource) throws IOException {
		final ImportCollectingTarget ict = new ImportCollectingTarget();
		ict.setImportingClass(pSource.getType());
		formatter.write(pSource, ict);
		final List<JSGQName> importedNames = ict.getImportedNames();
		final StringWriter sw = new StringWriter();
		DefaultJSGSourceTarget target = new DefaultJSGSourceTarget(sw);
		final JSGSourceTarget trgt = new JSGSourceTarget() {
			@Override
			public void write(Object pObject) {
				if (pObject instanceof JSGQName) {
					final JSGQName name = (JSGQName) pObject;
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
		}
		formatter.write(pSource, trgt);
		final String fileName = pSource.getType().getPackageName().replace('.', '/') + '/' + pSource.getType().getClassName() + ".java";
		final JSGLocation location = new JSGLocation() {
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
			public JSGLocation getName() {
				return location;
			}

			@Override
			public boolean isJavaSource() {
				return true;
			}

			@Override
			public boolean isResourceFile() {
				return false;
			}
		};
		return namedResource;
	}

	protected String toString(JSGQName pSourceName, List<JSGQName> pImportedNames, JSGQName pName) {
		JSGQName name = pName;
		while (name.isInnerClass()) {
			JSGQName outerName = Objects.requireNonNull(name.getOuterClass(), "Outer Class");
			if (outerName.equals(pSourceName)  ||  pImportedNames.contains(outerName)) {
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
	
	public JSGImportSorter getImportSorter() {
		return importSorter;
	}

	public void setImportSorter(JSGImportSorter pImportSorter) {
		this.importSorter = pImportSorter;
	}

	public JSGSourceFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(@Nonnull JSGSourceFormatter pFormatter) {
		formatter = pFormatter;
	}
}
