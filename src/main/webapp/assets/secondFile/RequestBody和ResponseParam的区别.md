### @RequestBody 和 @RequestParam 的区别：

@RequestParam:用来处理content-type为application/x-www-form-urlencoded编码内容。（Http协议中，如果不指定content-type，则默认传递的参数就是application/x-www-form-urlencoded类型）

RequestParam可以接受简单类型的属性，也可以接受对象类型

实质是将Request.getParameter()中的Key-Value参数Map利用Spring的转化机制ConversionService配置，转化成参数接收对象或字段



@RequestBody：用来处理HttpEntity传递过来的数据，一般用来处理非content-type为application/x-www-form-urlencoded编码内容。

GET请求中，因为没有HttpEntity所以@RequestBody并不适用

POST请求中，通过HttpEntity传递的参数，必须要在请求头中声明数据类型content-type，SpringMVC通过使用HandlerAdapter配置的HttpMessageConverters来解析HTTPEntity中的数据然后绑定到相应的bean上



总结：

在GET请求中，不能使用@RequestBody

在POST请求中，两者均可使用，但是如果使用@RequestBody，对于参数转化的配置必须统一