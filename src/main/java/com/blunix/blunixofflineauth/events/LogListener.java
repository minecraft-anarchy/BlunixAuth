package com.blunix.blunixofflineauth.events;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class LogListener extends AbstractFilter {
   public void registerFilter() {
      Logger logger = (Logger)LogManager.getRootLogger();
      logger.addFilter(this);
   }

   public Result filter(LogEvent event) {
      return event == null ? Result.NEUTRAL : this.shouldBeLogged(event.getMessage().getFormattedMessage());
   }

   public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
      return this.shouldBeLogged(msg.getFormattedMessage());
   }

   public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
      return this.shouldBeLogged(msg);
   }

   public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
      return msg == null ? Result.NEUTRAL : this.shouldBeLogged(msg.toString());
   }

   private Result shouldBeLogged(String message) {
      return !message.contains("auth") ? Result.NEUTRAL : Result.DENY;
   }
}
