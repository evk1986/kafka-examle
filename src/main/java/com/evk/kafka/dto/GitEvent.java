package com.evk.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class GitEvent {

    private String id;
    private GitEventType type;
    private Actor actor;
    private Repo repo;
    @JsonProperty("created_at")
    private Instant createdAt;


    public GitEventType getType() {
        return type;
    }

    public void setType(GitEventType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

@Data
class Actor {
    private String id;
    private String url;
}

@Data
class Repo {
    private String id;
    private String url;
}


//    Event object common properties
//    CommitCommentEvent
//            CreateEvent
//    DeleteEvent
//            ForkEvent
//    GollumEvent
//            IssueCommentEvent
//    IssuesEvent
//            MemberEvent
//    PublicEvent
//            PullRequestEvent
//    PullRequestReviewCommentEvent
//            PushEvent
//    ReleaseEvent
//            SponsorshipEvent
//WatchEvent


//{
//
//        "id": 13999415685,
//
//        "type": "PullRequestReviewEvent",
//
//        "actorId": 54027687,
//
//        "actorUrl": "https://api.github.com/users/jkumar-moj",
//
//        "repoId": 143270778,
//
//        "repoUrl": "https://api.github.com/repos/hmcts/ia-case-api",
//
//        "createdAt": "2020-10-28T12:48:25"
//
//        }