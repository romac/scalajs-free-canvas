package me.romac
package freecanvas
package test

import cats._
import cats.std.all._
import cats.syntax.all._

import scala.scalajs.js.JSApp
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.document

final class Color(val name: String) extends AnyVal

object Color {
  val Red   = new Color("red")
  val Green = new Color("green")
  val Blue  = new Color("blue")
}

final case class Point(name: String, x: Double, y: Double, color: Color, isCentroid: Boolean = false) {
  def dist(other: Point) =
    Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2))
}

object Main extends JSApp {

  import me.romac.freecanvas.{ Canvas => C, Graphics => G }

  import Color._

  val points = List(
    Point("A",  2, 10, Red, true),
    Point("A1", 2, 10, Red),
    Point("A2", 2, 5,  Red),
    Point("A3", 8, 4,  Red),
    Point("B",  5, 8,  Blue, true),
    Point("B1", 5, 8,  Blue),
    Point("B2", 7, 5,  Blue),
    Point("B3", 6, 4,  Blue),
    Point("C",  1, 2,  Green, true),
    Point("C1", 1, 2,  Green),
    Point("C2", 4, 9,  Green)
  )

  def mean(values: Seq[Double]): Double =
    values.foldLeft(0.0)(_ + _) / values.length

  def kMeans(points: List[Point]): (List[Point], List[Point]) = {
    val (centroids, regulars) = points.partition(_.isCentroid)

    val newCentroids = centroids.map { c =>
      val points = regulars.filter(_.color == c.color)
      val meanX  = mean(points.map(_.x))
      val meanY  = mean(points.map(_.y))

      c.copy(x = meanX, y = meanY)
    }

    val newPoints = regulars map { p =>
      val distances = newCentroids
        .map { c =>
          (c, c.dist(p))
        }
        .sortWith(_._2 < _._2)

      val (closest, dist) = distances.head

      p.copy(color = closest.color)
    }

    (newCentroids, newPoints)
  }

  val SCALE = 50
  val SIZE  = 10

  def drawPoint(point: Point): G.Graphics[Unit] = point match {
    case Point(_, x, y, color, true) =>
      val rect = C.Rectangle(x * SCALE, y * SCALE, SIZE, SIZE)

      for {
        _ <- G.setFillStyle(color.name)
        _ <- G.fillRect(rect)
      }
      yield ()

    case Point(_, x, y, color, false) =>
      val arc = C.Arc(
        point.x * SCALE + SIZE / 2,
        point.y * SCALE + SIZE / 2,
        SIZE / 2,
        0,
        Math.PI * 2
      )

      for {
        _ <- G.setFillStyle(color.name)
        _ <- G.beginPath
        _ <- G.arc(arc)
        _ <- G.fill
        _ <- G.closePath
      }
      yield ()
  }

  def draw(points: List[Point]): G.Graphics[Unit] = for {
    _ <- G.clearRect(C.Rectangle(0, 0, 800, 600))
    _ <- points.map(drawPoint).sequence_
  } yield ()

  def loop(points: List[Point], centroids: List[Point]): G.Graphics[Unit] = for {
    _ <- draw(points ++ centroids)
    (newPoints, newCentroids) = kMeans(points ++ centroids)
    _ <- G.setTimeout(draw(points ++ newCentroids), 1000)
    _ <- G.setTimeout(loop(newPoints, newCentroids), 2000)
  } yield ()

  def main(): Unit = {
    val canvas = document.getElementById("canvas").asInstanceOf[html.Canvas]
    val ctx    = canvas.getContext("2d").asInstanceOf[C.Context2D]

    val (centroids, regulars) = points.partition(_.isCentroid)
    val prog = loop(regulars, centroids)

    G.run(ctx)(prog)
  }

}

