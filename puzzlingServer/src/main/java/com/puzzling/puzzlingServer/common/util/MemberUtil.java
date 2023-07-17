package com.puzzling.puzzlingServer.common.util;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Objects;

@RequiredArgsConstructor

public class MemberUtil {

    public static Long getMemberId(Principal principal) {
        if (Objects.isNull(principal)) throw new IllegalArgumentException("principal is null");
        return Long.valueOf(principal.getName());
    }
}
