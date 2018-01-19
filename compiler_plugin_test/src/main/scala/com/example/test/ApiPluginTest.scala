package com.example.test

import com.example.{ExternalApi, SpecialElement}

object ApiPluginTest extends App {

  val targetList = ExternalApi.create(Blah("name"))

  ExternalApi.create2(ExternalApi.create1(Blah("sth")).elem)

  val t = {

    val sub1 = ExternalApi.create(Blah("anything"))

    val sub2 = ExternalApi.create1(sub1.elem)

    sub2
  }

}

case class Blah(name: String) extends SpecialElement
