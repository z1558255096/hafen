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
    @ApiModelProperty("每页数据数量")
    private int limit;
    private int offset;
    @ApiModelProperty("第几页")
    private Integer pageNum;
    private String source;

    public int getPageNum() {
        if(pageNum ==null) {
            if (limit <= 0) {
                limit = 10;
            }
            return (offset / limit) + 1;
        }{
            return pageNum;
        }
    }

    private String sortField;
    private String sortOrder;
}
