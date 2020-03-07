import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_Topic {
    private static final String ACTIVEMQ_URL = "tcp://192.168.0.128:61616";
    //主题的名字
    public static final String TOPIC_NAME = "topic01";

    private static final String ACTIVEMQ_TOPIC_NAME = "Topic-Persist";
    public static void main(String[] args) throws JMSException {
        //创建connection工厂
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //connection.start();
        //创建session 两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地有queue 和topic
        Topic topic = session.createTopic(ACTIVEMQ_TOPIC_NAME);
        //创建消西的生产者
        MessageProducer messageProducer = session.createProducer(topic);
       //设置持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //使用消息生产者 生长三条消息 发送到mq的队列里
        for (int i = 0; i <3 ; i++) {
            //通过session创建消息
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME--" + i);
             //使用指定好目的地的消息生产者发送消息
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("****TOPIC_NAME消息发布到MQ完成");



    }
}
