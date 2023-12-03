package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.EventReminderDao;
import com.moil.hafen.web.domain.EventReminder;
import com.moil.hafen.web.service.EventReminderService;
import org.springframework.stereotype.Service;

@Service
public class EventReminderServiceImpl extends ServiceImpl<EventReminderDao,EventReminder> implements EventReminderService {
}
