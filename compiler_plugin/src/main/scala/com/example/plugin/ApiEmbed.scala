package com.example.plugin

import scala.tools.nsc.Global
import scala.tools.nsc.plugins.{Plugin, PluginComponent}

class ApiEmbed(val global: Global) extends Plugin {
  val name: String                      = "apiembed"
  val components: List[PluginComponent] = List(new WrapInApiCallComponent(global))
  val description: String               = "embed api call"
}
