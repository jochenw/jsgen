package com.github.jochenw.jsgen.api;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.api.Subroutine.Parameter;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.SourceSerializer;


public class GettersAndSettersTest {
	private static final String GETTERS_AND_SETTERS_DEFAULT = "package com.foo.myapp;\n" + 
			"\n" + 
			"import javax.annotation.Nonnull;\n" + 
			"\n" + 
			"public class Bean {\n" +
			"    private int number;\n" +
			"    public int getNumber() {\n" +
			"        return number;\n" +
			"    }\n" +
			"    @Nonnull\n" +
			"    public void setNumber(int pNumber) {\n" +
			"        number = pNumber;\n" +
			"    }\n" +
			"}\n";

	private static final String GETTERS_AND_SETTERS_MAVEN = "package com.foo.myapp;\n" + 
			"\n" + 
			"import javax.annotation.Nonnull;\n" + 
			"\n" +
			"public class Bean\n" +
			"{\n" +
			"    private int number;\n" +
			"    public int getNumber(  ) \n" +
			"    {\n" +
			"        return number;\n" +
			"    }\n" +
			"    @Nonnull\n" +
			"    public void setNumber( int pNumber ) \n" +
			"    {\n" +
			"        number = pNumber;\n" +
			"    }\n" +
			"}\n";

	private static final String GETTERS_AND_SETTERS_DEFAULT_TERSE = "package com.foo.myapp;\n" + 
			"\n" + 
			"import javax.annotation.Nonnull;\n" + 
			"\n" + 
			"public class Bean {\n" +
			"    private int number;\n" +
			"    public int getNumber() { return number; }\n" +
			"    @Nonnull public void setNumber(int pNumber) { number = pNumber; }\n" +
			"}\n";

	private static final String GETTERS_AND_SETTERS_MAVEN_TERSE = "package com.foo.myapp;\n" + 
			"\n" + 
			"import javax.annotation.Nonnull;\n" + 
			"\n" +
			"public class Bean\n" +
			"{\n" +
			"    private int number;\n" +
			"    public int getNumber(  ) { return number; }\n" +
			"    @Nonnull public void setNumber( int pNumber ) { number = pNumber; }\n" +
			"}\n";

	@Test
	public void testDefault() {
		final String got = generateBeanClass(AbstractSourceWriter.DEFAULT_FORMATTER, false);
		assertEquals(GETTERS_AND_SETTERS_DEFAULT, got);
	}

	@Test
	public void testDefaultTerse() {
		final String got = generateBeanClass(AbstractSourceWriter.DEFAULT_FORMATTER, true);
		assertEquals(GETTERS_AND_SETTERS_DEFAULT_TERSE, got);
	}

	@Test
	public void testMaven() {
		final String got = generateBeanClass(AbstractSourceWriter.MAVEN_FORMATTER, false);
		assertEquals(GETTERS_AND_SETTERS_MAVEN, got);
	}

	@Test
	public void testMavenTerse() {
		final String got = generateBeanClass(AbstractSourceWriter.MAVEN_FORMATTER, true);
		assertEquals(GETTERS_AND_SETTERS_MAVEN_TERSE, got);
	}

	protected String generateBeanClass(SourceSerializer pFormatter, boolean pTerse) {
		final JSGFactory factory = JSGFactory.create();
		final Source jsb = factory.newSource("com.foo.myapp.Bean").makePublic();
		final Field numberField = jsb.newField(JQName.INT_TYPE, "number").makePrivate();
		jsb.newMethod(JQName.INT_TYPE, "getNumber")
			.makePublic()
			.terse(pTerse)
		    .body()
		        .tline("return ", numberField);
		final Method setNumberMethod = jsb.newMethod("setNumber").makePublic().terse(pTerse);
		final Parameter pNumber = setNumberMethod.parameter(JQName.INT_TYPE, "pNumber");
		setNumberMethod
		    .annotation(Nonnull.class);
		setNumberMethod.body()
		        .tline("number = ", pNumber);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final AbstractSourceWriter jsgw = new AbstractSourceWriter() {
			@Override
			protected OutputStream open(NamedResource pResource) throws IOException {
				return baos;
			}
		};
		jsgw.setFormatter(pFormatter);
		jsgw.write(factory);
		final String got = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		return got;
	}
}
