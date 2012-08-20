package com.luizabrahao.msc.sim.util;

import java.util.concurrent.Callable;

public class CallableAdapter {

	public static Runnable runnable(final Callable<Void> callable) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					callable.call();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
}
