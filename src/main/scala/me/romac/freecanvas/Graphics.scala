
package me.romac
package freecanvas

import cats.{Id, ~>}
import cats.free.Free
import cats.arrow.NaturalTransformation

import me.romac.freecanvas.{ Canvas => C }
import me.romac.freecanvas.GraphicsF._

object Graphics {

  type Graphics[A] = Free[GraphicsF, A]

  val unit  = ()
  val const = Function.const _

  def interpretWith(ctx: C.Context2D): GraphicsF ~> Id = new NaturalTransformation[GraphicsF, Id] {
    import scala.scalajs.js.timers

    def apply[A](fa: GraphicsF[A]): Id[A] = fa match {
      case SetLineWidth(value, next)                => ctx.lineWidth = value; next
      case SetFillStyle(value, next)                => ctx.fillStyle = value; next
      // case SetStrokeStyle(value, next)           => ctx.setStrokeStyle(value); next
      // case SetShadowColor(value, next)           => ctx.setShadowColor(value); next
      // case SetShadowBlur(value, next)            => ctx.setLineWidth(value); next
      // case SetShadowOffsetX(value, next)         => ctx.setLineWidth(value); next
      // case SetShadowOffsetY(value, next)         => ctx.setLineWidth(value); next
      // case SetLineCap(value, next)               => ctx.setLineWidth(value); next
      // case SetComposite(value, next)             => ctx.setLineWidth(value); next
      // case SetAlpha(value, next)                 => ctx.setLineWidth(value); next
      case BeginPath(next)                          => ctx.beginPath(); next
      // case Stroke(next)                          => ctx.setLineWidth(value); next
      case Fill(next)                               => ctx.fill(); next
      // case Clip(next)                            => ctx.setLineWidth(value); next
      // case LineTo(x, next)                       => ctx.setLineWidth(value); next
      // case MoveTo(x, next)                       => ctx.setLineWidth(value); next
      case ClosePath(next)                       => ctx.closePath(); next
      case Arc(C.Arc(x, y, r, s, e), next)          => ctx.arc(x, y, r, s, e); next
      case Rect(C.Rectangle(x, y, w, h), next)      => ctx.rect(x, y, w, h); next
      case FillRect(C.Rectangle(x, y, w, h), next)  => ctx.fillRect(x, y, w, h); next
      // case StrokeRect(value, next)               => ctx.setLineWidth(value); next
      case ClearRect(C.Rectangle(x, y, w, h), next) => ctx.clearRect(x, y, w, h); next
      // case Scale(x, next)                        => ctx.setLineWidth(value); next
      // case Rotate(angle, next)                   => ctx.setLineWidth(value); next
      // case Translate(x, next)                    => ctx.setLineWidth(value); next
      // case Transform(transform, next)            => ctx.setLineWidth(value); next
      // case TextAlign(next)                       => ctx.setLineWidth(value); next
      // case SetTextAlign(value, next)             => ctx.setLineWidth(value); next
      // case Font(next)                            => ctx.setLineWidth(value); next
      // case SetFont(value, next)                  => ctx.setLineWidth(value); next
      // case FillText(text, next)                  => ctx.setLineWidth(value); next
      // case StrokeText(text, next)                => ctx.setLineWidth(value); next
      // case MeasureText(value, next)              => ctx.setLineWidth(value); next
      // case Save(next)                            => ctx.setLineWidth(value); next
      // case Restore(next)                         => ctx.setLineWidth(value); next
      // case GetImageData(x, next)                 => ctx.setLineWidth(value); next
      // case PutImageData(data, next)              => ctx.setLineWidth(value); next
      // case CreateImageData(width, next)          => ctx.setLineWidth(value); next
      // case CreateImageDataCopy(data, next)       => ctx.setLineWidth(value); next
      // case DrawImage(source, next)               => ctx.setLineWidth(value); next
      case SetTimeout(value, delay, next) =>
        timers.setTimeout(delay) {
          Graphics.run(ctx)(value)
        }
        next
    }
  }

