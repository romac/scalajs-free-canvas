
package me.romac
package freecanvas

import cats.{ ~> }
import cats.implicits._
import cats.free.{ Free, Trampoline }
import cats.arrow.NaturalTransformation

import me.romac.freecanvas.{ Canvas => C }
import me.romac.freecanvas.GraphicsF._

object Graphics {

  type Graphics[A] = Free[GraphicsF, A]

  val unit  = ()
  val const = Function.const _

  class DOMIntepreter(ctx: C.Context2D) extends (GraphicsF ~> Trampoline) {
    import scala.scalajs.js.timers

    import Trampoline.done

    def apply[A](fa: GraphicsF[A]): Trampoline[A] = fa match {
      case SetLineWidth(value)                => ctx.lineWidth = value; done(())
      case SetFillStyle(value)                => ctx.fillStyle = value; done(())
      // case SetStrokeStyle(value)           => ctx.setStrokeStyle(value); done(())
      // case SetShadowColor(value)           => ctx.setShadowColor(value); done(())
      // case SetShadowBlur(value)            => ctx.setLineWidth(value); done(())
      // case SetShadowOffsetX(value)         => ctx.setLineWidth(value); done(())
      // case SetShadowOffsetY(value)         => ctx.setLineWidth(value); done(())
      // case SetLineCap(value)               => ctx.setLineWidth(value); done(())
      // case SetComposite(value)             => ctx.setLineWidth(value); done(())
      // case SetAlpha(value)                 => ctx.setLineWidth(value); done(())
      case BeginPath                          => ctx.beginPath(); done(())
      // case Stroke                          => ctx.setLineWidth(value); done(())
      case Fill                               => ctx.fill(); done(())
      // case Clip                            => ctx.setLineWidth(value); done(())
      // case LineTo(x)                       => ctx.setLineWidth(value); done(())
      // case MoveTo(x)                       => ctx.setLineWidth(value); done(())
      case ClosePath                          => ctx.closePath(); done(())
      case Arc(C.Arc(x, y, r, s, e))          => ctx.arc(x, y, r, s, e); done(())
      case Rect(C.Rectangle(x, y, w, h))      => ctx.rect(x, y, w, h); done(())
      case FillRect(C.Rectangle(x, y, w, h))  => ctx.fillRect(x, y, w, h); done(())
      // case StrokeRect(value)               => ctx.setLineWidth(value); done(())
      case ClearRect(C.Rectangle(x, y, w, h)) => ctx.clearRect(x, y, w, h); done(())
      // case Scale(x)                        => ctx.setLineWidth(value); done(())
      // case Rotate(angle)                   => ctx.setLineWidth(value); done(())
      // case Translate(x)                    => ctx.setLineWidth(value); done(())
      // case Transform(transform)            => ctx.setLineWidth(value); done(())
      // case TextAlign                       => ctx.setLineWidth(value); done(())
      // case SetTextAlign(value)             => ctx.setLineWidth(value); done(())
      // case Font                            => ctx.setLineWidth(value); done(())
      // case SetFont(value)                  => ctx.setLineWidth(value); done(())
      // case FillText(text)                  => ctx.setLineWidth(value); done(())
      // case StrokeText(text)                => ctx.setLineWidth(value); done(())
      // case MeasureText(value)              => ctx.setLineWidth(value); done(())
      // case Save                            => ctx.setLineWidth(value); done(())
      // case Restore                         => ctx.setLineWidth(value); done(())
      // case GetImageData(x)                 => ctx.setLineWidth(value); done(())
      // case PutImageData(data)              => ctx.setLineWidth(value); done(())
      // case CreateImageData(width)          => ctx.setLineWidth(value); done(())
      // case CreateImageDataCopy(data)       => ctx.setLineWidth(value); done(())
      // case DrawImage(source)               => ctx.setLineWidth(value); done(())
      case SetTimeout(value, timeout) =>
        timers.setTimeout(timeout) {
          Graphics.run(ctx)(value)
        }
        done(())
    }
  }

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

