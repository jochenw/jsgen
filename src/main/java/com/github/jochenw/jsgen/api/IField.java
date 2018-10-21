package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IField extends IAnnotatable {
	@Nonnull String getName();
	@Nonnull JQName getType();
	boolean isFinal();
	@Nullable Object getValue();
}
