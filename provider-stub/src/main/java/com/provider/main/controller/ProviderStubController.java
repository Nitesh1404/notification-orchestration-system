package com.provider.main.controller;

import com.google.gson.Gson;
import com.notification.constants.NotificationStatus;
import com.notification.domain.ProviderRequest;
import com.notification.domain.ProviderResponse;
import com.provider.main.service.StubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class ProviderStubController {

    @Autowired
    private StubService stubService;

    private final Gson gson= new Gson();

    private ExecutorService executorService;

    @PostMapping(value = "/ProviderStub")
    public ResponseEntity<ProviderResponse> handleProviderRequest(@PathVariable ProviderRequest providerRequest){
        log.info("Received Request to stub : {}" , providerRequest);

        executorService = Executors.newWorkStealingPool();
        ProviderResponse providerResponse = new ProviderResponse();

        String messageId = genMsgId();

        executorService.submit(()->{
            log.info("Executor Service started");
            stubService.processStub(providerRequest , messageId);
        });

        providerResponse.setStatus(NotificationStatus.QUEUED.getStatus());
        providerResponse.setMsgId(messageId);

        return ResponseEntity.status(HttpStatus.OK).body(providerResponse);
    }

    private String genMsgId(){
        return UUID.randomUUID().toString().replace("-" , "").replace("_","");
    }
}
