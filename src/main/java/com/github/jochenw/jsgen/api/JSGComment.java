package com.github.jochenw.jsgen.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.github.jochenw.jsgen.util.AbstractBuilder;
import com.github.jochenw.jsgen.util.Objects;

public class JSGComment extends AbstractBuilder<JSGComment> {
	private final List<String> text = new ArrayList<String>();
	private boolean isPublic;

	@Nonnull public JSGComment text(@Nonnull String... pText) {
		Objects.requireAllNonNull(pText, "Text Line");
		assertMutable();
		text.clear();
		text.addAll(Arrays.asList(pText));
		return self();
	}

	@Nonnull public JSGComment text(Iterable<String> pText) {
		Objects.requireAllNonNull(pText, "Text Line");
		assertMutable();
		text.clear();
		for (String s : pText) {
			text.add(s);
		}
		return self();
	}

	@Nonnull public List<String> getText() {
		return text;
	}
	
	@Override
	protected JSGComment self() {
		return this;
	}

	public boolean isPublic() {
		return isPublic;
	}

	@Nonnull public JSGComment makePublic() {
		return makePublic(true);
	}

	@Nonnull public JSGComment makePublic(boolean pPublic) {
		assertMutable();
		isPublic = pPublic;
		return this;
	}
}