  def run[A](ctx: C.Context2D)(graphics: Graphics[A]): Id[A] = {
    graphics.foldMap(interpretWith(ctx))
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

  def setLineWidth         (value: Int)                                                      : Graphics[Unit]          = Free.liftF(SetLineWidth(value, unit))
  def setFillStyle         (value: String)                                                   : Graphics[Unit]          = Free.liftF(SetFillStyle(value, unit))
  def setStrokeStyle       (value: String)                                                   : Graphics[Unit]          = Free.liftF(SetStrokeStyle(value, unit))
  def setShadowColor       (value: String)                                                   : Graphics[Unit]          = Free.liftF(SetShadowColor(value, unit))
  def setShadowBlur        (value: Int)                                                      : Graphics[Unit]          = Free.liftF(SetShadowBlur(value, unit))
  def setShadowOffsetX     (value: Int)                                                      : Graphics[Unit]          = Free.liftF(SetShadowOffsetX(value, unit))
  def setShadowOffsetY     (value: Int)                                                      : Graphics[Unit]          = Free.liftF(SetShadowOffsetY(value, unit))
  def setLineCap           (value: C.LineCap)                                                : Graphics[Unit]          = Free.liftF(SetLineCap(value, unit))
  def setComposite         (value: C.Composite)                                              : Graphics[Unit]          = Free.liftF(SetComposite(value, unit))
  def setAlpha             (value: Int)                                                      : Graphics[Unit]          = Free.liftF(SetAlpha(value, unit))
  def beginPath                                                                              : Graphics[Unit]          = Free.liftF(BeginPath(unit))
  def stroke                                                                                 : Graphics[Unit]          = Free.liftF(Stroke(unit))
  def fill                                                                                   : Graphics[Unit]          = Free.liftF(Fill(unit))
  def clip                                                                                   : Graphics[Unit]          = Free.liftF(Clip(unit))
  def lineTo               (x: Int, y: Int)                                                  : Graphics[Unit]          = Free.liftF(LineTo(x, y, unit))
  def moveTo               (x: Int, y: Int)                                                  : Graphics[Unit]          = Free.liftF(MoveTo(x, y, unit))
  def closePath                                                                              : Graphics[Unit]          = Free.liftF(ClosePath(unit))
  def arc                  (value: C.Arc)                                                    : Graphics[Unit]          = Free.liftF(Arc(value, unit))
  def rect                 (value: C.Rectangle)                                              : Graphics[Unit]          = Free.liftF(Rect(value, unit))
  def fillRect             (value: C.Rectangle)                                              : Graphics[Unit]          = Free.liftF(FillRect(value, unit))
  def strokeRect           (value: C.Rectangle)                                              : Graphics[Unit]          = Free.liftF(StrokeRect(value, unit))
  def clearRect            (value: C.Rectangle)                                              : Graphics[Unit]          = Free.liftF(ClearRect(value, unit))
  def scale                (x: Int, y: Int)                                                  : Graphics[Unit]          = Free.liftF(Scale(x, y, unit))
  def rotate               (angle: Int)                                                      : Graphics[Unit]          = Free.liftF(Rotate(angle, unit))
  def translate            (x: Int, y: Int)                                                  : Graphics[Unit]          = Free.liftF(Translate(x, y, unit))
  def transform            (transform: C.Transform)                                          : Graphics[Unit]          = Free.liftF(Transform(transform, unit))
  def textAlign                                                                              : Graphics[C.TextAlign]   = Free.liftF(TextAlign(identity))
  def setTextAlign         (value: C.TextAlign)                                              : Graphics[Unit]          = Free.liftF(SetTextAlign(value, unit))
  def font                                                                                   : Graphics[String]        = Free.liftF(Font(identity))
  def setFont              (value: String)                                                   : Graphics[Unit]          = Free.liftF(SetFont(value, unit))
  def fillText             (text: String, x: Int, y: Int)                                    : Graphics[Unit]          = Free.liftF(FillText(text, x, y, unit))
  def strokeText           (text: String, x: Int, y: Int)                                    : Graphics[Unit]          = Free.liftF(StrokeText(text, x, y, unit))
  def measureText          (value: String)                                                   : Graphics[C.TextMetrics] = Free.liftF(MeasureText(value, identity))
  def save                                                                                   : Graphics[Unit]          = Free.liftF(Save(unit))
  def restore                                                                                : Graphics[Unit]          = Free.liftF(Restore(unit))
  def getImageData         (x: Int, y: Int, width: Int, height: Int)                         : Graphics[C.ImageData]   = Free.liftF(GetImageData(x, y, width, height, identity))
  def putImageData         (data: C.ImageData, x: Int, y: Int)                               : Graphics[Unit]          = Free.liftF(PutImageData(data, x, y, unit))
  def createImageData      (width: Int, height: Int)                                         : Graphics[C.ImageData]   = Free.liftF(CreateImageData(width, height, identity))
  def createImageDataCopy  (data: C.ImageData)                                               : Graphics[C.ImageData]   = Free.liftF(CreateImageDataCopy(data, identity))
  def drawImage            (source: C.ImageSource, x: Int, y: Int)                           : Graphics[Unit]          = Free.liftF(DrawImage(source, x, y, unit))
  def setTimeout(value: Graphics[Unit], delay: Int): Graphics[Unit] = Free.liftF(SetTimeout(value, delay, unit))

}

