package com.provider.main.controller;

import com.google.gson.Gson;
import com.notification.domain.ProviderRequest;
import com.notification.domain.ProviderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
public class ProviderStubController {

    private final Gson gson= new Gson();

    private ExecutorService executorService;

    @PostMapping(value = "/ProviderStub")
    public ResponseEntity<ProviderResponse> handleProviderRequest(@PathVariable ProviderRequest providerRequest){
        log.info("Received Request to stub : {}" , providerRequest);
        ProviderResponse providerResponse = new ProviderResponse();


        return ResponseEntity.status(HttpStatus.OK).body(providerResponse);
    }
}
