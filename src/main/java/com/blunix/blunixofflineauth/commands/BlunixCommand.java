package com.blunix.blunixofflineauth.commands;

import org.bukkit.command.CommandSender;

public abstract class BlunixCommand {
   private String name;
   private String helpMessage;
   private String permission;
   private String usageMessage;
   private int argumentLength;
   boolean isConsoleCommand;
   boolean isPlayerCommand;
   boolean isUniversalCommand;

   public abstract void execute(CommandSender var1, String[] var2);

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getHelpMessage() {
      return this.helpMessage;
   }

   public void setHelpMessage(String helpMessage) {
      this.helpMessage = helpMessage;
   }

   public String getPermission() {
      return this.permission;
   }

   public void setPermission(String permission) {
      this.permission = permission;
   }

   public String getUsageMessage() {
      return this.usageMessage;
   }

   public void setUsageMessage(String usageMessage) {
      this.usageMessage = usageMessage;
   }

   public int getArgumentLength() {
      return this.argumentLength;
   }

   public void setArgumentLength(int argumentLength) {
      this.argumentLength = argumentLength;
   }

   public boolean isConsoleCommand() {
      return this.isConsoleCommand;
   }

   public void setConsoleCommand(boolean isConsoleCommand) {
      this.isConsoleCommand = isConsoleCommand;
   }

   public boolean isPlayerCommand() {
      return this.isPlayerCommand;
   }

   public void setPlayerCommand(boolean isPlayerCommand) {
      this.isPlayerCommand = isPlayerCommand;
   }

   public boolean isUniversalCommand() {
      return this.isUniversalCommand;
   }

   public void setUniversalCommand(boolean isUniversalCommand) {
      this.isUniversalCommand = isUniversalCommand;
   }
}
