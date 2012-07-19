DocFrame 工程主要是针对 文档的处理
其中支持4类的文档处理  1：pdf  ，2：txt，3：excel，4：html泛形 （Word/ excel 其本质是将其他的文档 转化为等效的html然后加入内部的文档标签）
主要有3种 $var{columnid}取单个变量，$vars{columnid} 针对数组中从0开始依次取变量，个数由实际个数决定，如果填写的vars的个数 超出 数据的上届，那么
超出部分显示的结果为空，$loops{ $var{columnid}}  循环显示，注意如果在 表格中，可以自动生成新的行

所有的例子代码 在 test 目录中的 DocDemo.java 类里，关于Html泛形的例子在目录 Sample中


html泛形 在使用中需要预先支持模板 ，制作方式是用word或者Excel 定义外观，并且在其中嵌入$变量,然后另存为.htm文件后即可完成模板制作