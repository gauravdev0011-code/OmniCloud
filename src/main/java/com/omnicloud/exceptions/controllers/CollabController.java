package com.omnicloud.exceptions.controllers;

import com.omnicloud.websocket.PresenceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/api/collab")
public class CollabController {

    private final PresenceService presenceService;

    public CollabController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @MessageMapping("/typing")
    @SendTo("/topic/presence")
    public Map<String, String> typing(Principal principal) {
        String username = principal != null ? principal.getName() : "anonymous";
        presenceService.userTyping(username);
        return Map.of(
                "type", "TYPING",
                "username", username
        );
    }

    @GetMapping("/online")
    @ResponseBody
    public Set<String> online() {
        return presenceService.getOnlineUsers();
    }
}