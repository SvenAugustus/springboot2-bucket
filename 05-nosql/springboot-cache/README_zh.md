https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache


### 缓存抽象
从3.1版开始，Spring框架提供了对将缓存透明添加到现有Spring应用程序的支持。与[事务支持](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#transaction)类似，缓存抽象允许一致使用各种缓存解决方案，而对代码的影响最小。


从Spring 4.1开始，通过支持[JSR-107 注解](https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-jsr-107)和更多自定义选项，对缓存抽象进行了显着扩展。


#### 基本原理
缓存抽象的核心是将缓存应用于Java方法，从而根据缓存中可用的信息减少执行次数。也就是说，每次调用目标方法时，抽象都会应用缓存行为，该行为检查给定参数是否已经执行了该方法。如果已执行，则返回缓存的结果，而不必执行实际的方法。如果尚未执行该方法，则将执行该方法，并将结果缓存并返回给用户，以便下次调用该方法时，将返回缓存的结果。这样，对于给定的一组参数，昂贵的方法（无论是受CPU限制还是与IO绑定）只能执行一次，结果可以重复使用，而不必再次实际执行该方法。缓存逻辑是透明应用的，不会对调用方造成任何干扰。

><font color='red'>该方法仅适用于保证无论给定输入（或参数）执行多少次都返回相同输出（结果）的方法。 </font>


Spring cache 利用了 Spring AOP 的动态代理技术，即当客户端尝试调用 pojo 的 foo（）方法的时候，给他的不是 pojo 自身的引用，而是一个动态生成的代理类
图 3. 动态代理调用图
![图 3. 动态代理调用图](proxy.jpg)  
如上图所示，这个时候，实际客户端拥有的是一个代理的引用，那么在调用


foo() 方法的时候，会首先调用 proxy 的 foo() 方法，这个时候 proxy 可以整体控制实际的 pojo.foo()


方法的入参和返回值，比如缓存结果，比如直接略过执行实际的 foo() 方法等，都是可以轻松做到的。


#### 核心概念

缓存抽象提供了其他与缓存相关的操作，例如更新缓存内容或删除一个或所有条目的能力。如果高速缓存处理在应用程序过程中可能更改的数据，则这些功能很有用。


与Spring Framework中的其他服务一样，缓存服务是一种抽象（不是缓存实现），并且需要使用实际的存储来存储缓存数据-也就是说，抽象使您不必编写缓存逻辑，但是没有提供实际的数据存储。`org.springframework.cache.Cache` 和 `org.springframework.cache.CacheManager` 接口实现了这种抽象。


Spring提供了这种抽象的一些实现：`java.util.concurrent.ConcurrentMap` 基于JDK 的缓存，`Ehcache 2.x`，`Gemfire`缓存，`Caffeine`和符合JSR-107的缓存（例如Ehcache 3.x）。
><font color='red'>对于多线程和多进程环境，缓存抽象没有特殊处理，因为此类功能由缓存实现处理。    
如果您具有多进程环境（即，一个应用程序部署在多个节点上），则需要相应地配置缓存提供程序。根据您的用例，在几个节点上复制相同数据就足够了。但是，如果在应用程序过程中更改数据，则可能需要启用其他传播机制。
</font>


Caching a particular item is a direct equivalent of the typical get-if-not-found-then-


proceed-and-put-eventually code blocks found with programmatic cache interaction.


No locks are applied, and several threads may try to load the same item concurrently.


The same applies to eviction. If several threads are trying to update or evict data


concurrently, you may use stale data. Certain cache providers offer advanced features


in that area. See the documentation of your cache provider for more details.


高速缓存特定项直接等同于通过程序化高速缓存交互找到的典型“如果找不到，然后继续处理并放入”代码块。没有应用锁，几个线程可能会尝试同时加载同一项目。驱逐同样如此。如果多个线程试图同时更新或逐出数据，则可以使用陈旧数据。某些缓存提供程序在该区域提供高级功能。有关更多详细信息，请参见缓存提供程序的文档。


To use the cache abstraction, you need to take care of two aspects:
- Caching declaration: Identify the methods that need to be cached and their policy.
- Cache configuration: The backing cache where the data is stored and from which it is read.


### 缓存声明

<div class="wiz-table-container" style="position: relative; padding: 0px;" contenteditable="false"><div class="wiz-table-body" contenteditable="true"><table><thead><tr><th>名称</th><th align="left">解释</th></tr></thead><tbody><tr><td><div><span>org.springframework.cache.Cache</span><br></div></td><td align="left">缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等</td></tr><tr><td><div><span>org.springframework.cache.CacheManager</span><br></div></td><td align="left"><div>缓存管理器，管理各种缓存（cache）组件</div></td></tr><tr><td class=""><div>@Cacheable</div></td><td align="left">主要针对方法配置，能够根据方法的请求参数对其进行缓存</td></tr><tr><td class=""><div>@CacheEvict</div></td><td align="left">清空缓存</td></tr><tr><td class=""><div>@CachePut</div></td><td align="left" class=""><div>保证方法被调用，又希望结果被缓存。<br>与@Cacheable区别在于是否每次调用方法都更新缓存，常用于更新</div></td></tr><tr><td class=""><div>@EnableCaching</div></td><td align="left">开启基于注解的缓存</td></tr><tr><td>keyGenerator</td><td align="left">缓存数据时key生成策略</td></tr><tr><td class=""><div>serialize</div></td><td align="left">缓存数据时value序列化策略</td></tr><tr><td class="" style=""><div><font color="#ff0000">@Caching</font></div></td><td style="" class=""><div><font color="#ff0000">支持批量<span style="">@Cacheable、</span><span style="">@CacheEvict、<span style="">@CachePut</span></span></font></div></td></tr><tr><td>@CacheConfig</td><td align="left">统一配置本类的缓存注解的属性</td></tr></tbody></table></div></div>



- `@Cacheable`/`@CachePut`/`@CacheEvict` 主要的参数
<div class="wiz-table-container" style="position: relative; padding: 0px;" contenteditable="false"><div class="wiz-table-body" contenteditable="true"><table style="width: 877px;"><thead><tr><th class="" style="width: 255px;"><div>名称</div></th><th class="" style="width: 621px;">解释</th></tr></thead><tbody><tr><td style="width: 255px;" class=""><div><b>value</b></div></td><td class="" style="width: 621px;"><div><font color="#ff0000">缓存的名称</font>，在 spring 配置文件中定义，必须指定至少一个<br><div><br></div>例如：<br><div><br></div>@Cacheable(value=”mycache”) 或者<br><div><br></div>@Cacheable(value={”cache1”,”cache2”}</div></td></tr><tr><td style="width: 255px;">key</td><td class="" style="width: 621px;"><div>缓存的 key，可以为空，如果指定要按照 <font color="#ff0000">SpEL</font> 表达式编写，<br><div><br></div>如果不指定，则缺省按照方法的所有参数进行组合<br><div><br></div>例如：<br><div><br></div>@Cacheable(value=”testcache”,key=”#id”)</div></td></tr><tr><td class="" style="width: 255px;"><div>condition</div></td><td class="" style="width: 621px;"><div>缓存的条件，可以为空，使用 <font color="#ff0000">SpEL</font> 编写，返回 true 或者 false，<br><div><br></div>只有为 true 才进行缓存/清除缓存<br><div><br></div>例如：@Cacheable(value=”testcache”,condition=”#userName.length()&gt;2”)</div></td></tr><tr><td style="width: 255px;" class=""><div>unless</div></td><td style="width: 621px;" class=""><div>否定缓存。<span style="">使用<span>&nbsp;</span></span><font color="#ff0000" style="">SpEL</font><span style=""><span>&nbsp;</span>编写,</span>当条件结果为TRUE时，就不会缓存。<br><div><br></div>@Cacheable(value=”testcache”,unless=”#userName.length()&gt;2”)</div></td></tr><tr><td style="width: 255px;">allEntries<br><div><br></div>(@CacheEvict )</td><td style="width: 621px;" class=""><div>是否清空所有缓存内容，缺省为 false，如果指定为 true，<br><div><br></div>则方法调用后将立即清空所有缓存<br><div><br></div>例如：<br><div><br></div>@CachEvict(value=”testcache”,allEntries=true)</div></td></tr><tr><td style="width: 255px;">beforeInvocation<br><div><br></div>(@CacheEvict)</td><td style="width: 621px;" class=""><div>是否在方法执行前就清空，缺省为 false，如果指定为 true，<br><div><br></div>则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法<br><div><br></div>执行抛出异常，则不会清空缓存<br><div><br></div>例如：<br><div><br></div>@CachEvict(value=”testcache”，beforeInvocation=true)</div></td></tr></tbody></table></div></div>



- Spring Cache提供了一些供我们使用的SpEL上下文数据，下表直接摘自Spring官方文档：
https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-spel-context
<div class="wiz-table-container" style="position: relative; padding: 0px;" contenteditable="false"><div class="wiz-table-body" contenteditable="true"><table style="width: 968px;"><thead><tr><th style="width: 135px;">名称</th><th style="width: 117px;">位置</th><th style="width: 472px;">描述</th><th style="width: 243px;">示例</th></tr></thead><tbody><tr><td style="width: 135px;" class=""><div><span style="">methodName</span></div></td><td style="width: 117px;">root对象</td><td style="width: 472px;" class="">当前被调用的方法名</td><td style="width: 243px;" class=""><div><code>#root.methodname</code></div></td></tr><tr><td style="width: 135px;" class=""><div><span style="">method</span></div></td><td style="width: 117px;" class=""><div>root对象</div></td><td style="width: 472px;">当前被调用的方法</td><td style="width: 243px;"><code>#root.method.name</code></td></tr><tr><td style="width: 135px;" class=""><div><span style="">target</span></div></td><td style="width: 117px;">root对象</td><td style="width: 472px;">当前被调用的目标对象实例</td><td style="width: 243px;"><code>#root.target</code></td></tr><tr><td style="width: 135px;" class=""><div><span style="">targetClass</span></div></td><td style="width: 117px;">root对象</td><td style="width: 472px;">当前被调用的目标对象的类</td><td style="width: 243px;"><code>#root.targetClass</code></td></tr><tr><td style="width: 135px;" class=""><div><span style="">args</span></div></td><td style="width: 117px;">root对象</td><td style="width: 472px;">当前被调用的方法的参数列表</td><td style="width: 243px;"><code>#root.args[0]</code></td></tr><tr><td style="width: 135px;" class=""><div><span style="">caches</span></div></td><td style="width: 117px;">root对象</td><td style="width: 472px;">当前方法调用使用的缓存列表</td><td style="width: 243px;"><code>#root.caches[0].name</code></td></tr><tr><td style="width: 135px;">Argument Name</td><td style="width: 117px;">执行上下文</td><td style="width: 472px;">当前被调用的方法的参数，如findArtisan(Artisan artisan),可以通过#artsian.id获得参数</td><td style="width: 243px;"><code>#artsian.id</code></td></tr><tr><td style="width: 135px;" class=""><div><span style="">result</span></div></td><td style="width: 117px;">执行上下文</td><td style="width: 472px;">方法执行后的返回值（仅当方法执行后的判断有效，如 unless cacheEvict的beforeInvocation=false）</td><td style="width: 243px;"><code>#result</code></td></tr></tbody></table></div></div>

- SpEL提供了多种运算符
<div class="wiz-table-container" style="position: relative; padding: 0px;" contenteditable="false"><div class="wiz-table-body" contenteditable="true"><table><thead><tr><th><strong>类型</strong></th><th><strong>运算符</strong></th></tr></thead><tbody><tr><td>关系</td><td>&lt;，&gt;，&lt;=，&gt;=，==，!=，lt，gt，le，ge，eq，ne</td></tr><tr><td>算术</td><td>+，- ，* ，/，%，^</td></tr><tr><td>逻辑</td><td>&amp;&amp;，||，!，and，or，not，between，instanceof</td></tr><tr><td>条件</td><td>?: (ternary)，?: (elvis)</td></tr><tr><td>正则表达式</td><td>matches</td></tr><tr><td>其他类型</td><td>?.，?[…]，![…]，^[…]，$[…]</td></tr></tbody></table></div></div>



### 缓存配置
https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotation-enable
<div class="wiz-table-container" style="position: relative; padding: 0px;" contenteditable="false"><div class="wiz-table-body" contenteditable="true"><table id="cache-annotation-driven-settings" class="tableblock frame-all grid-all spread" style="width: 1086px;"><caption class="title">Table 12. Cache annotation settings</caption><colgroup><col style=""><col style=""><col style=""><col style=""></colgroup><thead><tr><th class="tableblock halign-left valign-top" style="width: 124px;">XML Attribute</th><th class="tableblock halign-left valign-top" style="width: 206px;">Annotation Attribute</th><th class="tableblock halign-left valign-top" style="width: 265px;">Default</th><th class="tableblock halign-left valign-top" style="width: 490px;">Description</th></tr></thead><tbody><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>cache-manager</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock">N/A (see the <a href="https://docs.spring.io/spring-framework/docs/5.2.1.RELEASE/javadoc-api/org/springframework/cache/annotation/CachingConfigurer.html"><code>CachingConfigurer</code></a> javadoc)</p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock"><code>cacheManager</code></p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">The name of the cache manager to use. A default <code>CacheResolver</code> is initialized behind</p><div><br></div>the scenes with this cache manager (or <code>cacheManager</code> if not set). For more<div><br></div>fine-grained management of the cache resolution, consider setting the 'cache-resolver'<div><br></div>attribute.<p></p><p class="tableblock"><font><font>要使用的缓存管理器的名称。</font></font><code>CacheResolver</code><font><font>使用此缓存管理器（或</font></font><code>cacheManager</code><font><font>如果未设置）</font><font>在后台初始化</font><font>默认值</font><font>。如果需要</font><font>更精细地管理缓存方案，请考虑设置“ cache-resolver”属性。</font></font></p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>cache-resolver</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock">N/A (see the <a href="https://docs.spring.io/spring-framework/docs/5.2.1.RELEASE/javadoc-api/org/springframework/cache/annotation/CachingConfigurer.html"><code>CachingConfigurer</code></a> javadoc)</p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock">A <code>SimpleCacheResolver</code> using the configured <code>cacheManager</code>.</p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">The bean name of the CacheResolver that is to be used to resolve the backing caches.</p><div><br></div>This attribute is not required and needs to be specified only as an alternative to<div><br></div>the 'cache-manager' attribute.<p></p><p class="tableblock"><font><font>用来解析后备缓存的CacheResolver的bean名称。</font><font>此属性不是必需的，仅需指定为“ cache-manager”属性的替代方法。</font></font></p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>key-generator</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock">N/A (see the <a href="https://docs.spring.io/spring-framework/docs/5.2.1.RELEASE/javadoc-api/org/springframework/cache/annotation/CachingConfigurer.html"><code>CachingConfigurer</code></a> javadoc)</p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock"><code>SimpleKeyGenerator</code></p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">Name of the custom key generator to use.</p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>error-handler</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock">N/A (see the <a href="https://docs.spring.io/spring-framework/docs/5.2.1.RELEASE/javadoc-api/org/springframework/cache/annotation/CachingConfigurer.html"><code>CachingConfigurer</code></a> javadoc)</p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock"><code>SimpleCacheErrorHandler</code></p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">The name of the custom cache error handler to use. By default, any exception thrown during</p><div><br></div>a cache related operation is thrown back at the client.<p></p><p class="tableblock"><font><font>要使用的自定义缓存错误处理程序的名称。</font><font>默认情况下，在与缓存相关的操作期间抛出的所有异常都将返回给客户端。</font></font></p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>mode</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock"><code>mode</code></p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock"><code>proxy</code></p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">The default mode (<code>proxy</code>) processes annotated beans to be proxied by using Spring’s AOP</p><div><br></div>framework (following proxy semantics, as discussed earlier, applying to method calls<div><br></div>coming in through the proxy only). The alternative mode (<code>aspectj</code>) instead weaves the<div><br></div>affected classes with Spring’s AspectJ caching aspect, modifying the target class byte<div><br></div>code to apply to any kind of method call. AspectJ weaving requires <code>spring-aspects.jar</code><div><br></div>in the classpath as well as load-time weaving (or compile-time weaving) enabled. (See<div><br></div><a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-aj-ltw-spring">Spring configuration</a> for details on how to set up<div><br></div>load-time weaving.)<p></p><p class="tableblock">支持 Proxy 和&nbsp;<span style="font-family: monospace;">aspectj 两种模式。</span></p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>proxy-target-class</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock"><code>proxyTargetClass</code></p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock"><code>false</code></p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">Applies to proxy mode only. Controls what type of caching proxies are created for</p><div><br></div>classes annotated with the <code>@Cacheable</code> or <code>@CacheEvict</code> annotations. If the<div><br></div><code>proxy-target-class</code> attribute is set to <code>true</code>, class-based proxies are created.<div><br></div>If <code>proxy-target-class</code> is <code>false</code> or if the attribute is omitted, standard JDK<div><br></div>interface-based proxies are created. (See <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-proxying">Proxying Mechanisms</a><div><br></div>for a detailed examination of the different proxy types.)<p></p></td></tr><tr><td class="tableblock halign-left valign-top" style="width: 124px;"><p class="tableblock"><code>order</code></p></td><td class="tableblock halign-left valign-top" style="width: 206px;"><p class="tableblock"><code>order</code></p></td><td class="tableblock halign-left valign-top" style="width: 265px;"><p class="tableblock">Ordered.LOWEST_PRECEDENCE</p></td><td class="tableblock halign-left valign-top" style="width: 490px;"><p class="tableblock">Defines the order of the cache advice that is applied to beans annotated with</p><div><br></div><code>@Cacheable</code> or <code>@CacheEvict</code>. (For more information about the rules related to<div><br></div>ordering AOP advice, see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-ataspectj-advice-ordering">Advice Ordering</a>.)<div><br></div>No specified ordering means that the AOP subsystem determines the order of the advice.<p></p></td></tr></tbody></table></div></div>















