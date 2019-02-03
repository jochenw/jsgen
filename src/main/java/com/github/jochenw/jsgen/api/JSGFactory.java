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
package com.github.jochenw.jsgen.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.api.IProtectable.Protection;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.DefaultJavaSourceFormatter;
import com.github.jochenw.jsgen.impl.FileJavaSourceWriter;
import com.github.jochenw.jsgen.impl.Format;


/** A factory for creating {@link Source} objects. The factory will
 * collect the created objects, allowing to persist them easily in
 * one go.
 */
public class JSGFactory {
	/**
	 * Interface of an arbitrary source file, which is being generated.
	 * These include Java source files, but may as well be resource
	 * files, for example a property file, or an XML file.
	 */
	public interface NamedResource {
		/** Returns, whether this source file is a Java source file, or not.
		 * @return True, if this is a Java source file, otherwise false.
		 */
		boolean isJavaSource();
		/** Returns the source files location in the filesystem, relative
		 * to the base directory of the generated sources.
		 * @return The source files location in the filesystem, relative
		 * to the base directory of the generated sources.
		 */
		ILocation getName();
		/** Called to persist the source file by writing it to the given
		 * output stream.
		 * @param pOut An output stream, which has been opened to receive
		 * the source files contents.
		 * @throws IOException Writing to the external storage failed with an I/O error.
		 */
		void writeTo(OutputStream pOut) throws IOException;
	}
	private Map<ILocation,Object> resources = new HashMap<>();

	private JSGFactory() {
	}

	/**
	 * Returns the previously created {@link Source source builder} with the
	 * given name.
	 * @param pName Name of the {@link Source source builder}, which is being
	 * queried.
	 * @return Previously created {@link Source source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link Source source builder}.
	 */
	@Nullable public Source getSource(@Nonnull JQName pName) {
		final Object source = resources.get(pName);
		if (source == null) {
			return null;
		} else if (source instanceof Source) {
			return (Source) source;
		} else {
			throw new IllegalStateException("Expected JSGSourceBuilder, got " + source.getClass().getName());
		}
	}

	/**
	 * Returns the previously created {@link Source source builder} with the
	 * given type.
	 * @param pType Type of the {@link Source source builder}, which is being
	 * queried.
	 * @return Previously created {@link Source source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link Source source builder}.
	 */
	@Nullable public Source getSource(@Nonnull Class<?> pType) {
		return getSource(JQName.valueOf(pType));
	}

	/**
	 * Returns the previously created {@link Source source builder} with the
	 * given type.
	 * @param pType Type of the {@link Source source builder}, which is being
	 * queried.
	 * @return Previously created {@link Source source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link Source source builder}.
	 */
	@Nullable public Source getSource(@Nonnull String pType) {
		return getSource(JQName.valueOf(pType));
	}

	/**
	 * Creates a new {@link Source source builder} with the given name, and package
	 * protection.
	 * @param pName Name of the {@link Source source builder}, which is being
	 *   created.
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull JQName pName) {
		return newSource(pName, Protection.PACKAGE);
	}

	/**
	 * Creates a new {@link Source source builder} with the given name.
	 * @param pName Name of the {@link Source source builder}, which is being
	 *   created.
	 * @param pProtection The new classes protection (public, protected, default, or private).
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull JQName pName, Protection pProtection) {
		final Object source = resources.get(pName);
		if (source == null) {
			final Source sb = new Source(pName).protection(pProtection);
			resources.put(pName, sb);
			return sb;
		} else {
			throw new IllegalStateException("Source already exists: " + pName);
		}
	}

	/**
	 * Creates a new {@link Source source builder} with the given name, and package protection.
	 * @param pType Name of the {@link Source source builder}, which is being
	 *   created.
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull Class<?> pType) {
		return newSource(JQName.valueOf(pType)).makePackagePrivate();
	}

	/**
	 * Creates a new {@link Source source builder} with the given name.
	 * @param pType Name of the {@link Source source builder}, which is being
	 *   created.
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull String pType) {
		return newSource(JQName.valueOf(pType)).makePackagePrivate();
	}

	/** Performs the given action for each resource object, that the factory has created.
	 * @param pConsumer The action to perform for each resource object.
	 */
	public void forEach(BiConsumer<ILocation,Object> pConsumer) {
		resources.forEach(pConsumer);
	}

	/** Creates a new instance.
	 * @return A new factory instance
	 */
	public static JSGFactory create() {
		return new JSGFactory();
	}

	/** Writes the factories source objects as files to the given directory,
	 * using the {@link AbstractSourceWriter#DEFAULT_FORMAT default source code layout}.
	 * Equivalent to {@code write(pTargetDir, AbstractSourceWriter.DEFAULT_FORMAT)}.
	 * @param pTargetDir The target directory, where to write source files
	 *   to. The directory itself, and subdirectories, are being created,
	 *   if necessary, as required by the package structure.
	 */
	public void write(File pTargetDir) {
		write(pTargetDir, AbstractSourceWriter.DEFAULT_FORMAT); 
	}

	/** Writes the factories source objects as files to the given directory,
	 * using the given source code format.
	 * @param pTargetDir The target directory, where to write source files
	 *   to. The directory itself, and subdirectories, are being created,
	 *   if necessary, as required by the package structure.
	 * @param pFormat The source code layout to use.
	 */
	public void write(File pTargetDir, Format pFormat) {
		final FileJavaSourceWriter fjsw = new FileJavaSourceWriter(pTargetDir);
		fjsw.setFormatter(new DefaultJavaSourceFormatter(pFormat));
		fjsw.write(this);
	}
}
