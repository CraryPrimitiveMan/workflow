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
public class Process extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 过程ID
     */
    @TableId(value = "PROCESS_ID", type = IdType.AUTO)
    private Long processId;

    /**
     * 过程名称
     */
    private String processName;

    /**
     * 过程别名
     */
    private String processAlias;


}
