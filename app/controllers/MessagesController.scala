package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor
import domain.Message
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

/**
 * Created by cehauber on 12/05/2014.
 */

object MessagesController extends Controller with MongoController{

  def collection: JSONCollection = db.collection[JSONCollection]("messages")

  def list() = Action.async {
    // let's do our query
    val cursor: Cursor[Message] = collection.find(Json.obj()).cursor[Message]

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[Message]] = cursor.collect[List]()

    // everything's ok! Let's reply with the array
    futureUsersList.map { messages =>
      Ok(Json.toJson(messages))
    }
  }

  def add(author: String, body: String) = Action.async {
    val message = Message(author, body)
    // insert the user
    val futureResult = collection.insert(message)
    // when the insert is performed, send a OK 200 result
    futureResult.map(_ => Ok)
  }

  def delete(author: String) = Action.async {
    val futureResult = collection.remove(Json.obj("author" -> author))
    futureResult.map(_ => Ok)
  }
}
