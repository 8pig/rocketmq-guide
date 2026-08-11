package com.powernode.demo;

import com.powernode.constant.MqConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

public class BASync {


    @Test
    public void asyncProducer () throws Exception{

        DefaultMQProducer producer = new DefaultMQProducer("async-produce-group");
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        producer.start();
        Message msg = new Message("asyncTopic", "我是异步消息".getBytes());
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发生成功");
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("发生失败");

            }
        });
        System.out.println("执行了");

        System.in.read();


    }
}
