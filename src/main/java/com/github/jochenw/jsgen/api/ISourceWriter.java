package com.github.jochenw.jsgen.api;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;


/** Interface of an object, which persists the Java source representations.
 */
public interface ISourceWriter {
	/** Persists all objects, which have been created by the factory.
	 * @param pFactory The factory object being persisted.
	 */
	public default void write(@Nonnull JSGFactory pFactory) {
		pFactory.forEach((l,o) -> {
			try {
				if (o instanceof Source) {
					write((Source) o);
				} else if (o instanceof NamedResource) {
					write((NamedResource) o);
				} else {
					throw new IllegalStateException("Invalid object type: " + o.getClass().getName());
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}
	/** Persists the given Java source object.
	 * @param pSource The source object being persisted.
	 * @throws IOException An I/O error occurred while accessing external storage.
	 */
	void write(@Nonnull Source pSource) throws IOException;
	/** Persists the given Java resource file.
	 * @param pResource The source object being persisted.
	 * @throws IOException An I/O error occurred while accessing external storage.
	 */
	void write(@Nonnull NamedResource pResource) throws IOException;
}
