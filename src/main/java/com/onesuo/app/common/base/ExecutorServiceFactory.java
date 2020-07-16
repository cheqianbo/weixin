package com.onesuo.app.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceFactory {
	private static Logger logger = LoggerFactory.getLogger(ExecutorServiceFactory.class);
	private static ThreadPoolExecutor notifyExecutors;

	/**
	 * 标记线程创建工厂，修改自 Executors.DefaultThreadFactory 类.
	 */
	static class TaggedThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final String namePrefix;

		TaggedThreadFactory(String tag) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = tag + "-" + poolNumber.getAndIncrement() + "-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}

	/**
	 * 线程池初始化，只有第一次初始化有效
	 */
	public static synchronized void init(int nMessageThreads) {
		if (notifyExecutors == null && nMessageThreads > 0) {
			notifyExecutors = getFixedThreadPool(nMessageThreads, "notify");
			logger.info("notify executor's coreSize={} ", nMessageThreads);
		}
	}

	/**
	 * 提交通知任务
	 */
	public static Future<?> submitNotifyTask(Runnable task, String tag) {
		checkNotNull(notifyExecutors);
		return notifyExecutors.submit(wrapTask(task, tag));
	}

	/**
	 * 获取固定最少数量的任务线程池
	 */
	private static ThreadPoolExecutor getFixedThreadPool(int coreSize, String tag) {
		// 改自 Executors.newFixedThreadPool() 函数
		return new ThreadPoolExecutor(coreSize, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new TaggedThreadFactory(tag));
	}

	private static void checkNotNull(ExecutorService executor) {
		if (executor == null) {
			throw new RuntimeException("you should init the " + "ExecutorServiceManager before you use it!");
		}
	}

	/**
	 * 封装线程任务
	 *
	 * @param cmd 原始任务
	 * @param tag 线程标记
	 * @return 封装后的任务
	 */
	private static Runnable wrapTask(Runnable cmd, String tag) {
		final long startTime = System.currentTimeMillis();
		return () -> {
			final long queueDuration = System.currentTimeMillis() - startTime;
			if (queueDuration - startTime > 100) {
				// 可能会做一些额外的处理
			}
			final Thread currentThread = Thread.currentThread();
			final String oldName = currentThread.getName();
			currentThread.setName(oldName + "::" + tag);
			try {
				cmd.run();
			}
			catch (Exception e) {
				logger.error("", e);
			}
			finally {
				currentThread.setName(oldName);
			}
		};
	}
}