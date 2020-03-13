package com.minosiants

import java.nio.charset.StandardCharsets

import cats.data.Kleisli
import cats.effect.IO
import scodec.bits.BitVector
import scodec._
import scodec.codecs._

package object pencil {

  type Smtp[A] = Kleisli[IO, Request, A]

  val CRLF: BitVector = ascii.encode("\r\n").getOrElse(BitVector.empty)

  implicit class ExtraStringOps(str: String) {
    def toBase64: String = {
      BitVector.view(str.getBytes()).toBase64
    }
    def toBitVector: BitVector = {
      ascii.encode(str).getOrElse(BitVector.empty)
    }
  }

}
