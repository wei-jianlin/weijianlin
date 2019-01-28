package scala.org

import scala.util.Random

object MatchingPattern {

  def main(args: Array[String]): Unit = {
    val x = Random.nextInt(10)
    def matchTest(x : Int) : String = x match {
      case 0 => "0"
      case 1 => "1"
      case _ => "大于1"
    }
    println(matchTest(x))
    def showNotification(notification: Notification): String = {
      notification match {
        case Email(email, title, _) =>
          s"You got an email from $email with title: $title"
        case SMS(number, message) =>
          s"You got an SMS from $number! Message: $message"
        case VoiceRecording(name, link) =>
          s"you received a Voice Recording from $name! Click the link to hear it: $link"
      }
    }
    val someSms = SMS("12345", "Are you there?")
    val someVoiceRecording = VoiceRecording("Tom", "voicerecording.org/id/123")

    println(showNotification(someSms))  // prints You got an SMS from 12345! Message: Are you there?

    println(showNotification(someVoiceRecording))  // you received a Voice Recording from Tom! Click the link to hear it: voicerecording.org/id/123

    //在图中case Email(email, _, _) if importantPeopleInfo.contains(email)，只有email在重要人物列表中才匹配模式。
    def showImportantNotification(notification: Notification, importantPeopleInfo: Seq[String]): String = {
      notification match {
        case Email(email, _, _) if importantPeopleInfo.contains(email) =>
          "You got an email from special someone!"
        case SMS(number, _) if importantPeopleInfo.contains(number) =>
          "You got an SMS from special someone!"
        case other =>
          showNotification(other) // nothing special, delegate to our original showNotification function
      }
    }

    val importantPeopleInfo = Seq("867-5309", "jenny@gmail.com")

    val someSms2 = SMS("867-5309", "Are you there?")
    val someVoiceRecording2 = VoiceRecording("Tom", "voicerecording.org/id/123")
    val importantEmail = Email("jenny@gmail.com", "Drinks tonight?", "I'm free after 5!")
    val importantSms = SMS("867-5309", "I'm here! Where are you?")

    println(showImportantNotification(someSms2, importantPeopleInfo))
    println(showImportantNotification(someVoiceRecording2, importantPeopleInfo))
    println(showImportantNotification(importantEmail, importantPeopleInfo))
    println(showImportantNotification(importantSms, importantPeopleInfo))

    //匹配类型
    def goIdle(device: Device) = device match {
      case p: Phone => p.screenOff
      case c: Computer => c.screenSaverOn
    }
    println(goIdle(Computer("1")))
  }
}
abstract class Notification

case class Email(sender: String, title: String, body: String) extends Notification

case class SMS(caller: String, message: String) extends Notification

case class VoiceRecording(contactName: String, link: String) extends Notification

abstract class Device
case class Phone(model: String) extends Device{
  def screenOff = "Turning screen off"
}
case class Computer(model: String) extends Device {
  def screenSaverOn = "Turning screen saver on..."
}