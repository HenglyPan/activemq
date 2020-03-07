import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {
    private static final String ACTIVEMQ_URL = "tcp://192.168.0.128:61616";
    public static final String TOPIC_NAME = "topic01";
    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("我是1号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建目的地 topic 或queue
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息的消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //指定消费哪一个队列里的消息 使用拉曼达表达式
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage){
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("消费者接受到TOPIC的消息"+text);
                 } catch (JMSException e) {
                 e.printStackTrace();
                 }
               }
            });
                 System.in.read();
                 consumer.close();
                 connection.close();
                 session.close();

                 }
}








