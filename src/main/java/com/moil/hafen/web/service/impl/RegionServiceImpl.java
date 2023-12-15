package com.moil.hafen.web.service.impl;

import com.moil.hafen.common.mapper.LambdaQueryWrapperX;
import com.moil.hafen.web.dao.RegionCityMapper;
import com.moil.hafen.web.dao.RegionDistrictMapper;
import com.moil.hafen.web.dao.RegionProvinceMapper;
import com.moil.hafen.web.domain.RegionCity;
import com.moil.hafen.web.domain.RegionDistrict;
import com.moil.hafen.web.domain.RegionProvince;
import com.moil.hafen.web.service.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 省市区业务层实现类
 * @Version 1.0.0
 * @Date 2023/12/15 9:06
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Resource
    private RegionProvinceMapper regionProvinceMapper;
    @Resource
    private RegionCityMapper regionCityMapper;
    @Resource
    private RegionDistrictMapper regionDistrictMapper;

    @Override
    public List<RegionProvince> getRegion() {
        List<RegionProvince> provinceList = regionProvinceMapper.selectList(null);
        return provinceList;
    }

    @Override
    public List<RegionCity> getCity(String provinceCode) {
        LambdaQueryWrapperX<RegionCity> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(RegionCity::getProvinceCode, provinceCode);
        List<RegionCity> cityList = regionCityMapper.selectList(queryWrapperX);
        return cityList;
    }

    @Override
    public List<RegionDistrict> getDistrict(String cityCode) {
        LambdaQueryWrapperX<RegionDistrict> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eqIfPresent(RegionDistrict::getCityCode, cityCode);
        List<RegionDistrict> districtList = regionDistrictMapper.selectList(queryWrapperX);
        return districtList;
    }
}
