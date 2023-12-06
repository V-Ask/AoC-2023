file:///C:/Users/vict2/Documents/Advent-Of-Code-2023/Day6%20(Scala)/hello-world/src/main/scala/Main.scala
### scala.reflect.internal.FatalError: no context found for source-file:///C:/Users/vict2/Documents/Advent-Of-Code-2023/Day6%20(Scala)/hello-world/src/main/scala/Main.scala,line-1,offset=19

occurred in the presentation compiler.

action parameters:
offset: 19
uri: file:///C:/Users/vict2/Documents/Advent-Of-Code-2023/Day6%20(Scala)/hello-world/src/main/scala/Main.scala
text:
```scala
def d(a: Int) = a *@@Ø

object Main extends App {
  println("Hello, World!")
}
```



#### Error stacktrace:

```
scala.tools.nsc.interactive.CompilerControl.$anonfun$doLocateContext$1(CompilerControl.scala:100)
	scala.tools.nsc.interactive.CompilerControl.doLocateContext(CompilerControl.scala:100)
	scala.tools.nsc.interactive.CompilerControl.doLocateContext$(CompilerControl.scala:99)
	scala.tools.nsc.interactive.Global.doLocateContext(Global.scala:114)
	scala.meta.internal.pc.PcDefinitionProvider.definitionTypedTreeAt(PcDefinitionProvider.scala:151)
	scala.meta.internal.pc.PcDefinitionProvider.definition(PcDefinitionProvider.scala:68)
	scala.meta.internal.pc.PcDefinitionProvider.definition(PcDefinitionProvider.scala:16)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$definition$1(ScalaPresentationCompiler.scala:321)
```
#### Short summary: 

scala.reflect.internal.FatalError: no context found for source-file:///C:/Users/vict2/Documents/Advent-Of-Code-2023/Day6%20(Scala)/hello-world/src/main/scala/Main.scala,line-1,offset=19