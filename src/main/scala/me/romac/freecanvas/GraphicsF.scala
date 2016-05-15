
package me.romac
package freecanvas

import cats.free.Free

import me.romac.freecanvas.{ Canvas => C }

sealed trait GraphicsF[A] {
  def lift: Graphics[A] = Free.liftF(this)
}

object GraphicsF {
  case class  SetLineWidth         (value: Double)                              extends GraphicsF[Unit]
  case class  SetFillStyle         (value: String)                           extends GraphicsF[Unit]
  case class  SetStrokeStyle       (value: String)                           extends GraphicsF[Unit]
  case class  SetShadowColor       (value: String)                           extends GraphicsF[Unit]
  case class  SetShadowBlur        (value: Double)                              extends GraphicsF[Unit]
  case class  SetShadowOffsetX     (value: Double)                              extends GraphicsF[Unit]
  case class  SetShadowOffsetY     (value: Double)                              extends GraphicsF[Unit]
  case class  SetLineCap           (value: C.LineCap)                        extends GraphicsF[Unit]
  case class  SetLineJoin          (value: C.LineJoin)                       extends GraphicsF[Unit]
  case class  SetComposite         (value: C.Composite)                      extends GraphicsF[Unit]
  case class  SetAlpha             (value: Double)                              extends GraphicsF[Unit]
  case object BeginPath                                                      extends GraphicsF[Unit]
  case object Stroke                                                         extends GraphicsF[Unit]
  case object Fill                                                           extends GraphicsF[Unit]
  case object Clip                                                           extends GraphicsF[Unit]
  case class  LineTo               (x: Double, y: Double)                          extends GraphicsF[Unit]
  case class  MoveTo               (x: Double, y: Double)                          extends GraphicsF[Unit]
  case object ClosePath                                                      extends GraphicsF[Unit]
  case class  Arc                  (value: C.Arc)                            extends GraphicsF[Unit]
  case class  Rect                 (value: C.Rectangle)                      extends GraphicsF[Unit]
  case class  FillRect             (value: C.Rectangle)                      extends GraphicsF[Unit]
  case class  StrokeRect           (value: C.Rectangle)                      extends GraphicsF[Unit]
  case class  ClearRect            (value: C.Rectangle)                      extends GraphicsF[Unit]
  case class  Scale                (x: Double, y: Double)                          extends GraphicsF[Unit]
  case class  Rotate               (angle: Double)                              extends GraphicsF[Unit]
  case class  Translate            (x: Double, y: Double)                          extends GraphicsF[Unit]
  case class  Transform            (transform: C.Transform)                  extends GraphicsF[Unit]
  case object TextAlign                                                      extends GraphicsF[C.TextAlign]
  case class  SetTextAlign         (value: C.TextAlign)                      extends GraphicsF[Unit]
  case object Font                                                           extends GraphicsF[String]
  case class  SetFont              (value: String)                           extends GraphicsF[Unit]
  case class  FillText             (text: String, x: Double, y: Double)            extends GraphicsF[Unit]
  case class  StrokeText           (text: String, x: Double, y: Double)            extends GraphicsF[Unit]
  case class  MeasureText          (value: String)                           extends GraphicsF[C.TextMetrics]
  case object Save                                                           extends GraphicsF[Unit]
  case object Restore                                                        extends GraphicsF[Unit]
  case class  GetImageData         (x: Double, y: Double, width: Double, height: Double) extends GraphicsF[C.ImageData]
  case class  PutImageData         (data: C.ImageData, x: Double, y: Double)       extends GraphicsF[Unit]
  case class  CreateImageData      (width: Double, height: Double)                 extends GraphicsF[C.ImageData]
  case class  CreateImageDataCopy  (data: C.ImageData)                       extends GraphicsF[C.ImageData]
  case class  DrawImage            (source: C.ImageSource, x: Double, y: Double)   extends GraphicsF[Unit]

  case class  SetTimeout           (value: Graphics[Unit], delay: Double)       extends GraphicsF[Unit]
}

