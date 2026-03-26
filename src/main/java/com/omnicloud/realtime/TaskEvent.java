package com.omnicloud.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnicloud.realtime.TaskEvent;

import org.springframework.stereotype.Service;

@Service
public class TaskEventService {

    private final TaskWebSocketHandler handler;
    private final ObjectMapper mapper = new ObjectMapper();

    public TaskEventService(TaskWebSocketHandler handler) {
        this.handler = handler;
    }

    // send event to all connected users
    public void publish(TaskEvent event) {
        try {
            String json = mapper.writeValueAsString(event);
            handler.broadcast(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}