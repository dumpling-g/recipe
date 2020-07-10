package cc.mrbird.febs.recipe.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 菜谱 Entity
 *
 * @author MrBird
 * @date 2020-07-09 08:44:28
 */
@Data
@TableName("recipe")
public class Recipe {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 状态1草稿2发布
     */
    @TableField("status")
    private String status;

    /**
     * 
     */
    @TableField("view_count")
    private Integer viewCount;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
}
