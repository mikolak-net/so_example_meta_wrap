package com.example

object ExternalApi {

  def create[T <: SpecialElement](elem: T): TypeConstructor[T] =
    TypeConstructor(elem)

  def create1[T <: SpecialElement](elem: T): TypeConstructor[T] =
    TypeConstructor(elem)

  def create2[T <: SpecialElement](elem: T): TypeConstructor[T] =
    TypeConstructor(elem)
  //...

}

object MyApi {

  def process[T <: TypeConstructor[_ <: SpecialElement]](
      l: T,
      metadata: List[String]): T = {
    println("I've been called!")
    //do some interesting stuff with the List's type parameter here
    l
  }

}

case class TypeConstructor[E](elem: E)

trait SpecialElement
