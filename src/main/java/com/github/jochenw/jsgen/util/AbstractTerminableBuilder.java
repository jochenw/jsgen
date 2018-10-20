package com.github.jochenw.jsgen.util;

public abstract class AbstractTerminableBuilder<T extends AbstractBuilder<T>, F extends AbstractTerminableBuilder<T,F>> extends AbstractBuilder<T> {
	public abstract F end();
}
