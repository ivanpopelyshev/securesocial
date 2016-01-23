package securesocial.core.authenticator

import scala.concurrent.{ ExecutionContext, Future }
import scala.reflect.ClassTag
import play.api.mvc.{ Request, AnyContent }
import play.api.Play
import scala.collection.JavaConverters._;
import play.api.mvc.RequestHeader

case class RouterInfo(
  authPrefix: String,
  domain: Option[String]);

/**
 * Defines a router
 */
trait AuthRouter {
  def getDomain(request: RequestHeader): Option[String]

  def getAuthPrefix(request: RequestHeader): String

  def getAll(request: RequestHeader): RouterInfo
}

object AuthRouter {
  lazy val authQueryParam = Play.current.configuration.getString("securesocial.router.authQueryParam").getOrElse("auth")
  lazy val authHeader = Play.current.configuration.getString("securesocial.router.authHeader").getOrElse("X-Matroid-Auth")
  lazy val authDefault = Play.current.configuration.getString("securesocial.router.authDefault").getOrElse("b")

  lazy val configs = Play.current.configuration.getConfigList("domains").map {
    _.asScala.map {
      case x =>
        (x.getString("host").get, x.getString("domain").get)
    }.toMap
  }.getOrElse(Map())

  class Default extends AuthRouter {
    override def getAuthPrefix(request: RequestHeader) = {
      request.headers.get(authHeader).orElse(request.queryString.get(authQueryParam).map(_(0))).getOrElse(authDefault)
    }

    override def getDomain(request: RequestHeader) = {
      configs.get(request.host)
    }

    override def getAll(request: RequestHeader) = {
      RouterInfo(getAuthPrefix(request), getDomain(request))
    }
  }
}

