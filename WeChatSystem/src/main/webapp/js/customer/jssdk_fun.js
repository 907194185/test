$(function() {
	mygeolocation();
	$("#scan").bind("click", function() {
		wx.scanQRCode({
			needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
			success : function(res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				alert(result);
			}
		});
	});

	$("#getLocation").bind("click", function() {
		wx.getLocation({
			type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				var speed = res.speed; // 速度，以米/每秒计
				var accuracy = res.accuracy; // 位置精度
				showMap(latitude, longitude);
			}
		});
	});

	function showMap(latitude, longitude) {
		wx.openLocation({
			latitude : latitude, // 纬度，浮点数，范围为90 ~ -90
			longitude : longitude, // 经度，浮点数，范围为180 ~ -180。
			name : '无名', // 位置名
			address : '广东省佛山市', // 地址详情说明
			scale : 20, // 地图缩放级别,整形值,范围从1~28。默认为最大
			infoUrl : '' // 在查看位置界面底部显示的超链接,可点击跳转
		});
	}

	$("#chooseImage").bind("click", function() {
		wx.chooseImage({
			count : 9, // 默认9
			sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
			sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
			success : function(res) {
				var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				alert(localIds);
				$("#img").attr("src", localIds[0]);
			}
		});
	});

	$("#network").bind("click", function() {
		wx.getNetworkType({
			success : function(res) {
				var networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
				alert("您正在使用：" + networkType);
			}
		});
	});
	$("#close").bind("click", function() {
		wx.closeWindow();
	});

	var localId;
	$("#startRecord").bind("click", function() {
		wx.startRecord();
	});
	$("#stopRecord").bind("click", function() {
		wx.stopRecord({
			success : function(res) {
				localId = res.localId;
			}
		});
	});
	$("#playRecord").bind("click", function() {
		wx.playVoice({
			localId : localId // 需要播放的音频的本地ID，由stopRecord接口获得
		});
	});
	$("#hideOptions").bind("click", function() {
		wx.hideOptionMenu();
	});
	$("#showOptions").bind("click", function() {
		wx.showOptionMenu();
	});

});