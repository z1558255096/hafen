package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.AboutUsDao;
import com.moil.hafen.web.domain.AboutUs;
import com.moil.hafen.web.service.AboutUsService;
import org.springframework.stereotype.Service;

@Service
public class AboutUsServiceImpl extends ServiceImpl<AboutUsDao,AboutUs> implements AboutUsService {
}
