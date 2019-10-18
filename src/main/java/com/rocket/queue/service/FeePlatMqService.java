package com.rocket.queue.service;

import org.apache.rocketmq.client.producer.SendResult;

/**
 * @author pauljy
 * @program rocketQueue
 * @description
 * @date 2019-10-11 17:20
 */
public interface FeePlatMqService {
    SendResult openAccountMsg(String msgInfo);
}
