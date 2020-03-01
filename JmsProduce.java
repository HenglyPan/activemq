import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class JmsProduce {
    private static final String ACTIVEMQ_URL = "tcp://192.168.0.128:61616";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂 按照给定的url 默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂 获取connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();

        //3.创建会话session 两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地 不管是queue 还是topic
        Queue queue = session.createQueue("queue01");

        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6.创建消息
        for (int i = 0; i <4 ; i++) {
            TextMessage textMessage = session.createTextMessage("I love active" + i);
            //7.通过messageProducer发送给MQ队列
            messageProducer.send(textMessage);
        }
        // 9.关闭资源
        messageProducer.close();
        session.close();
        System.out.println("****消息发布到MQ队列完成");

    }

}