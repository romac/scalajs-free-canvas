
package me.romac
package freecanvas

import cats.free.Free

import me.romac.freecanvas.{ Canvas => C }

sealed trait GraphicsF[A]

object GraphicsF {
  case class SetLineWidth         [A](value: Int,         next: A)                                     extends GraphicsF[A]
  case class SetFillStyle         [A](value: String,      next: A)                                     extends GraphicsF[A]
  case class SetStrokeStyle       [A](value: String,      next: A)                                     extends GraphicsF[A]
  case class SetShadowColor       [A](value: String,      next: A)                                     extends GraphicsF[A]
  case class SetShadowBlur        [A](value: Int,         next: A)                                     extends GraphicsF[A]
  case class SetShadowOffsetX     [A](value: Int,         next: A)                                     extends GraphicsF[A]
  case class SetShadowOffsetY     [A](value: Int,         next: A)                                     extends GraphicsF[A]
  case class SetLineCap           [A](value: C.LineCap,   next: A)                                     extends GraphicsF[A]
  case class SetComposite         [A](value: C.Composite, next: A)                                     extends GraphicsF[A]
  case class SetAlpha             [A](value: Int,         next: A)                                     extends GraphicsF[A]
  case class BeginPath            [A](next: A)                                                         extends GraphicsF[A]
  case class Stroke               [A](next: A)                                                         extends GraphicsF[A]
  case class Fill                 [A](next: A)                                                         extends GraphicsF[A]
  case class Clip                 [A](next: A)                                                         extends GraphicsF[A]
  case class LineTo               [A](x: Int, y: Int, next: A)                                         extends GraphicsF[A]
  case class MoveTo               [A](x: Int, y: Int, next: A)                                         extends GraphicsF[A]
  case class ClosePath            [A](next: A)                                                         extends GraphicsF[A]
  case class Arc                  [A](value: C.Arc,       next: A)                                     extends GraphicsF[A]
  case class Rect                 [A](value: C.Rectangle, next: A)                                     extends GraphicsF[A]
  case class FillRect             [A](value: C.Rectangle, next: A)                                     extends GraphicsF[A]
  case class StrokeRect           [A](value: C.Rectangle, next: A)                                     extends GraphicsF[A]
  case class ClearRect            [A](value: C.Rectangle, next: A)                                     extends GraphicsF[A]
  case class Scale                [A](x: Int, y: Int,         next: A)                                 extends GraphicsF[A]
  case class Rotate               [A](angle: Int,             next: A)                                 extends GraphicsF[A]
  case class Translate            [A](x: Int, y: Int,         next: A)                                 extends GraphicsF[A]
  case class Transform            [A](transform: C.Transform, next: A)                                 extends GraphicsF[A]
  case class TextAlign            [A](next: C.TextAlign => A)                                          extends GraphicsF[A]
  case class SetTextAlign         [A](value: C.TextAlign, next: A)                                     extends GraphicsF[A]
  case class Font                 [A](next: String => A)                                               extends GraphicsF[A]
  case class SetFont              [A](value: String, next: A)                                          extends GraphicsF[A]
  case class FillText             [A](text: String, x: Int, y: Int, next: A)                           extends GraphicsF[A]
  case class StrokeText           [A](text: String, x: Int, y: Int, next: A)                           extends GraphicsF[A]
  case class MeasureText          [A](value: String, next: C.TextMetrics => A)                         extends GraphicsF[A]
  case class Save                 [A](next: A)                                                         extends GraphicsF[A]
  case class Restore              [A](next: A)                                                         extends GraphicsF[A]
  case class GetImageData         [A](x: Int, y: Int, width: Int, height: Int, next: C.ImageData => A) extends GraphicsF[A]
  case class PutImageData         [A](data: C.ImageData, x: Int, y: Int, next: A)                      extends GraphicsF[A]
  case class CreateImageData      [A](width: Int, height: Int, next: C.ImageData => A)                 extends GraphicsF[A]
  case class CreateImageDataCopy  [A](data: C.ImageData, next: C.ImageData => A)                       extends GraphicsF[A]
  case class DrawImage            [A](source: C.ImageSource, x: Int, y: Int, next: A)                  extends GraphicsF[A]

  case class SetTimeout[A](value: Free[GraphicsF, Unit], delay: Int, next: A) extends GraphicsF[A]
}
