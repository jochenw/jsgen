package com.github.jochenw.jsgen.api;

import static com.github.jochenw.jsgen.api.Source.q;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import com.github.jochenw.jsgen.api.Block;
import com.github.jochenw.jsgen.api.Constructor;
import com.github.jochenw.jsgen.api.JSGFactory;
import com.github.jochenw.jsgen.api.Field;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.StaticInitializer;
import com.github.jochenw.jsgen.api.LocalField;
import com.github.jochenw.jsgen.api.IProtectable.Protection;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.SourceSerializer;

public class JQStaticInitializerTest {
	private static final String INIT_JAVA_DEFAULT =
			"package com.foo.myapp;\n"
			+ "\n"
			+ "import java.lang.IllegalStateException;\n"
			+ "import java.lang.String;\n"
			+ "import java.lang.System;\n"
			+ "\n"
			+ "public class Foo {\n"
			+ "    private static final String PACKAGE;\n"
			+ "    {\n"
			+ "        final String className = Foo.class.getName();\n"
			+ "        final int index = className.lastIndexOf('.');\n"
			+ "        if (index == -1) {\n"
			+ "            throw new IllegalStateException(\"Unable to parse class name: \" + className);\n"
			+ "        }\n"
			+ "        PACKAGE = className.substring(0, index);\n"
			+ "    }\n"
			+ "    private String packageName = PACKAGE;\n"
			+ "    public Foo() {\n"
			+ "        System.out.println(\"Foo, package = \" + packageName);\n"
			+ "    }\n"
			+ "}\n";

	private static final String INIT_JAVA_MAVEN =
			"package com.foo.myapp;\n"
			+ "\n"
			+ "import java.lang.IllegalStateException;\n"
			+ "import java.lang.String;\n"
			+ "import java.lang.System;\n"
			+ "\n"
			+ "public class Foo\n"
			+ "{\n"
			+ "    private static final String PACKAGE;\n"
			+ "    \n"
			+ "    {\n"
			+ "        final String className = Foo.class.getName();\n"
			+ "        final int index = className.lastIndexOf('.');\n"
			+ "        if ( index == -1 )\n"
			+ "        {\n"
			+ "            throw new IllegalStateException( \"Unable to parse class name: \" + className );\n"
			+ "        }\n"
			+ "        PACKAGE = className.substring(0, index);\n"
			+ "    }\n"
			+ "    private String packageName = PACKAGE;\n"
			+ "    public Foo(  ) \n"
			+ "    {\n"
			+ "        System.out.println(\"Foo, package = \" + packageName);\n"
			+ "    }\n"
			+ "}\n";

	/**
	 * Tests creating the above class.
	 */
	@Test
	public void testInitJavaDefault() throws Exception {
		final JSGFactory factory = JSGFactory.create();
		generateInitJava(factory);
		final String gotDefault = asString(factory, AbstractSourceWriter.DEFAULT_FORMATTER);
		Assert.assertEquals(INIT_JAVA_DEFAULT, gotDefault);
	}

	@Test
	public void testInitJavaMaven() throws Exception {
		final JSGFactory factory = JSGFactory.create();
		generateInitJava(factory);
		final String gotDefault = asString(factory, AbstractSourceWriter.MAVEN_FORMATTER);
		Assert.assertEquals(INIT_JAVA_MAVEN, gotDefault);
	}
	
	private String asString(JSGFactory pFactory, SourceSerializer pFormatter) throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final AbstractSourceWriter jsgw = new AbstractSourceWriter() {
			@Override
			protected OutputStream open(NamedResource pResource) throws IOException {
				return baos;
			}
		};
		jsgw.setFormatter(pFormatter);
		jsgw.write(pFactory);
		return baos.toString(StandardCharsets.UTF_8.name());
	}
	
	private void generateInitJava(JSGFactory pFactory) {
		final Source js = pFactory.newSource("com.foo.myapp.Foo").makePublic();
		final Field jf = js.newField(String.class, "PACKAGE").makePrivate().makeStatic().makeFinal();
		final StaticInitializer jsi = js.newInitializer();
		final Block<?> jsiBody = jsi.body();
		final LocalField classNameField = jsiBody.newField(String.class, "className").makeFinal()
				.assign(js.getType(), ".class.getName()");
		final LocalField indexField = jsiBody.newField(JQName.INT_TYPE, "index").makeFinal()
				.assign(classNameField, ".lastIndexOf('.')");
		jsiBody.newIf(indexField, " == -1")
				.addThrowNew(IllegalStateException.class, q("Unable to parse class name: "), " + ", classNameField);
		jsiBody.line(jf, " = ", classNameField, ".substring(0, ", indexField, ");");
		final Field packageNameField = js.newField(JQName.STRING, "packageName", Protection.PRIVATE)
					.assign(jf);
		final Constructor constructor = js.newConstructor();
		constructor.body().line(System.class, ".out.println(", q(js.getType().getClassName() + ", package = "),
				                " + ", packageNameField, ");");
	}
}
