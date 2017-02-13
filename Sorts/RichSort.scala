package sort

/**
  * Created by yulin on 2/10/17.
  */
object RichSort {

  //插入排序就是每一步都将一个待排数据按其大小插入到已经排序的数据中的适当位置，直到全部插入完毕。

  def isort(xs: List[Int]): List[Int] = xs match {
    case List()   => List()
    case x :: xs1 => insert(x, isort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List()  => List(x)
    case y :: ys => if (x <= y) x :: xs else y :: insert(x, ys)
  }

  //快速排序通过一趟扫描将要排序的数据分割成独立的两部分,其中一部分的所有数据都比另外一部分的所有数据都要小,
  // 然后再按此方法对这两部分数据分别进行快速排序,整个排序过程可以递归进行,以此达到整个数据变成有序序列

  def quicksort[T](less: (T, T) => Boolean)(xs: List[T]):List[T]= xs match
  {
    case List() => List()
    case y::ys =>
      quicksort(less)(ys.filter( a => less(a, y)))::: List(y) ::: quicksort(less)(ys.filter( b => !less(b, y)))
  }


  //把原始数组分成若干子数组,对每一个子数组进行排序,
  //继续把子数组与子数组合并,合并后仍然有序,直到全部合并完,形成有序的数组

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {

    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

}
