
package me.romac
package freecanvas

import cats.{ ~> }
import cats.implicits._
import cats.free.{ Free, Trampoline }

import me.romac.freecanvas.{ Canvas => C }
import me.romac.freecanvas.GraphicsF._

object Graphics {

  def run[A](ctx: C.Context2D)(graphics: Graphics[A]): A = {
    val interpret = new DOMIntepreter(ctx)
    graphics.foldMap(interpret).run
  }

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

  def setLineWidth         (value: Int)                              : Graphics[Unit]          = SetLineWidth(value).lift
  def setFillStyle         (value: String)                           : Graphics[Unit]          = SetFillStyle(value).lift
  def setStrokeStyle       (value: String)                           : Graphics[Unit]          = SetStrokeStyle(value).lift
  def setShadowColor       (value: String)                           : Graphics[Unit]          = SetShadowColor(value).lift
  def setShadowBlur        (value: Int)                              : Graphics[Unit]          = SetShadowBlur(value).lift
  def setShadowOffsetX     (value: Int)                              : Graphics[Unit]          = SetShadowOffsetX(value).lift
  def setShadowOffsetY     (value: Int)                              : Graphics[Unit]          = SetShadowOffsetY(value).lift
  def setLineCap           (value: C.LineCap)                        : Graphics[Unit]          = SetLineCap(value).lift
  def setComposite         (value: C.Composite)                      : Graphics[Unit]          = SetComposite(value).lift
  def setAlpha             (value: Int)                              : Graphics[Unit]          = SetAlpha(value).lift
  def beginPath                                                      : Graphics[Unit]          = BeginPath.lift
  def stroke                                                         : Graphics[Unit]          = Stroke.lift
  def fill                                                           : Graphics[Unit]          = Fill.lift
  def clip                                                           : Graphics[Unit]          = Clip.lift
  def lineTo               (x: Int, y: Int)                          : Graphics[Unit]          = LineTo(x, y).lift
  def moveTo               (x: Int, y: Int)                          : Graphics[Unit]          = MoveTo(x, y).lift
  def closePath                                                      : Graphics[Unit]          = ClosePath.lift
  def arc                  (value: C.Arc)                            : Graphics[Unit]          = Arc(value).lift
  def rect                 (value: C.Rectangle)                      : Graphics[Unit]          = Rect(value).lift
  def fillRect             (value: C.Rectangle)                      : Graphics[Unit]          = FillRect(value).lift
  def strokeRect           (value: C.Rectangle)                      : Graphics[Unit]          = StrokeRect(value).lift
  def clearRect            (value: C.Rectangle)                      : Graphics[Unit]          = ClearRect(value).lift
  def scale                (x: Int, y: Int)                          : Graphics[Unit]          = Scale(x, y).lift
  def rotate               (angle: Int)                              : Graphics[Unit]          = Rotate(angle).lift
  def translate            (x: Int, y: Int)                          : Graphics[Unit]          = Translate(x, y).lift
  def transform            (transform: C.Transform)                  : Graphics[Unit]          = Transform(transform).lift
  def textAlign                                                      : Graphics[C.TextAlign]   = TextAlign.lift
  def setTextAlign         (value: C.TextAlign)                      : Graphics[Unit]          = SetTextAlign(value).lift
  def font                                                           : Graphics[String]        = Font.lift
  def setFont              (value: String)                           : Graphics[Unit]          = SetFont(value).lift
  def fillText             (text: String, x: Int, y: Int)            : Graphics[Unit]          = FillText(text, x, y).lift
  def strokeText           (text: String, x: Int, y: Int)            : Graphics[Unit]          = StrokeText(text, x, y).lift
  def measureText          (value: String)                           : Graphics[C.TextMetrics] = MeasureText(value).lift
  def save                                                           : Graphics[Unit]          = Save.lift
  def restore                                                        : Graphics[Unit]          = Restore.lift
  def getImageData         (x: Int, y: Int, width: Int, height: Int) : Graphics[C.ImageData]   = GetImageData(x, y, width, height).lift
  def putImageData         (data: C.ImageData, x: Int, y: Int)       : Graphics[Unit]          = PutImageData(data, x, y).lift
  def createImageData      (width: Int, height: Int)                 : Graphics[C.ImageData]   = CreateImageData(width, height).lift
  def createImageDataCopy  (data: C.ImageData)                       : Graphics[C.ImageData]   = CreateImageDataCopy(data).lift
  def drawImage            (source: C.ImageSource, x: Int, y: Int)   : Graphics[Unit]          = DrawImage(source, x, y).lift
  def setTimeout           (value: Graphics[Unit], delay: Int)       : Graphics[Unit]          = SetTimeout(value, delay).lift

}

