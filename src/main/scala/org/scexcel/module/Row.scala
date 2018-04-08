package org.scexcel.module

/** Provide Row[A] collection for representing Excel rows */

import collection.{IndexedSeqLike, mutable}
import collection.mutable.Builder
import collection.generic.CanBuildFrom
import scala.collection.immutable.VectorBuilder
/** A Representation of an Excel row
  *
  * @constructor create a row with a Vector[A]
  * @param elements
  * @tparam A
  */

case class Row[+A]  (val elements: Vector[A]) extends IndexedSeq[A] with IndexedSeqLike[A, Row[A]]  {

  import Row._


  /*importing the methods of the companion object*/
  /*compulsory definition of length method*/
  def length: Int=elements.length
  /*compulsory definition of the apply method*/
  def apply(idx: Int): A={
    if (idx < 0 || length <= idx) throw new IndexOutOfBoundsException
    else elements(idx)
  }
  /* Mandatory reimplementation of the NewBuilder in indexedSeq*/
  override  protected[this] def newBuilder: mutable.Builder[A,Row[A]]=Row.newBuilder

}
/** Factory for [[org.scexcel.module.Row]] instance*/

object Row{

  def fromSeq[A](buff: Vector[A]): Row[A]=new Row(buff)
  def apply[A](elem:A*)=fromSeq(elem.toVector)
  def newBuilder[A]: mutable.Builder[A,Row[A]]= new VectorBuilder mapResult fromSeq

  implicit def canBuildFrom[A]: CanBuildFrom[Row[A],A,Row[A]]={
    new CanBuildFrom[Row[A],A,Row[A]]{
      def apply(): Builder[A,Row[A]]=newBuilder
      def apply(from: Row[A]):Builder[A,Row[A]]=newBuilder
    }
  }

}
