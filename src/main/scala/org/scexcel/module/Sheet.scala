package org.scexcel.module

/** Provide Sheet collection for representing Excel sheet */


import collection.{IndexedSeqLike, mutable}
import collection.mutable.Builder
import collection.generic.CanBuildFrom
import scala.collection.immutable.VectorBuilder

/** A Representation of an Excel sheet
  *
  * @constructor create a sheet with a Vector[Row[A]
  * @param elements
  * @tparam A
  */

case class Sheet[+A]  (val elements: Vector[Row[A]]) extends IndexedSeq[Row[A]] with IndexedSeqLike[Row[A], Sheet[A]] {

  import Sheet._

  override protected[this] def newBuilder: Builder[Row[A],Sheet[A]]= Sheet.newBuilder

  def length :Int= elements.length
  def apply(idx:Int):Row[A]=if (idx <0 || idx >length) throw new IndexOutOfBoundsException else elements(idx)
  def apply(column: Int,row:Int):A= elements(column)(row)
  def apply(seq:Vector[(Int,Int)]): Vector[A] = (for (e<-seq) yield this.apply(e._1,e._2)).toVector



  def getIndicesOf[A](keys: A*): Vector[Option[(Int,Int)]]={
     val indices: Seq[Option[(Int, Int)]] = for(k<-keys; e<-this.elements) yield {
      if (e.contains(k)) Some((this.elements.indexOf(e),e.indexOf(k) ))
      else None
    }
    indices.toVector
  }
}


/** Factory for [[org.scexcel.module.Sheet]] instance*/
object Sheet{
  def fromSeq[A](buff:Vector[Row[A]]):Sheet[A]=new Sheet(buff)
  def apply[A](s:Row[A]*)=fromSeq(s.toVector)
  def newBuilder[A]: mutable.Builder[Row[A],Sheet[A]]= new VectorBuilder mapResult fromSeq


  implicit def canBuildFrom[A]: CanBuildFrom[Sheet[A],Row[A],Sheet[A]]={
    new CanBuildFrom[Sheet[A],Row[A],Sheet[A]] {
      def apply(): Builder[Row[A], Sheet[A]] = newBuilder
      def apply(from: Sheet[A]): Builder[Row[A], Sheet[A]] = newBuilder
    }
  }


}