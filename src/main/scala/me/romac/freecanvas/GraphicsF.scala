
package me.romac
package freecanvas

import cats.free.Free

import me.romac.freecanvas.{ Canvas => C }

sealed trait GraphicsF[A] {
  final def lift: Graphics[A] = Free.liftF(this)
}

object GraphicsF {
  final case class  SetLineWidth         (value: Double)                                       extends GraphicsF[Unit]
  final case class  SetFillStyle         (value: String)                                       extends GraphicsF[Unit]
  final case class  SetStrokeStyle       (value: String)                                       extends GraphicsF[Unit]
  final case class  SetShadowColor       (value: String)                                       extends GraphicsF[Unit]
  final case class  SetShadowBlur        (value: Double)                                       extends GraphicsF[Unit]
  final case class  SetShadowOffsetX     (value: Double)                                       extends GraphicsF[Unit]
  final case class  SetShadowOffsetY     (value: Double)                                       extends GraphicsF[Unit]
  final case class  SetLineCap           (value: C.LineCap)                                    extends GraphicsF[Unit]
  final case class  SetLineJoin          (value: C.LineJoin)                                   extends GraphicsF[Unit]
  final case class  SetComposite         (value: C.Composite)                                  extends GraphicsF[Unit]
  final case class  SetAlpha             (value: Double)                                       extends GraphicsF[Unit]
  final case object BeginPath                                                                  extends GraphicsF[Unit]
  final case object Stroke                                                                     extends GraphicsF[Unit]
  final case object Fill                                                                       extends GraphicsF[Unit]
  final case object Clip                                                                       extends GraphicsF[Unit]
  final case class  LineTo               (x: Double, y: Double)                                extends GraphicsF[Unit]
  final case class  MoveTo               (x: Double, y: Double)                                extends GraphicsF[Unit]
  final case object ClosePath                                                                  extends GraphicsF[Unit]
  final case class  Arc                  (value: C.Arc)                                        extends GraphicsF[Unit]
  final case class  Rect                 (value: C.Rectangle)                                  extends GraphicsF[Unit]
  final case class  FillRect             (value: C.Rectangle)                                  extends GraphicsF[Unit]
  final case class  StrokeRect           (value: C.Rectangle)                                  extends GraphicsF[Unit]
  final case class  ClearRect            (value: C.Rectangle)                                  extends GraphicsF[Unit]
  final case class  Scale                (x: Double, y: Double)                                extends GraphicsF[Unit]
  final case class  Rotate               (angle: Double)                                       extends GraphicsF[Unit]
  final case class  Translate            (x: Double, y: Double)                                extends GraphicsF[Unit]
  final case class  Transform            (transform: C.Transform)                              extends GraphicsF[Unit]
  final case object TextAlign                                                                  extends GraphicsF[C.TextAlign]
  final case class  SetTextAlign         (value: C.TextAlign)                                  extends GraphicsF[Unit]
  final case object Font                                                                       extends GraphicsF[String]
  final case class  SetFont              (value: String)                                       extends GraphicsF[Unit]
  final case class  FillText             (text: String, x: Double, y: Double)                  extends GraphicsF[Unit]
  final case class  StrokeText           (text: String, x: Double, y: Double)                  extends GraphicsF[Unit]
  final case class  MeasureText          (value: String)                                       extends GraphicsF[C.TextMetrics]
  final case object Save                                                                       extends GraphicsF[Unit]
  final case object Restore                                                                    extends GraphicsF[Unit]
  final case class  GetImageData         (x: Double, y: Double, width: Double, height: Double) extends GraphicsF[C.ImageData]
  final case class  PutImageData         (data: C.ImageData, x: Double, y: Double)             extends GraphicsF[Unit]
  final case class  CreateImageData      (width: Double, height: Double)                       extends GraphicsF[C.ImageData]
  final case class  CreateImageDataCopy  (data: C.ImageData)                                   extends GraphicsF[C.ImageData]
  final case class  DrawImage            (source: C.ImageSource, x: Double, y: Double)         extends GraphicsF[Unit]

  final case class  SetTimeout           (value: Graphics[Unit], delay: Double)                extends GraphicsF[Unit]
}

