package com.github.jochenw.jsgen.api;

public class JSGThrow {
	private final JSGQName type;
	private final Object constructorArgs;

	public JSGThrow(JSGQName type, Object constructorArgs) {
		this.type = type;
		this.constructorArgs = constructorArgs;
	}

	public JSGQName getType() {
		return type;
	}

	public Object getConstructorArgs() {
		return constructorArgs;
	}
}
