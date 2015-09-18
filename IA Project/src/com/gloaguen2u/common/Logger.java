package com.gloaguen2u.common;

public class Logger {
	private static int dispLevel;
	
	private static enum Level {
		INFO(0, "I"),
		WARNING(1, "W"),
		ERROR(2, "E"),
		RESULT(3, "R");
		
		private final int lvl;
		private final String text;
		Level(int lvl, String text) {this.lvl = lvl; this.text = text;}
		public String getText() {
			return this.text;
		}
		public boolean display() {
			return this.lvl >= Logger.dispLevel;
		}
	}
	private static void log(Level l, String s) {
		if(l.display()) {
			System.out.print("["+l.getText()+"] ");
			System.out.println(s);
		}
	}
	
	public static void setLogLevel(int i) {
		dispLevel = i;
	}
	
	public static void info(String s) {
		log(Level.INFO, s);
	}
	
	public static void warn(String s) {
		log(Level.WARNING, s);
	}
	
	public static void error(String s) {
		log(Level.ERROR, s);
	}
	
	public static void result(String s) {
		System.err.println("[R] " + s);
	}
}
