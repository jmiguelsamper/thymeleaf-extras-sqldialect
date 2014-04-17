
SQL Thymeleaf Dialect and Example
=================================

------------------------------------------------------------------------------

Overview
--------

Once upon a time there were websites with a one to one matching between requests and pages.

Firstly, those pages were just HTML. Then dynamic content was added in the form of SSI or CGI.
Later, a lot of websites were made using that paradigm with the ASP, JSP or PHP languages.

As a pastime, this example web application introduces a way of doing PHP-like web pages using Thymeleaf with zero Java code.


Quick start
-----------

- Download source code
- Run 
```
mvn jetty:run
```
- Open http://localhost:8080/
- You can modify HTML files on src/main/webapp folder on the fly.


To do
-----

There is a lot of room for improvement. For example:
- Configure a nice ErrorPage
- Unit test dialect code
- Allow several queries per page (providing variable name for the result)
- Add utilities for common tasks: redirection, binary upload...


License
-------

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).


