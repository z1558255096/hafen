package com.moil.hafen.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 8129
 */
@Data
@ApiModel("分页")
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    @ApiModelProperty(value = "每页数据数量", required = true)
    private int limit;
    private int offset;
    @ApiModelProperty(value = "第几页", required = true)
    private int pageNum;
    private String source;


    private String sortField;
    private String sortOrder;
}
