package subscription;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Levy on 2017/2/27.
 */
public class P2sConsumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        Topic topic = null;
        MessageConsumer messageConsumer = null;
        try {
            // 创建工厂
            // ActiveMQConnection.DEFAULT_USER 默认null
            // ActiveMQConnection.DEFAULT_PASSWORD 默认null
            // ActiveMQConnection.DEFAULT_BROKER_URL
            // 默认failover://tcp://localhost:61616
            connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD,
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            // 创建连接
            conn = connectionFactory.createConnection();
            // 启动连接
            conn.start();
            // 创建会话 createSession(true, Session.AUTO_ACKNOWLEDGE); false 表示不开启事务
            // Session.AUTO_ACKNOWLEDGE 消息模式
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建Topic
            topic = session.createTopic("P2sTopic");
            // 创建消息消费者
            messageConsumer = session.createConsumer(topic);
            // 注册消费消息监听
            messageConsumer.setMessageListener(new MessageListener() {

                public void onMessage(Message message) {
                    try {
                        System.out.println("订阅者1-订阅的消息："
                                + ((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
