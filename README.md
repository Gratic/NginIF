<h1 align="center"> Ngin'IF </h1> <br>
<p align="center">
  Very basic web server implementation made in Java by two students.
</p>

<h2 id="table-content">Table of Content</h2>
<ul>
<li><a href="#introduction">Introduction</a></li>
<li><a href="#features">Features</a></li>
<li><a href="#restrictive">Restrictive Access</a></li>
<li><a href="#contributors">Contributors</a></li>
<li><a href="#development-info">Development Environment</a></li>
</ul>

<h2 id="introduction">Introduction</h2>

The goal of this project was to implement a really basic web server.
This webserver is able to answer to GET, HEAD, POST, PUT, DELETE HTTP request.
Although, only fully implement GET, HEAD, DELETE.
*This project should never be used as a production web server.*

<h2 id="features">Features</h2>

* Answer to HTTP Methods
  * GET
  * HEAD
  * POST (Parameters are written in a file "postParameters" in /resources)
  * PUT (Only text data, no binary data)
  * DELETE
* Dynamic pages (pages which answer changes depending on GET parameters)
* Restrictive access to resources

<h2 id="restrictive">Restrictive Access</h2>
<p>All resources requested are restricted to /resources.</br>
Though, PUT and DELETE resources are restricted to /resources/uploads directory.</br>
Also, dynamic pages must start their resource path with /servlet.</p>

<h2 id="contributors">Contributors</h2>
<p>Florierie (<a href="https://github.com/Florierie">@Florierie</a>)</br>
Gratic (<a href="https://github.com/Gratic">@Gratic</a>)</p>

<h2 id="development-info">Development Environment</h2>
IDE : IntelliJ IDEA 2021.2.2</br>
JDK : OpenJDK 17
