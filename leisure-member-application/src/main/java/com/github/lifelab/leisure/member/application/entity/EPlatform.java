package com.github.lifelab.leisure.member.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lifelab.leisure.common.jpa.customiz.model.BaseJpaModel;
import com.github.lifelab.leisure.common.model.validator.ValidatorGroup;
import com.github.lifelab.leisure.member.application.entity.converter.PlatformStatusConverter;
import com.github.lifelab.leisure.member.application.entity.enums.EnumPlatformStatus;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * 集团信息
 *
 * @author weichao.li (liweichao0102@gmail.com)
 * @since 2018/10/10
 */
@Entity
@Table(name = "platform")
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "delete_flag = 0")
@ToString(of = {"id", "name", "status"})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EPlatform extends BaseJpaModel {

    /**
     * 主键
     */
    @Null(
            message = "id 必须为空",
            groups = {ValidatorGroup.Post.class, ValidatorGroup.Put.class, ValidatorGroup.Patch.class}
    )
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 发起人
     */
    private Long originator;

    /**
     * 发起人名称
     */
    @Column(name = "originator_name")
    private String originatorName;

    /**
     * 状态[0:禁用;1:审核中;2:启用]
     */
    @Convert(converter = PlatformStatusConverter.class)
    private EnumPlatformStatus status;

    /**
     * varchar ( 255 ) null comment 备注
     */
    private String comment;

    @OneToMany(mappedBy = "platform", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JsonIgnoreProperties({"platform"})
    @Where(clause = "level = 0")
    private List<EPlatformOrganization> organizations;
}
