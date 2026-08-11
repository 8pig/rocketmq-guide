package com.powernode.demo;

import com.powernode.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.util.List;

public class ASimpleTest {



    @Test
    public void simpleProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");

        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        producer.start();
        for (int i = 0; i < 10; i++) {
            String s = "我是第"+ i+"条消息";
            Message message = new Message("testTopic", s.getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult.getSendStatus());

        }
        producer.shutdown();

    }



    @Test
    public void simpleConsumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("test-consumer-group");
//        new MQPullConsumer()
//        defaultMQPushConsumer.setConsumeThreadMin(1);
//        defaultMQPushConsumer.setConsumeThreadMax(1);

        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        defaultMQPushConsumer.subscribe("testTopic", "*");

        defaultMQPushConsumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                        System.out.println("我是消费者");
                        System.out.println("消息: "+new String(msgs.get(0).getBody()));
                        System.out.println(context);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
        );
//        一直监听  异步回调
        defaultMQPushConsumer.start();

        System.in.read();





    }


}
