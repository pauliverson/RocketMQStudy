package com.rocket.queue.rocket;

import com.rocket.queue.service.impl.ParamConfigService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pauljy
 * @program rocketQueue
 * @description 消息消费监听
 * @date 2019-10-11 14:16
 */
@Component
public class RocketMsgListener implements MessageListenerConcurrently {

    private static final Logger LOG = LoggerFactory.getLogger(RocketMsgListener.class);
    @Resource
    private ParamConfigService paramConfigService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(list)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        LOG.info("接收到的消息为：{}", new String(messageExt.getBody()));
        int reConsume = messageExt.getReconsumeTimes();
        // 消息已经重试了3次，如果不需要再次消费。则返回成功
        if (reConsume == 3) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        if (messageExt.getTopic().equals(paramConfigService.feePlatTopic)) {
            String tags = messageExt.getTags();
            switch (tags) {
                case "FeeAccountTag":
                    LOG.info("开启 tag == >> {}", tags);
                    break;
                default:
                    LOG.info("未匹配到 tag == >> {}", tags);
                    break;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
