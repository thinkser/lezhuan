package com.thinkser.lezhuan.data;

import com.thinkser.core.utils.MarkedUtil;

/**
 * 所有提示语
 */

public class Marked {

    public static void initMarked() {
        customMarked();


//        MarkedUtil.addMark(7005, "昵称不能为空");
//        MarkedUtil.addMark(7006, "密码必须大于6位");
//        MarkedUtil.addMark(7007, "确认密码与密码不一致");
//        MarkedUtil.addMark(7008, "该手机号不存在");

    }

    //自定义提示语
    private static void customMarked(){
        //登录
        MarkedUtil.addMark(7000, "手机号不能为空");
        MarkedUtil.addMark(7001, "手机号格式错误");
        MarkedUtil.addMark(7002, "密码不能为空");
        MarkedUtil.addMark(7003, "手机号或密码错误");
        MarkedUtil.addMark(7004, "该手机号尚未注册");
        //注册
        MarkedUtil.addMark(7005, "该手机号已被注册");
        MarkedUtil.addMark(7006, "验证码已发送");
        MarkedUtil.addMark(7007, "验证码获取失败");
        MarkedUtil.addMark(7008, "手机号或验证码错误");
        MarkedUtil.addMark(7009, "昵称不能为空");
        MarkedUtil.addMark(7010, "确认密码不能为空");
        MarkedUtil.addMark(7011, "密码不能小于6位");
        MarkedUtil.addMark(7012, "密码与确认密码不一致");
        MarkedUtil.addMark(7013, "验证码不能为空");
        MarkedUtil.addMark(7014, "验证码必须为6位数字");
    }
}
