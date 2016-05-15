
package me.romac
package freecanvas

import org.scalajs.dom

object Canvas {

  type Context2D = dom.CanvasRenderingContext2D

  sealed abstract class LineCap(val asString: String)

  object LineCap {
    final case object Butt   extends LineCap("butt")
    final case object Round  extends LineCap("round")
    final case object Square extends LineCap("square")

    final def fromString(str: String): Option[LineCap] = Some(str) collect {
      case Butt   .asString => Butt
      case Round  .asString => Round
      case Square .asString => Square
    }

    final def unsafeFromString(str: String): LineCap =
      fromString(str).get
  }

  sealed abstract class LineJoin(val asString: String)

  object LineJoin {
    final case object Bevel extends LineJoin("bevel")
    final case object Round extends LineJoin("round")
    final case object Miter extends LineJoin("miter")

    final def fromString(str: String): Option[LineJoin] = Some(str) collect {
      case Bevel .asString => Bevel
      case Round .asString => Round
      case Miter .asString => Miter
    }

    final def unsafeFromString(str: String): LineJoin =
      fromString(str).get
  }

  sealed abstract class Composite(val asString: String)

  object Composite {
    final case object SourceOver      extends Composite("source-over")
    final case object SourceIn        extends Composite("source-in")
    final case object SourceOut       extends Composite("source-out")
    final case object SourceAtop      extends Composite("source-atop")
    final case object DestinationOver extends Composite("destination-over")
    final case object DestinationIn   extends Composite("destination-in")
    final case object DestinationOut  extends Composite("destination-out")
    final case object DestinationAtop extends Composite("destination-atop")
    final case object Lighter         extends Composite("lighter")
    final case object Copy            extends Composite("copy")
    final case object Xor             extends Composite("xor")
    final case object Multiply        extends Composite("multiply")
    final case object Screen          extends Composite("screen")
    final case object Overlay         extends Composite("overlay")
    final case object Darken          extends Composite("darken")
    final case object Lighten         extends Composite("lighten")
    final case object ColorDodge      extends Composite("color-dodge")
    final case object ColorBurn       extends Composite("color-burn")
    final case object HardLight       extends Composite("hard-light")
    final case object SoftLight       extends Composite("soft-light")
    final case object Difference      extends Composite("difference")
    final case object Exclusion       extends Composite("exclusion")
    final case object Hue             extends Composite("hue")
    final case object Saturation      extends Composite("saturation")
    final case object Color           extends Composite("color")
    final case object Luminosity      extends Composite("luminosity")

    final def fromString(str: String): Option[Composite] = Some(str) collect {
      case SourceOver      .asString => SourceOver
      case SourceIn        .asString => SourceIn
      case SourceOut       .asString => SourceOut
      case SourceAtop      .asString => SourceAtop
      case DestinationOver .asString => DestinationOver
      case DestinationIn   .asString => DestinationIn
      case DestinationOut  .asString => DestinationOut
      case DestinationAtop .asString => DestinationAtop
      case Lighter         .asString => Lighter
      case Copy            .asString => Copy
      case Xor             .asString => Xor
      case Multiply        .asString => Multiply
      case Screen          .asString => Screen
      case Overlay         .asString => Overlay
      case Darken          .asString => Darken
      case Lighten         .asString => Lighten
      case ColorDodge      .asString => ColorDodge
      case ColorBurn       .asString => ColorBurn
      case HardLight       .asString => HardLight
      case SoftLight       .asString => SoftLight
      case Difference      .asString => Difference
      case Exclusion       .asString => Exclusion
      case Hue             .asString => Hue
      case Saturation      .asString => Saturation
      case Color           .asString => Color
      case Luminosity      .asString => Luminosity
    }

    final def unsafeFromString(str: String): Composite =
      fromString(str).get
  }

  final case class Transform(
    m11: Double,
    m12: Double,
    m21: Double,
    m22: Double,
    dx: Double,
    dy: Double
  )

  sealed abstract class TextAlign(val asString: String)

  object TextAlign {
    final case object Left   extends TextAlign("left")
    final case object Right  extends TextAlign("right")
    final case object Center extends TextAlign("center")
    final case object Start  extends TextAlign("start")
    final case object End    extends TextAlign("end")

    final def fromString(str: String): Option[TextAlign] = Some(str) collect {
      case Left   .asString => Left
      case Right  .asString => Right
      case Center .asString => Center
      case Start  .asString => Start
      case End    .asString => End
    }

    final def unsafeFromString(str: String): TextAlign =
      fromString(str).get
  }

  type TextMetrics = dom.raw.TextMetrics

  type ImageData = dom.raw.ImageData

  type ImageSource = dom.raw.HTMLElement

  final case class Arc(
    x: Double,
    y: Double,
    radius: Double,
    startAngle: Double,
    endAngle: Double
  )

  final case class Rectangle(
    x: Double,
    y: Double,
    w: Double,
    h: Double
  )

}

