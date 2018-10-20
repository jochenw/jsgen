package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JSGStaticInitializer extends CodeBlock<JSGStaticInitializer> implements ICommentOwner  {
	private JSGComment comment;

	@Override
	protected JSGStaticInitializer self() {
		return this;
	}

	@Nonnull public JSGStaticInitializer comment(@Nonnull String... pText) {
		assertMutable();
		if (comment == null) {
			comment = new JSGComment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Nonnull public JSGStaticInitializer comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (comment == null) {
			comment = new JSGComment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Override
	@Nullable public JSGComment getComment() {
		return comment;
	}
}
