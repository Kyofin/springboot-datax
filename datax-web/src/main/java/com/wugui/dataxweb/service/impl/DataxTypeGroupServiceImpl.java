package com.wugui.dataxweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wugui.common.entity.DataxTypeGroup;
import com.wugui.dataxweb.dao.DataxTypeGroupMapper;
import com.wugui.dataxweb.service.DataxTypeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表服务实现类
 *
 * @author
 * @version v1.0
 * @since 2019-11-18 10:48:12
 */
@Service
public class DataxTypeGroupServiceImpl extends ServiceImpl
        <DataxTypeGroupMapper, DataxTypeGroup> implements DataxTypeGroupService {

    @Override
    public List<Map<String, String>> getGroupNameOptions() {
        //直接groupby查询再提取groupname字段
        List<DataxTypeGroup> list = this.lambdaQuery().select(DataxTypeGroup::getGroupname).groupBy(DataxTypeGroup::getGroupname).list();
        List<String> groupNameList = list.stream().map(DataxTypeGroup::getGroupname).collect(Collectors.toList());
        List<Map<String, String>> res = Lists.newLinkedList();
        //转成前端使用的格式
        groupNameList.forEach(e -> {
            Map<String, String> tmpMap = Maps.newHashMap();
            tmpMap.putAll(ImmutableMap.of("label", e, "value", e));
            res.add(tmpMap);
        });
        return res;
    }

    @Override
    public List<Map<String, String>> getTypeOptions() {
        //直接groupby查询再提取groupname字段
        List<DataxTypeGroup> list = this.lambdaQuery().select(DataxTypeGroup::getType).groupBy(DataxTypeGroup::getType).list();
        List<String> typeList = list.stream().map(DataxTypeGroup::getType).collect(Collectors.toList());
        List<Map<String, String>> res = Lists.newLinkedList();
        //转成前端使用的格式
        typeList.forEach(e -> {
            Map<String, String> tmpMap = Maps.newHashMap();
            tmpMap.putAll(ImmutableMap.of("label", e, "value", e));
            res.add(tmpMap);
        });
        return res;
    }
}