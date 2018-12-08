package com.github.jochenw.jsgen.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.api.IProtectable.Protection;


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
}
