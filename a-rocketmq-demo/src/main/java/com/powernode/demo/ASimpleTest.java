package com.powernode.demo;

import com.powernode.constant.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

public class ASimpleTest {



    @Test
    void simpleProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");

        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        producer.start();
        Message message = new Message("testTopic1", "我是一条简单的消息".getBytes());
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult.getSendStatus());
        producer.shutdown();

    }



    @Test
    void  simpleConsumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("test-consumer-group");

        defaultMQPushConsumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        defaultMQPushConsumer.subscribe("testTopic1", "*");





    }


}
