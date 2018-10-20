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
		JSGLocation getName();
		void writeTo(OutputStream pOut) throws IOException;
	}
	private Map<JSGLocation,Object> resources = new HashMap<>();

	/**
	 * Returns the previously created {@link JSGSource source builder} with the
	 * given name.
	 * @param pName Name of the {@link JSGSource source builder}, which is being
	 * queried.
	 * @return Previously created {@link JSGSource source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link JSGSource source builder}.
	 */
	@Nullable public JSGSource getSource(@Nonnull JSGQName pName) {
		final Object source = resources.get(pName);
		if (source == null) {
			return null;
		} else if (source instanceof JSGSource) {
			return (JSGSource) source;
		} else {
			throw new IllegalStateException("Expected JSGSourceBuilder, got " + source.getClass().getName());
		}
	}

	/**
	 * Returns the previously created {@link JSGSource source builder} with the
	 * given type.
	 * @param pType Type of the {@link JSGSource source builder}, which is being
	 * queried.
	 * @return Previously created {@link JSGSource source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link JSGSource source builder}.
	 */
	@Nullable public JSGSource getSource(@Nonnull Class<?> pType) {
		return getSource(JSGQName.valueOf(pType));
	}

	/**
	 * Returns the previously created {@link JSGSource source builder} with the
	 * given type.
	 * @param pType Type of the {@link JSGSource source builder}, which is being
	 * queried.
	 * @return Previously created {@link JSGSource source builder}, if available,
	 *   or null.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has been registered with the given name,
	 *   but the object is not a {@link JSGSource source builder}.
	 */
	@Nullable public JSGSource getSource(@Nonnull String pType) {
		return getSource(JSGQName.valueOf(pType));
	}

	/**
	 * Creates a new {@link JSGSource source builder} with the given name, and package
	 * protection.
	 * @param pName Name of the {@link JSGSource source builder}, which is being
	 *   created.
	 * @return The new {@link JSGSource source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public JSGSource newSource(@Nonnull JSGQName pName) {
		return newSource(pName, Protection.PACKAGE);
	}

	/**
	 * Creates a new {@link JSGSource source builder} with the given name.
	 * @param pName Name of the {@link JSGSource source builder}, which is being
	 *   created.
	 * @param pProtection The new classes protection (public, protected, default, or private).
	 * @return The new {@link JSGSource source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public JSGSource newSource(@Nonnull JSGQName pName, Protection pProtection) {
		final Object source = resources.get(pName);
		if (source == null) {
			final JSGSource sb = new JSGSource(pName).protection(pProtection);
			resources.put(pName, sb);
			return sb;
		} else {
			throw new IllegalStateException("Source already exists: " + pName);
		}
	}

	/**
	 * Creates a new {@link JSGSource source builder} with the given name, and package protection.
	 * @param pName Name of the {@link JSGSource source builder}, which is being
	 *   created.
	 * @return The new {@link JSGSource source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public JSGSource newSource(@Nonnull Class<?> pType) {
		return newSource(JSGQName.valueOf(pType)).makePackageProtected();
	}

	/**
	 * Creates a new {@link JSGSource source builder} with the given name.
	 * @param pName Name of the {@link JSGSource source builder}, which is being
	 *   created.
	 * @return The new {@link JSGSource source builder}.
	 * @throws NullPointerException The name is null.
	 * @throws IllegalStateException An object has already been registered with the given name.
	 */
	@Nonnull public JSGSource newSource(@Nonnull String pType) {
		return newSource(JSGQName.valueOf(pType)).makePackageProtected();
	}

	public void forEach(BiConsumer<JSGLocation,Object> pConsumer) {
		resources.forEach(pConsumer);
	}
}
