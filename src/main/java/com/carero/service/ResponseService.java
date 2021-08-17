package com.carero.service;

import com.carero.dto.response.PageResponse;
import com.carero.dto.response.RestResponse;
import com.carero.dto.response.SingleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> result = new SingleResponse<>();
        result.setData(data);
        result.setSuccessResult();
        return result;
    }

    public <T> PageResponse<T> getPageResponse(long count, int page, List<T> list) {
        PageResponse<T> result = new PageResponse<>();
        result.setTotalCount(count);
        result.setPage(page);
        result.setData(list);
        result.setSuccessResult();
        return result;
    }

    public RestResponse getSuccessResponse(){
        RestResponse result = new RestResponse();
        result.setSuccessResult();
        return result;
    }

    public RestResponse getFailResponse(int code, String msg){
        RestResponse result = new RestResponse();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

}
