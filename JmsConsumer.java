import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.io.IOException;

public class JmsConsumer{
        private static final String ACTIVEMQ_URL = "tcp://192.168.0.128:61616";
        public static void main(String[] args) throws JMSException, IOException {
            //获取连接代工厂
            ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
            //创建连接对象
            Connection connection= activeMQConnectionFactory.createConnection();
            connection.start();
            //创建session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地(具体是队列queue还是主题topic)
            Queue queue01 = session.createQueue("queue01");
            //创建消息消费者 指定消费哪一个队列里面的消息
            MessageConsumer messageConsumer = session.createConsumer(queue01);
           //6.通过监听的方式消费消息
               /*
        异步非阻塞式方式监听器(onMessage)
        订阅者或消费者通过创建的消费者对象,给消费者注册消息监听器setMessageListener,
        当消息有消息的时候,系统会自动调用MessageListener类的onMessage方法
        我们只需要在onMessage方法内判断消息类型即可获取消息
         */
           messageConsumer.setMessageListener(new MessageListener() {
             @Override
            public void onMessage(Message message) {
            if (message != null && message instanceof TextMessage) {
            //7.把message转换成消息发送前的类型并获取消息内容
             TextMessage textMessage = (TextMessage) message;
            try {
             System.out.println("****消费者接收到的消息:  " + textMessage.getText());
            } catch (JMSException e) {
             e.printStackTrace();
              }
             }
           }
         });
             System.out.println("执行了39行");
            //保证控制台不关闭,阻止程序关闭
             System.in.read();
            //关闭资源
             messageConsumer.close();
             session.close();
             connection.close();
    }
}
