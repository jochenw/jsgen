package com.github.jochenw.jsgen.api;

public class Throw {
	private final JQName type;
	private final Object constructorArgs;

	public Throw(JQName type, Object constructorArgs) {
		this.type = type;
		this.constructorArgs = constructorArgs;
	}

	public JQName getType() {
		return type;
	}

	public Object getConstructorArgs() {
		return constructorArgs;
	}
}
