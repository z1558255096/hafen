package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.system.domain.LoginLog;

public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog(LoginLog loginLog);
}
