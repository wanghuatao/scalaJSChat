package chat

import scala.scalajs.js.String
import org.scalajs.dom
import org.scalajs.dom.ErrorEvent
import org.scalajs.dom.Event
import org.scalajs.dom.HTMLInputElement
import org.scalajs.dom.MessageEvent
import org.scalajs.dom.CloseEvent

/**
 *
 */
object ChatExample {

    val d = dom.document
    val chatField = d.getElementById("playground")
    val errorField = d.getElementById("error")
    val infoField = d.getElementById("info")
    val ws = new dom.WebSocket("ws://127.0.0.1:9898")

    def main(): Unit = {}

    def populate(data: String) {
        Console.println("data " + data)
        val out = data.split("#")
        val msg = out(1)
        Console.println("msg " + msg)
        val prefix = out(0).toString
        prefix match {
            case "chat " => {
                val p = d.createElement("p")
                p.className = "small"
                p.innerHTML = s"${msg}"
                chatField.innerHTML = ""
                chatField.appendChild(p)
            }
            case "error " => {
                errorField.innerHTML = ""
                errorField.innerHTML = "<span class=\"badge\">" + msg + "</span>"
            }
            case "info " => {
                infoField.innerHTML = ""
                val alert = d.createElement("div")
                alert.className = "alert alert-success"
                alert.innerHTML = msg
                infoField.appendChild(alert)
            }
        }
    }

    def initWS() {
        ws.onmessage = (x: MessageEvent) => populate(x.data.toString)
        ws.onopen = (x: Event) => {}
        ws.onerror = (x: ErrorEvent) => Console.println("some error has occured " + x.message)
        ws.onclose = (x: CloseEvent) => {}
    }

    def sendData() {
        val data = (d.getElementById("chat").asInstanceOf[HTMLInputElement]).value
        ws.send(data)
        (d.getElementById("chat").asInstanceOf[HTMLInputElement]).value = ""
    }
}
