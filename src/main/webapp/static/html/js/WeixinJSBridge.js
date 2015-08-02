document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    window.shareData = {
        "timeLineLink": "http://www.baidu.com",
        "sendFriendLink": "http://www.taobao.com",
        "weiboLink": "http://www.baidu.com",
        "tTitle": "大鹏送煎饼啦",
        "tContent": "拯救不开心，拯救不赚钱！大鹏送煎饼啦，来赢《煎饼侠》电影票吧！",
        "fTitle": "大鹏送煎饼啦",
        "fContent": "拯救不开心，拯救不赚钱！大鹏送煎饼啦，来赢《煎饼侠》电影票吧！",
        "wContent": "拯救不开心，拯救不赚钱！大鹏送煎饼啦，来赢《煎饼侠》电影票吧！"
    };
    // 发送给好友
    WeixinJSBridge.on('menu:share:appmessage', function (argv) {
        WeixinJSBridge.invoke('sendAppMessage', {
            "img_url": "https://www.baidu.com/img/baidu_jgylogo3.gif",
            "img_width": "401",
            "img_height": "275",
            "link": window.shareData.sendFriendLink,
            "desc": window.shareData.fContent,
            "title": window.shareData.fTitle
        }, function (res) {
            _report('send_msg', res.err_msg);
        })
    });
    // 分享到朋友圈
    WeixinJSBridge.on('menu:share:timeline', function (argv) {
        WeixinJSBridge.invoke('shareTimeline', {
            "img_url": "https://www.baidu.com/img/baidu_jgylogo3.gif",
            "img_width": "401",
            "img_height": "275",
            "link": window.shareData.timeLineLink,
            "desc": window.shareData.tContent,
            "title": window.shareData.tTitle
        }, function (res) {
            _report('timeline', res.err_msg);
        });
    });

}, false)