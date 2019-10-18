package com.rocket.queue.controller;

import com.rocket.queue.service.impl.FeePlatMqServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author pauljy
 * @program rocketQueue
 * @description
 * @date 2019-10-11 17:28
 */
@RestController
@RequestMapping("/rocketMq")
public class RocketController {
    @Resource
    private FeePlatMqServiceImpl feePlatMqService;

    @GetMapping(value = "/sendMsg")
    public ResponseEntity sendMsg(@RequestParam("content") String content) {
        if (content == null) {
            return ResponseEntity.badRequest().body("content不能为空");
        }
        feePlatMqService.openAccountMsg(content);
        return ResponseEntity.ok("成功");
    }
}
