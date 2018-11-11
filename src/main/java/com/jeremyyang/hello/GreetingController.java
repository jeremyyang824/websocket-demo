package com.jeremyyang.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class GreetingController {

    private final SimpMessageSendingOperations messagingTemplate;

    private final Random rnd = new Random(47);
    private final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();


    @Autowired
    public GreetingController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

        // mock income streams
        singleThreadPool.submit(() -> {
            while(true) {
                Thread.sleep(1500);
                String msg = "Message: " + rnd.nextInt(1000);
                Greeting greeting = new Greeting(msg);

                // broadcast to all clients
                this.messagingTemplate.convertAndSend("/topic/something", greeting);
            }
        });
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
