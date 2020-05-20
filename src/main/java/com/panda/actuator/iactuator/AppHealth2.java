package com.panda.actuator.iactuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class AppHealth2 extends AbstractHealthIndicator {

    private final FileStore fileStore;
    private final long thresholdBytes;

    public AppHealth2(@Value("${health.filestore.path:/}") String path, @Value("${health.filestore.threshold.bytes:10485760}") long thresholdBytes) throws IOException {
        //获取当前项目所在硬盘
        fileStore = Files.getFileStore(Paths.get(path));
        this.thresholdBytes = thresholdBytes;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        long diskFreeInBytes = fileStore.getUnallocatedSpace();
        if (diskFreeInBytes >= thresholdBytes) {
            builder.up();
        } else {
            builder.down();
        }

        long totalSpaceInBytes = fileStore.getTotalSpace();
        builder.withDetail("disk.free", diskFreeInBytes);
        builder.withDetail("disk.total", totalSpaceInBytes);
    }
}
