package com.carero.dto.response;

import lombok.Data;

@Data
public class SingleResponse<T> extends RestResponse{
    private T data;
}
