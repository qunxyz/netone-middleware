页面是基于资源模型来应用的,对于页面资源的表达模式主要是路径,其中parentid可以定位路径,pagePath就是真实的逻辑路径
访问第一个页面First.jsp的时候必须输入parentid 与 页路径 pagePath
/portal/flowpage/First.jsp?parentid=xxx&pagepath=abc.efg.xxx

该应用中为了能实现不同页面间的记忆能力,用jstl的标签的形式在所有的流程页面中保持表单字段一致