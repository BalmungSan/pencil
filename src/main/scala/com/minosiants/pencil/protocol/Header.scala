/*
 * Copyright 2020 Kaspar Minosiants
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minosiants.pencil
package protocol

import cats.Show
import cats.syntax.show._

sealed trait Header extends Product with Serializable

object Header {

  final case class `MIME-Version`(value: String = "1.0") extends Header

  final case class `Content-Type`(
      contentType: ContentType,
      params: Map[String, String] = Map.empty
  ) extends Header

  final case class `Content-Transfer-Encoding`(
      mechanism: Encoding
  ) extends Header

  final case class `Content-Disposition`(
      contentDisposition: ContentDisposition,
      params: Map[String, String] = Map.empty
  ) extends Header

  private def paramsToValues(params: Map[String, String]): String =
    params
      .iterator
      .map {
        case (key, value) => s"${key}=${value}"
      }.mkString(";")

  implicit lazy val headerShow: Show[Header] = Show.show {
    case `MIME-Version`(value) =>
      s"MIME-Version: $value"

    case `Content-Type`(ct, params) =>
      s"Content-Type: ${ct.show}; ${paramsToValues(params)}"

    case `Content-Transfer-Encoding`(mechanism) =>
      s"Content-Transfer-Encoding: ${mechanism.show}"

    case `Content-Disposition`(contentDisposition, params) =>
      s"Content-Disposition: ${contentDisposition.show}; ${paramsToValues(params)}"
  }
}
