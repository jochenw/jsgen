package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.jochenw.jsgen.impl.Quoter;

public class JSGSource extends JSGClass<JSGSource> {
	private JSGComment packageComment;

	public JSGSource(JSGQName pType) {
		super(pType);
	}

	@Override
	protected JSGSource self() {
		return this;
	}

	@Nonnull public JSGSource packageComment(@Nonnull String... pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new JSGComment().makePublic();
		}
		packageComment.text(pText);
		return this;
	}

	@Nonnull public JSGSource comment(@Nonnull Iterable<String> pText) {
		assertMutable();
		if (packageComment == null) {
			packageComment = new JSGComment().makePublic();
		}
		packageComment.text(pText);
		return self();
	}

	@Nullable
	public JSGComment getPackageComment() {
		return packageComment;
	}

	public static String q(String pValue) {
		return Quoter.valueOf(pValue);
	}
}
