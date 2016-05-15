package me.romac.freecanvas

import scala.scalajs.js.timers

import cats.{ ~> }
import cats.free.{ Free, Trampoline }
import cats.implicits._

import me.romac.freecanvas.{ Canvas => C }
import me.romac.freecanvas.GraphicsF._

class DOMIntepreter(ctx: C.Context2D) extends (GraphicsF ~> Trampoline) {

  import Trampoline.{ done }

  def apply[A](fa: GraphicsF[A]): Trampoline[A] = fa match {
    case SetLineWidth(value)                 => done(ctx.lineWidth = value)
    case SetFillStyle(value)                 => done(ctx.fillStyle = value)
    case SetStrokeStyle(value)               => done(ctx.strokeStyle = value)
    case SetShadowColor(value)               => done(ctx.shadowColor = value)
    case SetShadowBlur(value)                => done(ctx.shadowBlur = value)
    case SetShadowOffsetX(value)             => done(ctx.shadowOffsetX = value)
    case SetShadowOffsetY(value)             => done(ctx.shadowOffsetY = value)
    case SetLineCap(lineCap)                 => done(ctx.lineCap = lineCap.value)
    // case SetComposite(composite)          => done(ctx.composite = composite.value)
    // case SetAlpha(value)                  => done(ctx.alpha = value)
    case BeginPath                           => done(ctx.beginPath())
    case Stroke                              => done(ctx.stroke())
    case Fill                                => done(ctx.fill())
    case Clip                                => done(ctx.clip())
    case LineTo(x, y)                        => done(ctx.lineTo(x, y))
    case MoveTo(x,y )                        => done(ctx.moveTo(x, y))
    case ClosePath                           => done(ctx.closePath())
    case Arc(C.Arc(x, y, r, s, e))           => done(ctx.arc(x, y, r, s, e))
    case Rect(C.Rectangle(x, y, w, h))       => done(ctx.rect(x, y, w, h))
    case FillRect(C.Rectangle(x, y, w, h))   => done(ctx.fillRect(x, y, w, h))
    case StrokeRect(C.Rectangle(x, y, w, h)) => done(ctx.strokeRect(x, y, w, h))
    case ClearRect(C.Rectangle(x, y, w, h))  => done(ctx.clearRect(x, y, w, h))
    case Scale(x, y)                         => done(ctx.scale(x, y))
    case Rotate(angle)                       => done(ctx.rotate(angle))
    case Translate(x, y)                     => done(ctx.translate(x, y))
    case Transform(t)                        => done(ctx.transform(t.m11, t.m12, t.m21, t.m22, t.dx, t.dy))
    case TextAlign                           => done(C.TextAlign(ctx.textAlign))
    case SetTextAlign(align)                 => done(ctx.textAlign = align.value)
    case Font                                => done(ctx.font)
    case SetFont(value)                      => done(ctx.font = value)
    case FillText(text, x, y)                => done(ctx.fillText(text, x, y))
    case StrokeText(text, x, y)              => done(ctx.strokeText(text, x, y))
    case MeasureText(value)                  => done(ctx.measureText(value))
    case Save                                => done(ctx.save())
    case Restore                             => done(ctx.restore())
    case GetImageData(x, y, h, w)            => done(ctx.getImageData(x, y, h, w))
    case PutImageData(data, x, y)            => done(ctx.putImageData(data, x, y))
    case CreateImageData(width, height)      => done(ctx.createImageData(width, height))
    case CreateImageDataCopy(data)           => done(ctx.createImageData(data))
    // case DrawImage(source, x, y)          => done(ctx.drawImage())
    case SetTimeout(value, timeout)          => done {
      timers.setTimeout(timeout) {
        value.foldMap(this).run
      }: Unit
    }
  }
}

