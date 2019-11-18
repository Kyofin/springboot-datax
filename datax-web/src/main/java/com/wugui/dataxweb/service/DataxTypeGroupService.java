package com.wugui.dataxweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wugui.common.entity.DataxTypeGroup;

import java.util.List;
import java.util.Map;

/**
 * 表服务接口
 *
 * @author
 * @version v1.0
 * @since 2019-11-18 10:48:11
 */
public interface DataxTypeGroupService extends IService<DataxTypeGroup> {

    List<Map<String, String>> getGroupNameOptions();

    List<Map<String, String>> getTypeOptions();
}