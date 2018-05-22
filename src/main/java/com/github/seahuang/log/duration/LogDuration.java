package com.github.seahuang.log.duration;

/**
 * Log Duration settings
 */
public enum LogDuration {
	/**
	 * Explicitly set to log the duration, ignore global configuration
	 * 显式指定需要记录方法运行的时间，忽略全局配置
	 */
	YES, 
	/**
	 * Explicitly set <b>not</b> to log the duration, ignore global configuration
	 * 显式指定<b>不</b>需要记录方法运行的时间，忽略全局配置
	 */
	NO,
	/**
	 * If global set defined, use global configuration, or default to NO
	 * 如果存在全局配置，使用全局配置。否则默认同NO
	 */
	DEFAULT;
}
