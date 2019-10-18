package com.rocket.queue.service.impl;

import com.rocket.queue.service.FeePlatMqService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pauljy
 * @program rocketQueue
 * @description
 * @date 2019-10-11 17:21
 */
@Service
public class FeePlatMqServiceImpl implements FeePlatMqService {
    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Resource
    private ParamConfigService paramConfigService;
    @Override
    public SendResult openAccountMsg(String msgInfo) {

        defaultMQProducer.setProducerGroup(paramConfigService.feePlatGroup);
        SendResult sendResult = null;
        try {
            Message message = new Message(paramConfigService.feePlatTopic,
                    paramConfigService.feeAccountTag,
                    "fee_open_account_key", msgInfo.getBytes());
            sendResult = defaultMQProducer.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sendResult;
    }
}
