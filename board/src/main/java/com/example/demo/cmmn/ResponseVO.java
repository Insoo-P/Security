package com.example.demo.cmmn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO {

    private boolean result;

    public static ResponseVO saveOk() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setResult(true);
        return responseVO;
    }

    public static ResponseVO saveFail() {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setResult(false);
        return responseVO;
    }
}
