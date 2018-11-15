package com.github.hicolors.leisure.member.intf;

import com.github.hicolors.leisure.common.model.expression.ColorsExpression;
import com.github.hicolors.leisure.member.model.model.platform.*;
import com.github.hicolors.leisure.member.model.persistence.Platform;
import com.github.hicolors.leisure.member.model.persistence.PlatformJob;
import com.github.hicolors.leisure.member.model.persistence.PlatformMember;
import com.github.hicolors.leisure.member.model.persistence.PlatformOrganization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PlatformApi
 *
 * @author weichao.li (liweichao0102@gmail.com)
 * @date 2018/10/28
 */

@Api(tags = "platform", description = "对平台的相关操作")
@RequestMapping("platform")
public interface PlatformApi {

    @ApiOperation("平台 - 创建")
    @PostMapping
    Platform create(@RequestBody PlatformModel model);

    @ApiOperation("平台 - 查询")
    @GetMapping("/{id}")
    Platform query(@PathVariable("id") Long id);

    @ApiOperation("平台 - 部分修改")
    @PatchMapping("/{id}")
    Platform modify(@PathVariable("id") Long id, @RequestBody PlatformPatchModel model);

    @ApiOperation("平台 - 查询组织架构")
    @GetMapping("/{id}/organization")
    PlatformOrganization queryOrganization(@PathVariable("id") Long id);

    @ApiOperation("平台 - 创建组织架构")
    @PostMapping("/{id}/organization")
    PlatformOrganization createOrganization(@PathVariable("id") Long id, @RequestBody PlatformOrganizationModel model);

    @ApiOperation("平台 - 修改组织架构信息")
    @PatchMapping("/{pid}/organization/{oid}")
    PlatformOrganization modifyOrganization(@PathVariable("pid") Long pid, @PathVariable("oid") Long oid, @RequestBody PlatformOrganizationPatchModel model);

    @ApiOperation("平台 - 创建岗位")
    @PostMapping("/{id}/job")
    PlatformJob createJob(@PathVariable("id") Long id, @RequestBody PlatformJobModel model);

    @ApiOperation("平台 - 查询{pid}平台下{oid}组织的员工")
    @GetMapping("/{pid}/organization/{oid}/member")
    Page<PlatformMember> queryPlatformMember(@PathVariable("pid") Long pid, @PathVariable("oid") Long oid, @PageableDefault(page = 0) Pageable pageable, List<ColorsExpression> filters);

    @ApiOperation("平台 - 创建员工")
    @PostMapping("/{pid}/organization/{oid}/member")
    PlatformMember createMember(@PathVariable("pid") Long pid, @PathVariable("oid") Long oid, @RequestBody PlatformMemberModel model);

    @ApiOperation("平台 - 修改员工信息")
    @PatchMapping("/{pid}/organization/{oid}/member/{pmid}")
    PlatformMember modifyMember(@PathVariable("pid") Long pid, @PathVariable("oid") Long oid, @PathVariable("pmid") Long pmid, @RequestBody PlatformMemberPatchModel model);


}