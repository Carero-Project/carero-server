package com.carero.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultPaging<T> {
    private int totalCount;
    private int page;
    private T data;
}
