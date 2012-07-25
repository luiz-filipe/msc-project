package com.luizabrahao.msc.ants.agent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.luizabrahao.msc.ants.env.AntEnvironmentFactory;
import com.luizabrahao.msc.ants.env.PheromoneNode;

public class ThreadTest {
	@Test
	public void testAgentInterruptionDetection() {
		final ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		final PheromoneNode[][] grid = AntEnvironmentFactory.createPheromoneNodeGrid(3, 3);
		final AntAgent a = new AntAgent("a-01", WorkerAntType.TYPE,  grid[1][1], true);
		
		Future<Void> future = executor.submit(a);
		
		try {
			future.get(3, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			// allow task to exit
		} catch (InterruptedException e) {
			// allow task to exit
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			future.cancel(false);
		}
	}
}
