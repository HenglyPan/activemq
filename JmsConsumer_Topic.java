import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {
    private static final String ACTIVEMQ_URL = "tcp://192.168.0.128:61616";
    public static final String TOPIC_NAME = "topic01";

    private static final String ACTIVEMQ_TOPIC_NAME= "Topic-Persist";
    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("我是1号消费者张三");
        //创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("ppp");
      //  connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建目的地 topic 或queue
        Topic topic = session.createTopic(ACTIVEMQ_TOPIC_NAME);

        //通过session创建持久化订阅
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "我是张三");
        connection.start();

        //创建消息的消费者
       // MessageConsumer consumer = session.createConsumer(topic);
        //指定消费哪一个队列里的消息
        topicSubscriber.setMessageListener(message -> {
            if (message instanceof TextMessage){
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("接收到持久化订阅者消息"+text);
                 } catch (JMSException e) {
                 e.printStackTrace();
                 }
               }
            });
                 System.in.read();
                 topicSubscriber.close();
                 connection.close();
                 session.close();

                 }
}








