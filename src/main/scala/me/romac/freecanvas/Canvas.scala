
package me.romac
package freecanvas

import org.scalajs.dom

object Canvas {

  type Context2D = dom.CanvasRenderingContext2D

  sealed trait LineCap
  sealed trait Composite
  sealed trait Transform
  sealed trait TextAlign
  sealed trait TextMetrics
  sealed trait ImageData
  sealed trait ImageSource

  case class Arc(x: Double, y: Double, radius: Double, startAngle: Double, endAngle: Double)
  case class Rectangle(x: Double, y: Double, w: Double, h: Double)

}

