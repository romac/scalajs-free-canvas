
package me.romac

import cats.free.Free

package object freecanvas {
  type Graphics[A] = Free[GraphicsF, A]
}

