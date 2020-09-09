# JAX-WS Using CXF
-  http://cxf.apache.org/docs/springboot.html
-  https://github.com/apache/cxf/tree/master/distribution/src/main/release/samples

#### Starting the server

The sample uses Maven. It can be built and run from the command line using Maven, Java:

##### ---- With Maven ----
```
$ mvn -Pserver
```
##### ---- With Java ----
```
$ java -jar target/springboot-feat-jaxws-xcf.jar
```
#### Endpoint

##### ---- From the browser ----

<http://localhost:8296/services/HelloService?wsdl>

#### Testing the server

##### ---- From the command line ----

```
$ mvn -Pclient
```
see: `SampleWsClientApplication`

##### ---- Generate the client code ----

**wsdl2java tool of Apache**

```
wsdl2java -autoNameResolution http://localhost:8296/services/HelloService?wsdl
```

**wsimport tool of JDK**

```
mkdir -p /home/svenaugustus/client-source-code
wsimport -encoding utf-8 -p com.github.flysium.io.bucket.springboot.client -keep http://localhost:8296/services/HelloService?wsdl -s /home/svenaugustus/client-source-code -B-XautoNameResolution
```

Usage:

```
-encoding ：Specify the encoding format (in this case, utf-8)
-keep：Whether to generate Java source files
-s：Specify the output directory of. java files
-d：Specify the output directory of the. class file
-p：Define the package name of the generated class, if not the default package name
-verbose：Display output information in console
-b：Specify jaxws/jaxb binding files or additional schemas
-extension：Use extensions to support SOAP 1.2
```

The generated client code cannot be renamed.

``` java
CommonService_Service c = new CommonService_Service();
com.xncoding.webservice.client.User user = c.getCommonServiceImplPort().getUser("Tom");
assertThat(user.getName(), is("Tom"));
```