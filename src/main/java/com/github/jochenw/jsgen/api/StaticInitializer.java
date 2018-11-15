package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StaticInitializer extends CodeBlock<StaticInitializer> implements ICommentOwner  {
	private Comment comment;

	@Override
	protected StaticInitializer self() {
		return this;
	}

	@Nonnull public StaticInitializer comment(@Nonnull String... pText) {
		assertMutable();
		if (comment == null) {
			comment = new Comment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Nonnull public StaticInitializer comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (comment == null) {
			comment = new Comment().makePublic(false);
		}
		comment.text(pText);
		return this;
	}

	@Override
	@Nullable public Comment getComment() {
		return comment;
	}
}