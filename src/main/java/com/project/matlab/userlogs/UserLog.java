package com.project.matlab.userlogs;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserLog {

    public UserLog() {
    }

    public UserLog(String activity, Date timeStamp, Long userId){
        this.activity=activity;
        this.timestamp=timeStamp;
        this.userId=userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private int version;

    private String activity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
