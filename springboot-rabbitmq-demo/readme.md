### RabbitMQ

<p>使用Erlang编写的一个开源的消息队列，本身支持很多的协议：AMQP，XMPP, SMTP,STOMP，也正是如此，
   使的它变的非常重量级，更适合于企业级的开发。同时实现了Broker架构，核心思想是生产者不会将消息直接发送给队列，
   消息在发送给客户端时先在中心队列排队。对路由(Routing)，负载均衡(Load balance)、数据持久化都有很好的支持。
   多用于进行企业级的ESB整合。</p>
   
   RabbitMQ提供了四种Exchange模式：fanout,direct,topic,header 。 header模式在实际使用中较少，这里只讨论前三种模式。
   
   - fanout 模式就是广播模式，消息来了，会发给所有的队列
   - Direct 模式就是指定队列模式， 消息来了，只发给指定的 Queue, 其他Queue 都收不到。
   - topic  主题模式