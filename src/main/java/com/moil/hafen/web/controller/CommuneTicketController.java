package com.moil.hafen.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.tencent.TencentService;
import com.moil.hafen.common.tencent.resp.TencentResp;
import com.moil.hafen.web.domain.CommuneTicket;
import com.moil.hafen.web.domain.CommuneTicketAdvance;
import com.moil.hafen.web.domain.CommuneTicketAdvanceOption;
import com.moil.hafen.web.service.CommuneTicketAdvanceOptionService;
import com.moil.hafen.web.service.CommuneTicketAdvanceService;
import com.moil.hafen.web.service.CommuneTicketService;
import com.moil.hafen.web.vo.LocationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理后台/公社模块/公社门票管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeTicket"})
@Api(tags = "公社门票管理")
public class CommuneTicketController extends BaseController {

    private String message;

    @Resource
    private CommuneTicketService communeTicketService;
    @Resource
    private CommuneTicketAdvanceService communeTicketAdvanceService;
    @Resource
    private CommuneTicketAdvanceOptionService communeTicketAdvanceOptionService;
    @Resource
    private TencentService tencentService;

    /**
     * 获取公社门票列表（分页） - 管理后台/小程序
     *
     * @param request       要求
     * @param communeTicket 公社票
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取公社门票列表（分页）")
    public Result<IPage<CommuneTicket>> page(QueryRequest request, CommuneTicket communeTicket) {
        IPage<CommuneTicket> page = communeTicketService.getPage(request, communeTicket);
        return Result.OK(page);
    }

    /**
     * 地址坐标转位置
     *
     * @param lat 纬度
     * @param lng 经度
     *
     * @return {@link Result}<{@link List}<{@link LocationVO}>>
     */
    @GetMapping("getLocation")
    @ApiOperation("地址坐标转位置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lat", value = "纬度", required = true),
            @ApiImplicitParam(name = "lng", value = "经度", required = true)
    })
    public Result<LocationVO> getLocation(@RequestParam String lat, @RequestParam String lng) {
        TencentResp tencentResp = tencentService.location(lat, lng);
        String jsonData = tencentResp.getData();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        JSONObject location = jsonObject.getJSONObject("location");
        LocationVO locationVO = new LocationVO();
        locationVO.setAddress(jsonObject.get("address").toString());
        locationVO.setLat(location.get("lat").toString());
        locationVO.setLng(location.get("lng").toString());
        return Result.OK(locationVO);
    }

    /**
     * 添加公社门票信息 - 管理后台
     *
     * @param communeTicket 公社票
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社门票信息")
    public Result add(@RequestBody CommuneTicket communeTicket) throws FebsException {
        try {
            communeTicketService.save(communeTicket);
            addAdvance(communeTicket);
            return Result.OK();
        } catch (Exception e) {
            message = "添加公社门票信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除公社门票信息 - 管理后台
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/delete")
    @ApiOperation("删除公社门票信息")
    public Result delete(@RequestParam Integer id) throws FebsException {
        try {
            //删除公社课程信息
            boolean update = communeTicketService.lambdaUpdate().eq(CommuneTicket::getId, id).set(CommuneTicket::getDelFlag, 1).update();
            return Result.OK(update);
        } catch (Exception e) {
            message = "删除公社门票信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架公社门票 - 管理后台
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/changeStatus")
    @ApiOperation("上下架公社门票")
    public Result changeStatus(@RequestParam Integer id, @RequestParam int status) throws FebsException {
        try {
            CommuneTicket communeTicket = new CommuneTicket();
            communeTicket.setStatus(status);
            communeTicket.setId(id);
            communeTicket.setModifyTime(new Date());
            return Result.OK(communeTicketService.updateById(communeTicket));
        } catch (Exception e) {
            message = "上下架公社门票失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社门票信息 - 管理后台
     *
     * @param communeTicket 公社票
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/update")
    @ApiOperation("修改公社门票信息")
    public Result update(@RequestBody CommuneTicket communeTicket) throws FebsException {
        try {
            communeTicket.setModifyTime(new Date());
            communeTicketService.updateById(communeTicket);
            addAdvance(communeTicket);
            return Result.OK();
        } catch (Exception e) {
            message = "修改公社门票信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    private void addAdvance(CommuneTicket communeTicket) {
        //删除原有门票高级设置
        communeTicketAdvanceService.update(new LambdaUpdateWrapper<CommuneTicketAdvance>()
                .eq(CommuneTicketAdvance::getTicketId, communeTicket.getId()).set(CommuneTicketAdvance::getDelFlag, 1));
        //删除原有门票高级设置选项
        communeTicketAdvanceOptionService.update(new LambdaUpdateWrapper<CommuneTicketAdvanceOption>()
                .eq(CommuneTicketAdvanceOption::getTicketId, communeTicket.getId()).set(CommuneTicketAdvanceOption::getDelFlag, 1));
        List<CommuneTicketAdvance> communeLessonAdvanceList = communeTicket.getCommuneTicketAdvanceList();
        if (CollectionUtils.isNotEmpty(communeLessonAdvanceList)) {
            for (CommuneTicketAdvance communeTicketAdvance : communeLessonAdvanceList) {
                communeTicketAdvance.setTicketId(communeTicket.getId());
                communeTicketAdvanceService.save(communeTicketAdvance);

                List<CommuneTicketAdvanceOption> communeTicketAdvanceOptionList = communeTicketAdvance.getCommuneTicketAdvanceOptionList();
                if (CollectionUtils.isEmpty(communeTicketAdvanceOptionList)) {
                    continue;
                }
                for (CommuneTicketAdvanceOption communeTicketAdvanceOption : communeTicketAdvanceOptionList) {
                    communeTicketAdvanceOption.setAdvanceId(communeTicketAdvance.getId());
                    communeTicketAdvanceOption.setTicketId(communeTicket.getId());
                }
                communeTicketAdvanceOptionService.saveBatch(communeTicketAdvanceOptionList);
            }
        }
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
    public Result<CommuneTicket> detail(@RequestParam Integer id) {
        return Result.OK(communeTicketService.detail(id));
    }
}
