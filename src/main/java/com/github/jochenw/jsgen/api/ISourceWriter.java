package com.github.jochenw.jsgen.api;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;


public interface ISourceWriter {
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
	void write(@Nonnull Source pSource) throws IOException;
	void write(@Nonnull NamedResource pResource) throws IOException;
}
