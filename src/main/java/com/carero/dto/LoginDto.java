package com.carero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
