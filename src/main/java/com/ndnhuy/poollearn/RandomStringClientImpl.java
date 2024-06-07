package com.ndnhuy.poollearn;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomStringClientImpl implements RandomStringClient {
    @Override
    public String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
