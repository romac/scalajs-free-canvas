
package me.romac
package freecanvas

import org.scalajs.dom

object Canvas {

  type Context2D = dom.CanvasRenderingContext2D

  sealed trait LineCap {
    def value: String
  }

  sealed trait Composite {
    def value: String
  }

  case class Transform(m11: Double, m12: Double, m21: Double, m22: Double, dx: Double, dy: Double)

  sealed trait TextAlign {
    def value: String
  }

  object TextAlign {
    def apply(str: String): TextAlign = ???
  }

  type TextMetrics = dom.raw.TextMetrics

  type ImageData = dom.raw.ImageData

  sealed trait ImageSource {
    def value: String
  }

  case class Arc(x: Double, y: Double, radius: Double, startAngle: Double, endAngle: Double)
  case class Rectangle(x: Double, y: Double, w: Double, h: Double)

}

