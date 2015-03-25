![image](http://img.hb.aicdn.com/9ed1767a2c2f192f5a5c163427eef415c9fea61d863b-ZnhCjX_fw658)
# cell
**cell** 是一套基于 nginx 实现 url 按业务规则路由进行站点拆分的解决方案。



### 名字原由

取名 **cell** 意指源于同一母体的站点，就像**细胞分裂**（cell division）一样不断分化独立，不同 BU 分拆定制自己的站点，却又服务着共同的业务。



### 项目背景

在公司达到一定的体量之后，为了保证业务的不断创新和快速发展，如何构建灵活自主的组织架构是每个公司必须去解决的问题。采用事业群模式，替换原本简单的按职能划分，是很多互联网企业的最终选择，如大众点评、58同城。

在公司组织架构调整的时候，技术架构如果无法跟上，便会带来一些问题。

如点评有酒店、结婚、玩乐等多个事业群，在他们需要对各自的商户页进行定制开发的时候，他们往往希望能各自开发维护自己的独立站点，但由于 url 的一致性（如下），无法从 slb 进行区分，进而进行路由分发。代码只能都绑死在一个站点内，既带来了开发上线交叉合作的复杂性，也不利于系统运行的安全稳定。

【美食商户页】[http://www.dianping.com/shop/15909459](http://www.dianping.com/shop/15909459)
![image](http://img.hb.aicdn.com/cfea63bd698fda1c502f2b4b7a1ac48a4a00c2d55a779-CtBCJp_fw658)

【酒店商户页】[http://www.dianping.com/shop/2412679](http://www.dianping.com/shop/2412679)
![image](http://img.hb.aicdn.com/2bd3e80a80302b1092a686a3e3c6fb95e3fd894a64890-b5RUTx_fw658)

【结婚商户页】[http://www.dianping.com/shop/3589714](http://www.dianping.com/shop/3589714)
![image](http://img.hb.aicdn.com/869009703c17bd8450f83148c1e5be3e586d6eee93ca1-PjpL6T_fw658)

如上面 /shop/+d 这型的 url ，单纯从正则匹配的角度 slb 无法进行路由区分，因而也就无法进行站点拆分了。**cell** 这套解决方案正是为了解决这个问题。 

