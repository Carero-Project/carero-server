package com.carero.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends RestResponse{
    private long totalCount;
    private int page;
    private List<T> data;
}
