package cn.tycoding.scst.component.chat.controller;


import cn.tycoding.scst.common.exception.GlobalException;
import cn.tycoding.scst.common.utils.R;
import cn.tycoding.scst.component.chat.constant.ChatConstant;
import cn.tycoding.scst.component.chat.entity.User;
import cn.tycoding.scst.component.chat.service.ChatSessionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 路由接口控制器
 *
 * @author tycoding
 * @date 2019-06-13
 */
@Slf4j
@Controller
public class RouterController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ChatSessionService chatSessionService;

    /**
     * 登陆页面
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "login";
    }

    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        Set<String> keys = redisTemplate.keys(ChatConstant.USER_PREFIX + ChatConstant.REDIS_MATCH_PREFIX);
        if (keys != null && keys.size() > 0) {
            keys.forEach(key -> {
                User entity = chatSessionService.findById(key);
                if (entity != null) {
                    if ((entity.getName()).equals(user.getName())) {
                        throw new GlobalException("用户名已存在");
                    }
                }
            });
        }
        redisTemplate.boundValueOps(ChatConstant.USER_PREFIX + user.getId()).set(JSONObject.toJSONString(user));
        return new R();
    }

    /**
     * 首页入口
     *
     * @return
     */
    @GetMapping("/{id}/chat")
    public String index(@PathVariable("id") String id) {
        User user = chatSessionService.findById(id);
        if (user == null) {
            return "redirect:/";
        }
        return "index";
    }
}
