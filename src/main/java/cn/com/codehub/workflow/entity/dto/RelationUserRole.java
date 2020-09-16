package cn.com.codehub.workflow.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import cn.com.codehub.workflow.entity.dto.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author guangjunsun
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RelationUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
