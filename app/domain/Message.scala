package domain

import play.api.libs.json.Json


/**
 * Created by cehauber on 12/05/2014.
 */
case class Message(author: String, body: String)

object Message {
  implicit val messageReadJson = Json.reads[Message]
  implicit val messageWriteJson = Json.writes[Message]
}