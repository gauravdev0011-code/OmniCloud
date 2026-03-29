package com.omnicloud.realtime;

public class PresenceEvent {

    private String type; // JOIN or LEAVE
    private Long userId;
    private Long teamId;
    private long timestamp;

    public PresenceEvent() {}

    public PresenceEvent(String type, Long userId, Long teamId) {
        this.type = type;
        this.userId = userId;
        this.teamId = teamId;
        this.timestamp = System.currentTimeMillis();
    }

    public String getType() { return type; }
    public Long getUserId() { return userId; }
    public Long getTeamId() { return teamId; }
    public long getTimestamp() { return timestamp; }

    public void setType(String type) { this.type = type; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}