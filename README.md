# Jsgen (Java Source Generation Framework)

Jsgen is a Java Source Generation Framework: That means, it should be a valuable tool, if you intend to write a custom generator for Java
sources.

As such, it is the successor of a previous framework, called [JaxMeJS](http://jaxme.sourceforge.net/JaxMeJS/docs/index.html).
The predecessor came into being as a standalone project. It was incorporated into the bigger JaxMe project, when the latter
was adopted by the Apache Webservices project. And it was buried as part of the bigger project, when the latter was moved to the
[Apache Attic](http://svn.apache.org/repos/asf/webservices/archive/jaxme/).

That was fine for quite some time, because the latest released version (JaxMeJS 0.5.2) did its job quite well.
Over the years, however, the Java language has evolved, and the lack of support for features like Generics, or
Annotations, became a burden. Hence the Successor: Jsgen picks up, where JaxMeJS ended. It is, however, a complete
rewrite with several additional features, that the author considers to be important for modern Java applications:

1. It supports Generics.
2. It supports Annotations.
3. The builder pattern has been adopted. Almost all important classes are implemented as builders. This should make
writing the actual source generators much more concise, and maintainable, than it used to be before.
4. The code style is adaptable. Code styles allow you to concentrate on the actual work. The resulting Jave source will
   look nicely formatted, anyways. As of this writing, you can select between two builtin code styles:

  - The default code style is basically the authors personal free style, roughly comparable two the default
    code style of the Eclipse Java IDE.

  - As an alternative, there is also a Maven code style, which is widely used in the Open Source communities.
5. Import lists are created, and sorted, automatically.




