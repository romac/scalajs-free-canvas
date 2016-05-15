
package me.romac
package freecanvas

import cats.{ ~>, Monad }
import cats.implicits._

import me.romac.freecanvas.{ Canvas => C }
import me.romac.freecanvas.GraphicsF._

object Graphics {

  def runWith[A, M[_]: Monad](interpret: GraphicsF ~> M)(graphics: Graphics[A]): M[A] =
    graphics.foldMap(interpret)

  def run[A](ctx: C.Context2D)(graphics: Graphics[A]): A =
    runWith(new DOMIntepreter(ctx))(graphics).run

  def withContext[A](action: Graphics[A]): Graphics[A] =
    for {
      _ <- save
      a <- action
      _ <- restore
    } yield a

  def path[A](action: Graphics[A]): Graphics[A] =
    for {
      _ <- beginPath
      a <- action
      _ <- closePath
    } yield a

  def setLineWidth         (value: Double)                                 : Graphics[Unit]          = SetLineWidth(value)       .lift
  def setFillStyle         (value: String)                                 : Graphics[Unit]          = SetFillStyle(value)       .lift
  def setStrokeStyle       (value: String)                                 : Graphics[Unit]          = SetStrokeStyle(value)     .lift
  def setShadowColor       (value: String)                                 : Graphics[Unit]          = SetShadowColor(value)     .lift
  def setShadowBlur        (value: Double)                                 : Graphics[Unit]          = SetShadowBlur(value)      .lift
  def setShadowOffsetX     (value: Double)                                 : Graphics[Unit]          = SetShadowOffsetX(value)   .lift
  def setShadowOffsetY     (value: Double)                                 : Graphics[Unit]          = SetShadowOffsetY(value)   .lift
  def setLineCap           (value: C.LineCap)                              : Graphics[Unit]          = SetLineCap(value)         .lift
  def setComposite         (value: C.Composite)                            : Graphics[Unit]          = SetComposite(value)       .lift
  def setAlpha             (value: Double)                                 : Graphics[Unit]          = SetAlpha(value)           .lift
  val beginPath                                                            : Graphics[Unit]          = BeginPath                 .lift
  val stroke                                                               : Graphics[Unit]          = Stroke                    .lift
  val fill                                                                 : Graphics[Unit]          = Fill                      .lift
  val clip                                                                 : Graphics[Unit]          = Clip                      .lift
  def lineTo               (x: Double, y: Double)                          : Graphics[Unit]          = LineTo(x, y)              .lift
  def moveTo               (x: Double, y: Double)                          : Graphics[Unit]          = MoveTo(x, y)              .lift
  val closePath                                                            : Graphics[Unit]          = ClosePath                 .lift
  def arc                  (value: C.Arc)                                  : Graphics[Unit]          = Arc(value)                .lift
  def rect                 (value: C.Rectangle)                            : Graphics[Unit]          = Rect(value)               .lift
  def fillRect             (value: C.Rectangle)                            : Graphics[Unit]          = FillRect(value)           .lift
  def strokeRect           (value: C.Rectangle)                            : Graphics[Unit]          = StrokeRect(value)         .lift
  def clearRect            (value: C.Rectangle)                            : Graphics[Unit]          = ClearRect(value)          .lift
  def scale                (x: Double, y: Double)                          : Graphics[Unit]          = Scale(x, y)               .lift
  def rotate               (angle: Double)                                 : Graphics[Unit]          = Rotate(angle)             .lift
  def translate            (x: Double, y: Double)                          : Graphics[Unit]          = Translate(x, y)           .lift
  def transform            (transform: C.Transform)                        : Graphics[Unit]          = Transform(transform)      .lift
  val textAlign                                                            : Graphics[C.TextAlign]   = TextAlign                 .lift
  def setTextAlign         (value: C.TextAlign)                            : Graphics[Unit]          = SetTextAlign(value)       .lift
  val font                                                                 : Graphics[String]        = Font                      .lift
  def setFont              (value: String)                                 : Graphics[Unit]          = SetFont(value)            .lift
  def fillText             (text: String, x: Double, y: Double)            : Graphics[Unit]          = FillText(text, x, y)      .lift
  def strokeText           (text: String, x: Double, y: Double)            : Graphics[Unit]          = StrokeText(text, x, y)    .lift
  def measureText          (value: String)                                 : Graphics[C.TextMetrics] = MeasureText(value)        .lift
  val save                                                                 : Graphics[Unit]          = Save                      .lift
  val restore                                                              : Graphics[Unit]          = Restore                   .lift
  def getImageData         (x: Double, y: Double, w: Double, h: Double)    : Graphics[C.ImageData]   = GetImageData(x, y, w, h)  .lift
  def putImageData         (data: C.ImageData, x: Double, y: Double)       : Graphics[Unit]          = PutImageData(data, x, y)  .lift
  def createImageData      (w: Double, h: Double)                          : Graphics[C.ImageData]   = CreateImageData(w, h)     .lift
  def createImageDataCopy  (data: C.ImageData)                             : Graphics[C.ImageData]   = CreateImageDataCopy(data) .lift
  def drawImage            (source: C.ImageSource, x: Double, y: Double)   : Graphics[Unit]          = DrawImage(source, x, y)   .lift
  def setTimeout           (value: Graphics[Unit], delay: Double)          : Graphics[Unit]          = SetTimeout(value, delay)  .lift

}

