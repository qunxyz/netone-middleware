## 2011年1月份收集的问题日志 ##
  * 工作流相关
  1. 工作流RMI 改良 增加部分方法，对应的RMI服务和相关工程中的rmi客户端要更新  fix
  1. 提供了工作流扩展工程wfweb-ext 移植ISS中的代办任务应用，wfweb-ext 作为扩展工程将一起默认发布到webser工程中，并且在cmsweb的首页中集成，在二次开发标签文档中加入 wfweb-ext的引用[二次开发标签文档](http://netone-middleware.googlecode.com/files/NETONE_DEV_TAG.zip)  fix
  1. 工作流提供子流程调度时自动复制父流程的相关变量，复制原则为如果子流程的某相关变量的字段名与父流程雷同，那么就自动复制
  1. 相关变量编辑窗口中文保存会乱码
  1. 流程可视化监控图，提交节点后无法自动刷新
  1. 工作流的子流程要实现自动调度包括 同步 和异步模式，同步模式子流程结束后自动提交父流程，异步模式要直接提交父节点，或许这个功能后期通过配置来解决
  1. 工作流Route节点和子流程 不要鉴权，所以无需创建资源节点
  * PORTAL相关
    1. Portal的小窗口默认有缓存，要处理下，用户有时没有选择用cache却自动有了cache ，用户需要自己重新编辑保持下小窗口方可生效
    1. portal中集成工作流代办任务
    1. portal 将全部用RMI来连接，不在web工程中提供数据源配置
    1. Portal中需要提供鉴权功能，控制门户也可见和不可见，这样可以提供门户页的共享度和通用性
  1. portal支持自动适应外部样式，通过cookies进行交互，在DRP的做法参考
```
setCookie("extTheme", extTheme, a, url);
setCookie("appTheme", appTheme, a, url);
setCookie("mainTheme", mainTheme, a, url);
```
  1. 修订Portal的头链接的样式显示缺陷 fix
  1. portal.do的参数需要支持 naturalname传递数据
  * 其他
    1. 中间件首页的布局和功能说明加以修订  fix
    1. 应用资源根目录，原先只允许管理员访问，现在开放授权处理，需要用户授予BUSSENV.BUSSENV.SECURITY.ROLE资源 操作的权限即可。修改需要更新所有的客户端oecSpeciTag.jar包  fix
    1. 页框管理和展示有些问题 需要处理  fix
    1. 提交RMI和TAG 的 DEMO工程 Ok Done
    1. 所有的localhost 的要配置127.0.0.1 或者 用统一域名netone.oesee.org.包括数据库连接和RMI
    1. 或许需要提供ormer 持久层的RMI连接 Ok Done 在工作流的rmi中提供
    1. 很多客户端包不统一 主要包括：安全客户端、环境、资源、webcache、工作流、表单、bi、portal
    1. 发布包的 安全插件包 不完整丢失很多公共类，需要补上 fix