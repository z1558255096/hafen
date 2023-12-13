package com.moil.hafen.web.vo;

import com.moil.hafen.web.domain.Clue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 线索列表返回对象
 * @Version 1.0.0
 * @Date 2023/12/14 2:47
 */
@Data
@ApiModel("线索列表返回对象")
public class ClueVo {
    @ApiModelProperty("线索信息")
    private Clue clue;

    @ApiModelProperty("跟进状态：1-待跟进;2-跟进中;3-已到访;4-已成交;5-已失效")
    private String followUpStatus;

    @ApiModelProperty("下次跟进时间")
    private Date nextFollowUpTime;
}
