package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.impl.Quoter;

public class Source extends JSGClass<Source> {
	private Comment packageComment;

	public Source(JQName pType) {
		super(pType);
	}

	@Override
	protected Source self() {
		return this;
	}

	@Nonnull public Source packageComment(@Nonnull String... pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new Comment().makePublic();
		}
		packageComment.text(pText);
		return this;
	}

	@Nonnull public Source comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new Comment().makePublic();
		}
		packageComment.text(pText);
		return self();
	}

	@Nullable
	public Comment getPackageComment() {
		return packageComment;
	}

	public static String q(String pValue) {
		return Quoter.valueOf(pValue);
	}
}
