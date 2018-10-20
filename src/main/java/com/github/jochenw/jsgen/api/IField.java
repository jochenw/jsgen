package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IField extends IAnnotatable {
	@Nonnull String getName();
	@Nonnull JSGQName getType();
	boolean isFinal();
	@Nullable Object getValue();
}
