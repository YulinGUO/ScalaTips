class RichTraversable[K,V](ite: Iterable[(K,V)])  {

  def reduceByKey()(implicit num: Numeric[V]):Iterable[(K,V)]= {
    import num._

    ite.groupBy(_._1)
      .map { case (group: K, traversable) => traversable.reduce{(a,b) => (a._1, a._2 + b._2)} }
      .toList
  }
}

object RichTraversable{

  implicit def iteToRichIte[K,V](rdd: Iterable[(K, V)]):RichTraversable[K,V]= {
    new RichTraversable[K,V](rdd)
  }

}
