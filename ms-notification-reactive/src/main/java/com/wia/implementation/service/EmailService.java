package com.wia.implementation.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(String userId, String message) {
	log.info("Enviando EMAIL....{}, {}", userId, message);
    }

}