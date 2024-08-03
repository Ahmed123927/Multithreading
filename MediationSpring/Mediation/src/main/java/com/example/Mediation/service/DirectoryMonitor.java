package com.example.Mediation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DirectoryMonitor {

    @Autowired
    private FileParse fileParse;

    private final String directoryToWatch = "D:\\Data\\topics\\New folder\\Calls";
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private WatchService watchService;

    public DirectoryMonitor() {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directoryToWatch);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Scheduled(fixedRate = 10000)
    public void monitorDirectory() {
        executorService.submit(() -> {
            System.out.println("In monitor ");
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filePath = Paths.get(directoryToWatch, ev.context().toString());

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("New file : " + filePath);
                        executorService.submit(() -> {

                            try {
                                fileParse.parseFileAndSave(filePath.toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        });
                    }
                }
        });
    }


}
