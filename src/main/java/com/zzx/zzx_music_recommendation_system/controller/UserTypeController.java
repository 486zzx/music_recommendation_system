package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.service.UserTypeService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2023-04-03
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/zzx_music_recommendation_system/user-type")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @ApiOperation("填写用户喜好标签")
    @PostMapping(value = "/fillUserLikeType")
    public ResVO<Void> fillUserLikeType(@RequestBody @Valid ReqVO<List<Long>> musicTypeIds) {
        try {
            userTypeService.fillUserLikeType(musicTypeIds.getArgs());
            return ResVO.ok();
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("用户是否填写喜好标签")
    @PostMapping(value = "/isExistUserLikeType")
    public ResVO<Boolean> isExistUserLikeType() {
        try {
            return ResVO.ok(userTypeService.isExistUserLikeType());
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

}
