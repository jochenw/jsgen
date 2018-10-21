package com.github.jochenw.jsgen.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.api.IProtectable.Protection;


public class JSGFactory {
	public interface NamedResource {
		boolean isJavaSource();
		boolean isResourceFile();
		ILocation getName();
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
	 * @param pName Name of the {@link Source source builder}, which is being
	 *   created.
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull Class<?> pType) {
		return newSource(JQName.valueOf(pType)).makePackageProtected();
	}

	/**
	 * Creates a new {@link Source source builder} with the given name.
	 * @param pName Name of the {@link Source source builder}, which is being
	 *   created.
	 * @return The new {@link Source source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public Source newSource(@Nonnull String pType) {
		return newSource(JQName.valueOf(pType)).makePackageProtected();
	}

	public void forEach(BiConsumer<ILocation,Object> pConsumer) {
		resources.forEach(pConsumer);
	}

	public static JSGFactory build() {
		return new JSGFactory();
	}
}
