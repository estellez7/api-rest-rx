package com.clients.demo.web;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class HelloWorldController {

    @GetMapping(
            value = "/helloWorld",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<String>> helloWorld() {
        return Single.just("Hello World!!!")
                .subscribeOn(Schedulers.io())
                .map(ResponseEntity::ok);
    }
}
