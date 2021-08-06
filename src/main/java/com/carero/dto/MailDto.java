package com.carero.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class MailDto {
    @NotNull
    private String email;
}
