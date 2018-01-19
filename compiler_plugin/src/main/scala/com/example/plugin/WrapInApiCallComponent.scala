package com.example.plugin

import com.example.{SpecialElement, TypeConstructor}

import scala.tools.nsc.Global
import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.Transform

class WrapInApiCallComponent(val global: Global)
    extends PluginComponent
    with Transform {
  protected def newTransformer(unit: global.CompilationUnit) =
    WrapInApiCallTransformer

  val runsAfter: List[String] = List("typer") //since we need the type
  val phaseName: String       = WrapInApiCallComponent.Name

  import global._

  object WrapInApiCallTransformer extends Transformer {
    override def transform(tree: global.Tree) = {
      val transformed = super.transform(tree)
      transformed match {
        case call @ Apply(_, _) =>
          if (call.tpe != null && call.tpe.finalResultType <:< typeOf[
                TypeConstructor[_ <: SpecialElement]]) {
            println(s"Found relevant call $call")

            val typeArguments = call.tpe.typeArgs.map(_.toString).toList

            val listSymbOf = symbolOf[List.type]
            val wrappedFuncSecondArgument =
              q"$listSymbOf.apply(..$typeArguments)"

            val apiObjSymbol = symbolOf[com.example.MyApi.type]

            val wrappedCall =
              q"$apiObjSymbol.process[${call.tpe.finalResultType}]($call, $wrappedFuncSecondArgument)"

            //explicit typing, otherwise later phases throw NPEs etc.
            val ret = typer.typed(wrappedCall)
            println(showRaw(ret))
            println("----")
            ret
          } else {
            call
          }
        case _ => transformed
      }
    }
  }
}

object WrapInApiCallComponent {
  val Name = "api_embed_component"
}