  def setLineWidth         (value: Int)                                                      : Graphics[Unit]          = SetLineWidth(value).lift
  def setFillStyle         (value: String)                                                   : Graphics[Unit]          = SetFillStyle(value).lift
  def setStrokeStyle       (value: String)                                                   : Graphics[Unit]          = SetStrokeStyle(value).lift
  def setShadowColor       (value: String)                                                   : Graphics[Unit]          = SetShadowColor(value).lift
  def setShadowBlur        (value: Int)                                                      : Graphics[Unit]          = SetShadowBlur(value).lift
  def setShadowOffsetX     (value: Int)                                                      : Graphics[Unit]          = SetShadowOffsetX(value).lift
  def setShadowOffsetY     (value: Int)                                                      : Graphics[Unit]          = SetShadowOffsetY(value).lift
  def setLineCap           (value: C.LineCap)                                                : Graphics[Unit]          = SetLineCap(value).lift
  def setComposite         (value: C.Composite)                                              : Graphics[Unit]          = SetComposite(value).lift
  def setAlpha             (value: Int)                                                      : Graphics[Unit]          = SetAlpha(value).lift
  def beginPath                                                                              : Graphics[Unit]          = BeginPath.lift
  def stroke                                                                                 : Graphics[Unit]          = Stroke.lift
  def fill                                                                                   : Graphics[Unit]          = Fill.lift
  def clip                                                                                   : Graphics[Unit]          = Clip.lift
  def lineTo               (x: Int, y: Int)                                                  : Graphics[Unit]          = LineTo(x, y).lift
  def moveTo               (x: Int, y: Int)                                                  : Graphics[Unit]          = MoveTo(x, y).lift
  def closePath                                                                              : Graphics[Unit]          = ClosePath.lift
  def arc                  (value: C.Arc)                                                    : Graphics[Unit]          = Arc(value).lift
  def rect                 (value: C.Rectangle)                                              : Graphics[Unit]          = Rect(value).lift
  def fillRect             (value: C.Rectangle)                                              : Graphics[Unit]          = FillRect(value).lift
  def strokeRect           (value: C.Rectangle)                                              : Graphics[Unit]          = StrokeRect(value).lift
  def clearRect            (value: C.Rectangle)                                              : Graphics[Unit]          = ClearRect(value).lift
  def scale                (x: Int, y: Int)                                                  : Graphics[Unit]          = Scale(x, y).lift
  def rotate               (angle: Int)                                                      : Graphics[Unit]          = Rotate(angle).lift
  def translate            (x: Int, y: Int)                                                  : Graphics[Unit]          = Translate(x, y).lift
  def transform            (transform: C.Transform)                                          : Graphics[Unit]          = Transform(transform).lift
  def textAlign                                                                              : Graphics[C.TextAlign]   = TextAlign.lift
  def setTextAlign         (value: C.TextAlign)                                              : Graphics[Unit]          = SetTextAlign(value).lift
  def font                                                                                   : Graphics[String]        = Font.lift
  def setFont              (value: String)                                                   : Graphics[Unit]          = SetFont(value).lift
  def fillText             (text: String, x: Int, y: Int)                                    : Graphics[Unit]          = FillText(text, x, y).lift
  def strokeText           (text: String, x: Int, y: Int)                                    : Graphics[Unit]          = StrokeText(text, x, y).lift
  def measureText          (value: String)                                                   : Graphics[C.TextMetrics] = MeasureText(value).lift
  def save                                                                                   : Graphics[Unit]          = Save.lift
  def restore                                                                                : Graphics[Unit]          = Restore.lift
  def getImageData         (x: Int, y: Int, width: Int, height: Int)                         : Graphics[C.ImageData]   = GetImageData(x, y, width, height).lift
  def putImageData         (data: C.ImageData, x: Int, y: Int)                               : Graphics[Unit]          = PutImageData(data, x, y).lift
  def createImageData      (width: Int, height: Int)                                         : Graphics[C.ImageData]   = CreateImageData(width, height).lift
  def createImageDataCopy  (data: C.ImageData)                                               : Graphics[C.ImageData]   = CreateImageDataCopy(data).lift
  def drawImage            (source: C.ImageSource, x: Int, y: Int)                           : Graphics[Unit]          = DrawImage(source, x, y).lift
  def setTimeout           (value: Graphics[Unit], delay: Int)                               : Graphics[Unit]          = SetTimeout(value, delay).lift

}

