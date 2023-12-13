package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理后台/公社模块/公社门票人员预约信息
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"CommuneTicketReservationPerson"})
@Api(tags = "公社门票人员预约信息")
public class CommuneTicketReservationPersonController extends BaseController {

}
