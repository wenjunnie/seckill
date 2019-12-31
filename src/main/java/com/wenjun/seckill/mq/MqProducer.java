package com.wenjun.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.OrderService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wenjun
 * @Date: 2019/12/30 21:10
 */
@Component
public class MqProducer {

    private DefaultMQProducer producer;

    private TransactionMQProducer transactionMQProducer;

    @Value("${mq.nameserver.addr}")//properties文件中配置
    private String nameAddr;

    @Value("${mq.topicname}")
    private String topicName;

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() throws MQClientException {
        //做MQProducer的初始化
        producer = new DefaultMQProducer("producer_group");//名字任取
        producer.setNamesrvAddr(nameAddr);
        producer.start();

        transactionMQProducer = new TransactionMQProducer("transaction_producer_group");
        transactionMQProducer.setNamesrvAddr(nameAddr);
        transactionMQProducer.start();

        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
                //真正要做的事（创建订单）
                Integer userId = (Integer) ((Map)arg).get("userId");
                Integer itemId = (Integer) ((Map)arg).get("itemId");
                Integer amount = (Integer) ((Map)arg).get("amount");
                Integer promoId = (Integer) ((Map)arg).get("promoId");
                try {
                    orderService.createOrder(userId,itemId,amount,promoId);
                } catch (BusinessException e) {
                    e.printStackTrace();
                    //MySQL回滚了，设置
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                return null;
            }
        });
    }

    //事务型同步库存扣减消息（投放消息），消息发送成功再执行MySQL下单逻辑
    public boolean transactionAsyncReduceStock(Integer userId, Integer itemId, Integer amount, Integer promoId) {
        Map<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("itemId",itemId);
        bodyMap.put("amount",amount);

        Map<String,Object> argsMap = new HashMap<>();
        argsMap.put("userId",userId);
        argsMap.put("itemId",itemId);
        argsMap.put("amount",amount);
        argsMap.put("promoId",promoId);

        Message message = new Message(topicName,"increase",JSON.toJSON(bodyMap).toString().getBytes(StandardCharsets.UTF_8));
        TransactionSendResult sendResult;
        try {
            sendResult = transactionMQProducer.sendMessageInTransaction(message,argsMap);
        } catch (MQClientException e) {
            //消息投放失败
            e.printStackTrace();
            return false;
        }
        if (sendResult.getLocalTransactionState() == LocalTransactionState.ROLLBACK_MESSAGE) {
            return false;
        } else if (sendResult.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
            return true;
        } else {
            return false;
        }
    }

    //同步库存扣减消息（投放消息）
    public boolean asyncReduceStock(Integer itemId, Integer amount) {
        Map<String,Object> bodyMap = new HashMap<>();
        bodyMap.put("itemId",itemId);
        bodyMap.put("amount",amount);
        Message message = new Message(topicName,"increase",JSON.toJSON(bodyMap).toString().getBytes(StandardCharsets.UTF_8));
        try {
            producer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            //消息投放失败
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
