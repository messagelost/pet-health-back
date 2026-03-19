package com.jacob.service.system;

import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.service.base.BaseService;

public interface NotifyTemplateService extends BaseService<NotifyTemplate> {
    NotifyTemplate getByEventTypeInCache(Integer eventType);
}
