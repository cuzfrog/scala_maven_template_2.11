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

