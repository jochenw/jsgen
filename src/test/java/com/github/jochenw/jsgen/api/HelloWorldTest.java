package com.github.jochenw.jsgen.api;

import static com.github.jochenw.jsgen.api.Source.q;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import com.github.jochenw.jsgen.api.JSGFactory;
import com.github.jochenw.jsgen.api.Method;
import com.github.jochenw.jsgen.api.JQName;
import com.github.jochenw.jsgen.api.Source;
import com.github.jochenw.jsgen.api.JSGFactory.NamedResource;
import com.github.jochenw.jsgen.impl.AbstractSourceWriter;
import com.github.jochenw.jsgen.impl.JSGSourceFormatter;


public class HelloWorldTest {
	private static final String HELLO_WORLD_JAVA_DEFAULT = "package com.foo.myapp;\n" + 
			"\n" + 
			"import java.lang.String;\n" + 
			"import java.lang.System;\n" + 
			"\n" + 
			"public class Main {\n" + 
			"    public static void main(String[] pArgs) {\n" + 
			"        System.out.println(\"Hello, world!\");\n" + 
			"    }\n" + 
			"}\n";

	private static final String HELLO_WORLD_JAVA_MAVEN = "package com.foo.myapp;\n" + 
			"\n" + 
			"import java.lang.String;\n" + 
			"import java.lang.System;\n" + 
			"\n" + 
			"public class Main\n" +
			"{\n" +
			"    public static void main( String[] pArgs ) \n" +
			"    {\n" +
			"        System.out.println(\"Hello, world!\");\n" + 
			"    }\n" + 
			"}\n";

	/**
	 * Tests creating the class {@link #HELLO_WORLD_JAVA_DEFAULT}.
	 * <pre>
	 *   package com.foo.myapp;
	 *
	 *   public class Main {
	 *       public static void main(String[] pArgs) throws Exception {
	 *           System.out.println("Hello, world!");
	 *       }
	 *   }
	 * </pre>
	 */
	@Test
	public void testHelloWorldJavaDefault() throws Exception {
		final String got = generateHelloWorld(AbstractSourceWriter.DEFAULT_FORMATTER);
		Assert.assertEquals(HELLO_WORLD_JAVA_DEFAULT, got);
	}

	/**
	 * Tests creating the class {@link #HELLO_WORLD_JAVA_MAVEN}.
	 * <pre>
	 *   package com.foo.myapp;
	 *
	 *   public class Main {
	 *       public static void main(String[] pArgs) throws Exception {
	 *           System.out.println("Hello, world!");
	 *       }
	 *   }
	 * </pre>
	 */
	@Test
	public void testHelloWorldJavaMaven() throws Exception {
		final String got = generateHelloWorld(AbstractSourceWriter.MAVEN_FORMATTER);
		Assert.assertEquals(HELLO_WORLD_JAVA_MAVEN, got);
	}

	private String generateHelloWorld(JSGSourceFormatter pFormatter) throws UnsupportedEncodingException {
		final JSGFactory factory = JSGFactory.create();
		final Source jsb = factory.newSource("com.foo.myapp.Main").makePublic();
		final Method mainMethod = jsb.newMethod("main").makePublic().makeStatic();
		mainMethod.parameter(JQName.STRING_ARRAY, "pArgs");
		mainMethod.body().line(System.class, ".out.println(", q("Hello, world!"), ");");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final AbstractSourceWriter jsgw = new AbstractSourceWriter() {
			@Override
			protected OutputStream open(NamedResource pResource) throws IOException {
				return baos;
			}
		};
		jsgw.setFormatter(pFormatter);
		jsgw.write(factory);
		final String got = baos.toString(StandardCharsets.UTF_8.name());
		return got;
	}

}
