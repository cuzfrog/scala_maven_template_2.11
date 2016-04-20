# scala_maven_template_2.11
##Scala points:


####1.val initialization order:
http://docs.scala-lang.org/tutorials/FAQ/initialization-order.html

when override hashCode, make sure every val involved is not null.

####2.pitfall of mapValues and filterNot etc.
https://issues.scala-lang.org/browse/SI-4776

some methods do not create new map, but instead, create a MapView

####3.disable Mylyn in eclipse to speed up scala compiling.

simply enter Preferences-> Mylyn  disable any Auto*

####4.lazy argument

f(x: =>B):C   now, B is something like lazy val a=(expensive-like fun)

####5.Enum

a.use case object as enumeration ,well a little more verbose.

b.override Enumeration http://stackoverflow.com/questions/2507273/overriding-scala-enumeration-value  so you can inherit your own Value creation method in subclass or inner-class

####6.evaluation style
```scala
//BAD: 
val a={
  val b=expensiveFunction  //temperary val
  b
} //everytime access a, b is calculated
//GOOD:(unchecked20160301)
def fun={
  val b=expensiveFunction  //temperary val
  b
}
val a=fun
```

```scala
//BAD:
val fun=expensiveFunction.cheapFunction(_:T)  //everytime access fun, both functions are calculated
//GOOD:
val result=expensiveFunction
val fun=result.cheapFunction(_:T)
```
####7.use .view properly
```scala
import com.typesafe.scalalogging.LazyLogging

object TempTest extends App with LazyLogging {
  def timer(f: () => Any): Unit = {
    val time1 = System.currentTimeMillis()
    f()
    val time2 = System.currentTimeMillis()
    logger.info(f.toString + " done, time consumed:" + (time2 - time1))
  }

  val f1 = () => {
    r(List(1, 2, 3, 4, 5),1000)
  }
  val f2 = () => {
    r(List(1, 2, 3, 4, 5).view,1000).view.force
  }
  
  def r(l: Seq[Int], n: Int):Seq[Int] = {
    if (n == 0) l
    else r(l.map(_ + 1), n - 1)
  }
  
  timer(f1)
  timer(f2)
}
```
f2 is faster than f1, but, if you change run times from 1000 to 10000, it will throw overflow exception

####8.if procedure control
```scala
//BAD:
if(condition1)
  val a=x
else
  val a=y
  
//GOOD:
val a=if(condition1) x else y
```

####9.package sealed trait
```scala
package foo
sealed trait I
private[foo] trait I2 extends I
```
trait I2 can be and only can be implemented within package foo

####10.abuse of closure
```scala
val map1:Map[Key1,Map[Key2,Value]]=someMap1
val map2:Map[Key1,Map[Key2,Function]]=someMap2

val mapWithProcessedValue=map1.map{
  e1=>
    (e1._1,e1._2.map{
      e2=>
        val fun=map2(e1._1)(e2._1) //Bad, but not worst
        (e2._1,fun(e2._2)) //apply fun to value
    })
}

val funToFind=map2(_:Key1)(_:Key2)
val mapWithProcessedValue=map1.map{
  e1=>
    val partialFun=funToFind(e1.1)_
    (e1._1,e1._2.map{
      e2=>
        val fun=partialFun(e2._1) //Now, you suppose that this be faster, in reality, this is the slowest
        (e2._1,fun(e2._2)) //apply fun to value
    })
}

val mapWithProcessedValue=map1.map{
  e1=>
    val subMap2=map2(e1.1)
    (e1._1,e1._2.map{
      e2=>
        val fun=subMap2(e2._1) //Good.
        (e2._1,fun(e2._2)) //apply fun to value
    })
}
```



