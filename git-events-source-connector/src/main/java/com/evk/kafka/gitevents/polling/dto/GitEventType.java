package com.evk.kafka.gitevents.polling.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum GitEventType {
    CommitCommentEvent,
    CreateEvent,
    DeleteEvent,
    ForkEvent,
    GollumEvent,
    IssueCommentEvent,
    IssuesEvent,
    MemberEvent,
    PublicEvent,
    PullRequestEvent,
    PullRequestReviewCommentEvent,
    PushEvent,
    ReleaseEvent,
    SponsorshipEvent,
    WatchEvent,
    PullRequestReviewEvent,
    @JsonEnumDefaultValue
    UNKNOWN,
}
