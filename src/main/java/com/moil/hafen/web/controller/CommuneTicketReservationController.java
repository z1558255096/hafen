package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneTicket;
import com.moil.hafen.web.domain.CommuneTicketReservation;
import com.moil.hafen.web.domain.CommuneTicketReservationPerson;
import com.moil.hafen.web.service.CommuneTicketReservationPersonService;
import com.moil.hafen.web.service.CommuneTicketReservationService;
import com.moil.hafen.web.service.CommuneTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理后台/公社模块/公社门票预约
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"CommuneTicketReservation"})
@Api(tags = "公社门票预约")
public class CommuneTicketReservationController extends BaseController {

    @Resource
    private CommuneTicketReservationService communeTicketReservationService;
    @Resource
    private CommuneTicketReservationPersonService communeTicketReservationPersonService;
    @Resource
    private CommuneTicketService communeTicketService;


    /**
     * 添加公社门票预约信息 - 管理后台
     *
     * @param req 公社门票预约信息
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @Transactional
    @ApiOperation("添加公社门票预约信息")
    public Result add(@RequestBody CommuneTicketReservation req) throws FebsException {
        communeTicketReservationService.save(req);
        List<CommuneTicketReservationPerson> list = req.getTicketReservationPeople();
        for (CommuneTicketReservationPerson communeTicketReservationPerson : list) {
            communeTicketReservationPerson.setReservationId(req.getId());
        }
        communeTicketReservationPersonService.saveBatch(list);
        //减去门票数量
        Boolean update = communeTicketService.updateTicketCount(req.getTicketId(), req.getCount());
        if (!update) {
            throw new FebsException("门票数量不足");
        }
        return Result.OK();
    }

    /**
     * 取消公社预约信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/delete")
    @ApiOperation("取消公社预约信息")
    public Result delete(@RequestParam String id) throws FebsException {
        CommuneTicketReservation byId = communeTicketReservationService.getById(id);
        byId.setDelFlag(1);
        communeTicketReservationService.updateById(byId);
        communeTicketReservationPersonService.lambdaUpdate().set(CommuneTicketReservationPerson::getDelFlag, 1).eq(CommuneTicketReservationPerson::getId, id).update();
        //增加门票数量
        CommuneTicket communeTicket = communeTicketService.getById(byId.getTicketId());
        communeTicket.setCount(communeTicket.getCount() + byId.getCount());
        communeTicketService.updateById(communeTicket);
        return Result.OK();
    }

    /**
     * 通过ID获取公社门票详情 - 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneTicket}>
     */
    @GetMapping("/detail")
    @ApiOperation("通过ID获取公社门票详情")
    public Result<CommuneTicketReservation> detail(@RequestParam Integer id) {
        return Result.OK(communeTicketReservationService.detail(id));
    }

}
