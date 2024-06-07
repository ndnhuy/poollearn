package com.ndnhuy.poollearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class RandomStringController {

    @Autowired
    private RandomStringClient client;

    @Autowired
    private RandomStringRepository repository;

    @GetMapping("/randomString")
    public ResponseEntity<RandomStringEntity> getRandomString() {
        var str = client.getRandomString();
        var e = RandomStringEntity.builder().randomString(str).build();
        repository.save(e);
        return ResponseEntity.ok(e);
    }
}
