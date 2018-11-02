package com.github.jochenw.jsgen.api;

import javax.annotation.Nonnull;


public class ResettableBlock extends Block<ResettableBlock> {
	private @Nonnull final Block<?> parent;

	public ResettableBlock(@Nonnull Block<?> pParent) {
		parent = pParent;
	}

	public void commit() {
		parent.getContents().addAll(getContents());
	}

	public void rollback() {
		// Do nothing, just don't add contents to parent.
	}
}
