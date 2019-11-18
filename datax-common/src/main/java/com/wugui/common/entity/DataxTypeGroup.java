package com.wugui.common.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 实体类(datax_type_group)
 *
 * @author zhouhongfa@gz-yibo.com
 * @version v1.0
 * @since 2019-11-18 11:08:23
 */

@Data
@TableName(value = "datax_type_group")
public class DataxTypeGroup extends Model<DataxTypeGroup> {

    /**
     *
     */
    @TableId
    @ApiModelProperty(value = "")
    private Long id;


    /**
     * 组名
     */
    @ApiModelProperty(value = "组名")
    @TableField(value = "groupname")
    private String groupname;


    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @TableField(value = "type")
    private String type;


    /**
     * 状态：0删除 1启用 2禁用
     */
    @ApiModelProperty(value = "状态：0删除 1启用 2禁用")
    @TableField(value = "status")
    private Integer status;


    /**
     *
     */
    @ApiModelProperty(value = "")
    @TableField(value = "update_by")
    private String updateBy;


    /**
     *
     */
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;


    /**
     *
     */
    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;


    /**
     *
     */
    @ApiModelProperty(value = "")
    @TableField(value = "create_by")
    private String createBy;


    /**
     * 说明
     */
    @ApiModelProperty(value = "说明、注释")
    @TableField(value = "comments")
    private String comments;

}