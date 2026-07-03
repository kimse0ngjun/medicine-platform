package org.cloud.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPasswordResponse {

    private boolean success;
    private String message;
}
