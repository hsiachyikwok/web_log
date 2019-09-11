package com.hsia.weblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hsia
 */
@ApiModel
@Data
public class PageReqVO {

    @ApiModelProperty(value = "页码",required = true)
    private Integer page;

    @ApiModelProperty(value = "行数", required = true)
    private Integer rows;
}
