#How to pimp my lib

##1.如何使用RichTraversable 

在richTraversable增加了reduceByKey的功能。  

```
import RichTraversable._
//比如为[(K,V)]的形式  
val a = List((1,2),(1,2),(2,3)).reduceByKey()
```

##2.实现原理  
scala原生代码中, List等继承了Traversable接口的类，并没有reduceByKey的函数。当然也可以写一个reduceByKey函数，传入list,返回结果。但是这样就没法pipe操作一个list了。  

做法如下：  
首先定义一个RichTraversable函数类，参数为ite: Iterable[(K,V)],实际上也定义了一个构造器。当然，也定义了一个ite的属性。   

```
class RichTraversable[K,V](ite: Iterable[(K,V)])  {

  def reduceByKey()(implicit num: Numeric[V]):Iterable[(K,V)]= {
    import num._

    ite.groupBy(_._1)
      .map { case (group: K, traversable) => traversable.reduce{(a,b) => (a._1, a._2 + b._2)} }
      .toList
  }
}
```
RichTraversable类中定义了reduceByKey的函数，也就是说，如果list能够转化为RichTraversable，就可以直接使用reduceByKey这个函数了。  
这里我们使用隐式函数，list能够隐式转变为一个RichT。  

```
object RichTraversable{
  implicit def iteToRichIte[K,V](rdd: Iterable[(K, V)]):RichTraversable[K,V]= {
    new RichTraversable[K,V](rdd)
  }
}
```
##3.参考
大神自己的文章 <http://www.artima.com/weblogs/viewpost.jsp?thread=179766>