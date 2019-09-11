package com.hsia.weblog.vo.req;

import com.hsia.weblog.vo.PageReqVO;
import lombok.Data;

/**
 * @author hsia
 */
@Data
public class LoggerQueryReqVO  extends PageReqVO {
    private int id;

    private String dirName;

    private String fileName;
}
