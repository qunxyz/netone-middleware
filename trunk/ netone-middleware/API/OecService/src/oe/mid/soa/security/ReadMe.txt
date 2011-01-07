参考3A安全认证体系的应用,在这里我们本需要提供分别针对3A, Authentication,Authoristion,Auditing这三类
的接口服务. 不过处于应用价值的考虑,在普遍的安全应用中 Authoristion 是比较灵活多变的,通常是将
Authentication+ Auditing + Resource 三者综合起来实现的,所以在这里我们针对 3A 安全体系
提供的三类服务中 没有 Authoristion 的接口,但是提供了其元接口
Auditing,Authentication 和 resource

我们在 Authentication