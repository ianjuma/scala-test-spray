package com.json.fix.util

import org.slf4j.LoggerFactory

trait AppLog {
  def log = LoggerFactory.getLogger(this.getClass)
}

trait AppCCPrinter {
  override def toString = getString.getCaseClassString(this)
}

object getString {
  private[util] def getCaseClassString(cc: AnyRef): String = {
    (cc.getClass.getDeclaredFields map (f => {
      f.setAccessible(true)
      f.getName + "=" + getAnyString(f.get(cc), "None")
    })).mkString(cc.getClass.getSimpleName + "[", ";", "]")
  }

  def getAnyString(any: Any, default: String = "") : String = {
    any match {
      case Some(x) => x.toString
      case None => default
      case x => x.toString
    }
  }
}
