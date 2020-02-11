package com.blz.controller;

import com.blz.domain.User;
import com.blz.service.UserService;
import com.blz.utils.MD5Util;
import com.blz.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> login(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //System.out.println("控制层："+user);

        Map<String, Object> map = new HashMap<>();
        String email = user.getEmail();
        String password = MD5Util.EncoderPwdByMd5(user.getPassword());
        //System.out.println(password);
        if (userService.login(email, password)){
            String token = TokenUtil.sign(email,user.getPassword());
            if (token != null){
                map.put("code", "10000");
                map.put("message","认证成功");
                map.put("token", token);
                System.out.println("success");
                return map;
            }
        }
        map.put("code", "00000");
        map.put("message","error");
        return map;
    }


    @RequestMapping(value = "/online/getuser.do",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getLoginUser(@RequestHeader("token") String token){
        String email = TokenUtil.getEmail(token);
        //System.out.println(token);
        System.out.println("控制层：从token中获取的email值"+email);

        User user = userService.findByEmail(email);
        Map<String,Object> map = new HashMap<>();
        if(user != null){
            map.put("data",user);
        }
        return map;
    }

    @RequestMapping(value = "/changePwd.do",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> changePwd(@RequestBody HashMap<String, String> map){
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        String token = map.get("token");
        String email = TokenUtil.getEmail(token);
        Map<String,Object> map1 = new HashMap<>();
        try {
            if (userService.changePwd(email,MD5Util.EncoderPwdByMd5(oldPwd),MD5Util.EncoderPwdByMd5(newPwd))){
                map1.put("status","success");
                map1.put("msg","更改密码成功");
            }else {
                map1.put("status","error");
                map1.put("msg","旧密码输入不正确");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map1;
    }
}
