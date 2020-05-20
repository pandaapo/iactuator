package com.panda.actuator.iactuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AppHealth1 implements HealthIndicator {

    /**
     * 模拟监测：返回写死的404健康信息
     * @return
     */
    @Override
    public Health health() {
        int errorCode = checkHealth();
        if (errorCode != 0) {
            //返回自定义健康信息
            return Health.down().withDetail("httpstatus", errorCode).withDetail("app-actuator", "down").withDetail("message", "连接出现问题").build();
        }
        return Health.up().withDetail("httpstatus", 0).withDetail("app-actuator", "up").withDetail("message", "连接正常").build();
    }

    /**
     * 对监控对象的检测操作
     * @return
     */
    private int checkHealth() {
        return HttpStatus.NOT_FOUND.value();
    }
}
