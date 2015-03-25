![image](http://img.hb.aicdn.com/9ed1767a2c2f192f5a5c163427eef415c9fea61d863b-ZnhCjX_fw658)
# cell
**cell** 是一套基于 nginx 实现 url 按业务规则路由进行站点拆分的解决方案。



### 名字原由

取名 **cell** 意指源于同一母体的站点，就像**细胞分裂**（cell division）一样不断分化独立，不同 BU 分拆定制自己的站点，却又可以服务着共同的业务。



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



### 解决方案

![image](http://img.hb.aicdn.com/31bf7e5848d4ee7cb0b3a4dde02df329f8e44d08c037-73eRzB_fw658)

为了解决 slb 正则匹配无法进行 url 识别的问题，为 nginx 编写一个 lua 脚本实现的模块。在匹配到 /shop/+d 这型 url 的时候，该脚本会解析出 url 中的 shopId，然后去访问 redis 查询该商户的类型，根据获取到的类型路由到对应的应用站点。


####1) redis 配置
如点评 M站 拆分的 redis 配置方案：

key :  

	mobile:wap:m:web:shop:+d (如 mobile:wap:m:web:shop:15909459 ~ http://m.dianping.com/shop/15909459) 
 
value :  

	main  | shopping | hotel | wedding | backup

value 站点映射 :

	main  --> mobile-m-shop-web  （新版 M 站）
    shopping  --> m-shopping-web （购物 M 站）
    hotel  --> m-hotel-web       （酒店 M 站）
    wedding  --> m-wedding-web   （结婚 M 站）
    backup --> mobile-wap-m-web  （旧版 M 站）
	
	
	
####2) cell 详解 [ policy+listener+checker ]

* **policy** 是由参与分拆的各个事业部共同协商制定的一份准则，一型 url 对应一份 **policy**， 该 **policy** 将根据 url 中的 shopId 裁决该 url 属于哪个事业部类型，并路由到其对应站点。
		
		public interface Policy {

    		public Type judge(int shopId);

		}


* **listener** 监听所有的 shop 变更，任意商户一旦有任何的改动，该 listener 便会获取其 shopId ，交由所有 **policy** 裁判，并将裁决结果更新到 redis 。

* **checker** 是对 **listener** 的补充，以防有任何的消息遗漏。**checker** 可以是定时增量检查，也可以是全量检查。 

	

    