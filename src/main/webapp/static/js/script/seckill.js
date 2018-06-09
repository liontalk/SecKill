// 主要处理交互逻辑代码
// javascript 代码的模块化
// seckill.detail.init(param);
var seckill = {

    //封装ajax请求的js代码
    URL: {
        now: function () {
            return "/seckill/time/now";
        },
        exposer: function (seckillID) {
            return "/seckill/" + seckillID + "/exposer";
        },
        killUrl: function (seckillID, md5) {
            return "/seckill/" + seckillID + "/" + md5 + "/execute";
        }

    },
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    startSecKill: function (seckillId, seckillBox) {
        seckillBox.hide()
            .html("<button type='button' class='btn btn-primary btn-lg' id='killButton'>开始秒杀</button>");
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            // 在回调函数中，执行交互流程
            if (result && result.success) {
                var exposer = result.data;
                if (exposer.exposed) {
                    //秒杀开始
                    //获取秒杀地址
                    var md5String = exposer.md5;
                    var killUrl = seckill.URL.killUrl(seckillId,md5String);
                    //绑定一次点击，
                    $("#killButton").one('click', function () {
                        //绑定执行秒杀的操作
                        // 禁用按钮
                        console.log("执行秒杀。。。。。");
                        $(this).addClass("disabled");
                        // 发送秒杀执行的请求地址，执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result.success) {
                                var seckillReuslt = result.data;
                                var status = seckillReuslt.status;
                                var statusInfo = seckillReuslt.statusInfo;
                                // 显示用户秒杀的结果
                                seckillBox.html("<span class='label label-success'>"+statusInfo+"</span>");
                            }
                        })
                    });
                    seckillBox.show();
                } else {
                    //秒杀没有开始
                    var now = exposer.now;
                    var start = exposer.start;
                    var end = exposer.end;
                    // 重新计算秒杀时间
                    seckill.countdown(seckillId, now, start, end);
                }
            }
        });
    },
    countDown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            console.log("秒杀结束！");
            seckillBox.html('秒杀结束!');
        } else if (nowTime < startTime) {
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on("finish.countdown", function () {
                // 时间完成之后的事件
                // 获取秒杀地址 ，控制实现逻辑，执行秒杀
                seckill.startSecKill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            console.log("秒杀开始");
            seckill.startSecKill(seckillId, seckillBox);
        }
    },
    // 详情页的秒杀逻辑
    detail: {
        // 详情页面的初始化

        init: function (params) {
            var seckillId = params.seckillId;
            var startTime = params.startTime;
            var endTime = params.endTime;
            // 用户手机验证和交互
            var killPhone = $.cookie('killPhone');
            //验证手机号码
            if (!seckill.validatePhone(killPhone)) {
                // 绑定手机号
                // 控制输出
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,
                    backdrop: 'static',//禁止位置关闭
                    keybord: false,//关闭键盘事件
                });

                $("#killPhoneBtn").click(function () {
                    var phone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(phone)) {
                        //电话写入到cookies
                        $.cookie('killPhone', phone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号输入错误!</label>').show(300);
                    }
                });
            }

            //用户已经登录，倒计时数据
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result.success) {
                    var nowTime = result.data;
                    seckill.countDown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log("result=" + result);
                }
            })
        }
    },
}