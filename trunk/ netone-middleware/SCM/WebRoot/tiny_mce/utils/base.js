/***
 *	Base JavaScript
 **/
var basejs = {
	base: "/ndyd"
};

if(!window.XMLHttpRequest) {

	// 解决IE6透明PNG图片BUG
	DD_belatedPNG.fix(".png");
	
	// 解决IE6不缓存背景图片问题
	document.execCommand("BackgroundImageCache", false, true);

}

