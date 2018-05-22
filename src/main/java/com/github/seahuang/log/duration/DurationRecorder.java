package com.github.seahuang.log.duration;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface DurationRecorder {
	/**
	 * 全局设置是否记录时间
	 * Set global default whether or not log duration
	 * @param defaultLogDuration
	 */
	void setDefaultLogDuration(Boolean defaultLogDuration);
	/**
	 * 开始计时（也可能不计时，看配置）
	 * Start(may not) counting the time by configuration
	 * @param jp Provide meta information
	 */
	public void start(JoinPoint jp);
	/**
	 * 结束计时（如果没有开始，那么也没有结束，返回空）
	 * <p>Stop(not if not started) counting the time</p>
	 * @return million second from the point started， return null if not counted
	 */
	public Long stop();
}
