package com.thinkser.lezhuan.data;

import com.thinkser.core.utils.MarkedUtil;

/**
 * 所有提示语
 */

public class Marked {

    public static void initMarked() {
        //自定义提示语
        MarkedUtil.addMark(7000, "手机号不能为空");
        MarkedUtil.addMark(7001, "手机号输入有误");
        MarkedUtil.addMark(7002, "用户名或密码错误");
        MarkedUtil.addMark(7003, "该手机号已被注册");
        MarkedUtil.addMark(7004, "昵称不能为空");
        MarkedUtil.addMark(7005, "密码必须大于6位");
        MarkedUtil.addMark(7006, "确认密码与密码不一致");
        MarkedUtil.addMark(7007, "该手机号未被注册");

        //bmob提示语
        MarkedUtil.addMark(9001, "Application Id为空，请初始化");
        MarkedUtil.addMark(9002, "解析返回数据出错");
        MarkedUtil.addMark(9003, "上传文件出错");
        MarkedUtil.addMark(9004, "文件上传失败");
        MarkedUtil.addMark(9005, "批量操作只支持最多50条");
        MarkedUtil.addMark(9006, "objectId为空");
        MarkedUtil.addMark(9007, "文件大小超过10M");
        MarkedUtil.addMark(9008, "上传文件不存在");
        MarkedUtil.addMark(9009, "没有缓存数据");
        MarkedUtil.addMark(9010, "网络超时");
        MarkedUtil.addMark(9011, "BmobUser类不支持批量操作");
        MarkedUtil.addMark(9012, "上下文为空");
        MarkedUtil.addMark(9013, "BmobObject（数据表名称）格式不正确");
        MarkedUtil.addMark(9014, "第三方账号授权失败");
        MarkedUtil.addMark(9015, "其他错误均返回此code");
        MarkedUtil.addMark(9016, "无网络连接，请检查您的手机网络。");
        MarkedUtil.addMark(9017, "与第三方登录有关的错误，具体请看对应的错误描述");
        MarkedUtil.addMark(9018, "参数不能为空");
        MarkedUtil.addMark(9019, "格式不正确：手机号码、邮箱地址、验证码");
        MarkedUtil.addMark(10010, "该手机号发送短信达到限制");
        MarkedUtil.addMark(10011, "该账户无可用的发送短信条数");
        MarkedUtil.addMark(10012, "身份信息必须审核通过才能使用该功能");
        MarkedUtil.addMark(10013, "非法短信内容");
    }
}
