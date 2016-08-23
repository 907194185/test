function mygeolocation(){
	alert("geolocation");
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(locationSuccess, locationError,{
			// 指示浏览器获取高精度的位置，默认为false
			enableHighAccuracy: true,
			// 指定获取地理位置的超时时间，默认不限时，单位为毫秒
			timeout: 5000,
			// 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置。
			maximumAge: 3000
		});
	}else{
		alert("Your browser does not support Geolocation!");
	}
	
};


locationError=function(error){
    switch(error.code) {
        case error.TIMEOUT:
            alert("A timeout occured! Please try again!");
            break;
        case error.POSITION_UNAVAILABLE:
        	alert('We can\'t detect your location. Sorry!');
            break;
        case error.PERMISSION_DENIED:
        	alert('Please allow geolocation access for this to work.');
            break;
        case error.UNKNOWN_ERROR:
        	alert('An unknown error occured!');
            break;
    }
};

locationSuccess=function(position){
    var coords = position.coords;   
    alert(coords.latitude+","+coords.longitude);
    var map = new BMap.Map("map");    // 创建Map实例
    var point = new BMap.Point(coords.longitude,coords.latitude);
	map.centerAndZoom(point, 12);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	//map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	var mk = new BMap.Marker(point);
	map.addOverlay(mk);
	map.panTo(point);
    /*var latlng = new qq.maps.LatLng(
        // 维度
        coords.latitude,
        // 精度
        coords.longitude
    );  
    var myOptions = {  
        // 地图放大倍数  
        zoom: 12,  
        // 地图中心设为指定坐标点  
        center: latlng,  
        // 地图类型  
        mapTypeId: qq.maps.MapTypeId.ROADMAP  
    };  
    // 创建地图并输出到页面  
    var myMap = new qq.maps.Map(  
        document.getElementById("map"),myOptions  
    );  
    // 创建标记  
    var marker = new qq.maps.Marker({  
        // 标注指定的经纬度坐标点  
        position: latlng,  
        // 指定用于标注的地图  
        map: myMap
    });
    //创建标注窗口  
    var infowindow = new qq.maps.InfoWindow({  
        content:"您在这里<br/>纬度："+  
            coords.latitude+  
            "<br/>经度："+coords.longitude  
    });  
    //打开标注窗口  
    infowindow.open(myMap,marker); */
};