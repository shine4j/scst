package cn.tycoding.scst.system.biz.controller;

import cn.tycoding.scst.common.controller.BaseController;
import cn.tycoding.scst.common.utils.QueryPage;
import cn.tycoding.scst.common.utils.R;
import cn.tycoding.scst.common.log.annotation.Log;
import cn.tycoding.scst.system.api.entity.SysDept;
import cn.tycoding.scst.system.biz.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tycoding
 * @date 2019-06-03
 */
@RestController
@RequestMapping("/dept")
@Api(value = "SysDeptController", tags = {"部门管理接口"})
public class SysDeptController extends BaseController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询部门列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dept", value = "查询条件", required = true, dataType = "SysDept", paramType = "body"),
            @ApiImplicitParam(name = "queryPage", value = "分页条件", required = true, dataType = "QueryPage", paramType = "body")
    })
    public R<Map> list(SysDept dept, QueryPage queryPage) {
        return new R<>(this.selectByPageNumSize(queryPage, () -> sysDeptService.list(dept)));
    }

    @GetMapping("/tree")
    @ApiOperation(value = "获取部门Tree树")
    public R<List> tree() {
        return new R<>(sysDeptService.tree());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询详细部门信息", notes = "id存在且大于0")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "Long")
    public R<SysDept> findById(@PathVariable Long id) {
        if (id == null || id == 0) {
            return new R<>();
        } else {
            return new R<>(sysDeptService.findById(id));
        }
    }

    @Log("添加部门")
    @PostMapping
    @ApiOperation(value = "添加部门")
    @ApiImplicitParam(name = "dept", value = "部门实体信息", required = true, dataType = "SysDept", paramType = "body")
    public R add(@RequestBody SysDept dept) {
        sysDeptService.add(dept);
        return new R();
    }

    @Log("删除部门")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "Long")
    public R delete(@PathVariable Long id) {
        sysDeptService.delete(id);
        return new R();
    }

    @Log("更新部门")
    @PutMapping
    @ApiOperation(value = "更新部门")
    @ApiImplicitParam(name = "dept", value = "部门实体信息", required = true, dataType = "SysDept", paramType = "body")
    public R edit(@RequestBody SysDept dept) {
        sysDeptService.update(dept);
        return new R();
    }

    @GetMapping("/checkName/{name}/{id}")
    @ApiOperation(value = "校验该名称是否已存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String")
    })
    public R<Boolean> checkName(@PathVariable("name") String name, @PathVariable("id") String id) {
        return new R<>(sysDeptService.checkName(name, id));
    }
}
