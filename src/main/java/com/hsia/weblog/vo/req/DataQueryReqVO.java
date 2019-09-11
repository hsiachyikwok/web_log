package com.hsia.weblog.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hsia
 */
@ApiModel
@Data
public class DataQueryReqVO {

    @ApiModelProperty(value = "id" ,required = true)
    @NotNull(message = "id不能为空！")
    private int id;


}
