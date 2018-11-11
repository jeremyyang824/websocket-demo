package com.jeremyyang.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StompConnectedEvent.class);

    @Override
    public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
        logger.info("client connected... ");
    }
}
