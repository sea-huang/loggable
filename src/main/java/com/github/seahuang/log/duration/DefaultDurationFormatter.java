package com.github.seahuang.log.duration;

public class DefaultDurationFormatter implements DurationFormatter {
	
	public String format(Long total){
		if(total == null){
			return "";
		}
		int millisecs = (int)(total % 1000);
		total = total / 1000;
		int secs = (int)(total % 60);
		total = total / 60;
		int mins = (int)(total % 60);
		total = total / 60;
		int hours = (int)(total % 24);
		total = total / 24;
		int days = total.intValue();
		
		StringBuilder result = new StringBuilder();
		if(days > 0){
			result.append(days).append("D ");
		}
		if(hours > 0){
			result.append(hours).append("H ");
		}
		if(mins > 0){
			result.append(mins).append("m ");
		}
		result.append(secs).append(".").append(millisecs).append("s");
		
		return result.toString();
	}
}
