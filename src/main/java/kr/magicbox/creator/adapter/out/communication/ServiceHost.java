package kr.magicbox.creator.adapter.out.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceHost {
    USER("user"),
    SUBSCRIBE("subscribe"),
    REVIEW("review"),
    SHORTFORM("shortform"),
    RELEASE("release");

    private final String hostName;
}