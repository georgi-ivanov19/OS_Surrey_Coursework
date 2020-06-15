package com.com1032.assignment.test;

import com.com1032.assignment.*;
import com.com1032.assignment.Process;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Test;

public class TestScheduler {
	ProcessScheduler ps = new ProcessScheduler();

	@Test
	public void testInput1() throws FileNotFoundException {
		List<Process> processes = ps
				.constructReadyQueue(new File("D:\\Eclipse Workspace\\OS_Simulator_Coursework\\TestInput1.txt"));
		assertEquals(
				"Total completion time after running FCFS - 87.00ms\nTotal waiting time after running FCFS - 155.00ms\nAverage waiting time after running FCFS - 25.83ms\n",
				ps.runFCFSScheduler(processes));
		assertEquals(
				"Total completion time after running SJF - 87.00ms\nTotal waiting time after running SJF - 149.00ms\nAverage waiting time after running SJF - 24.83ms\n",
				ps.runSJFScheduler(processes));
		assertEquals(
				"Total completion time after running Priority Scheduling - 87.00ms\nTotal waiting time after running Priority Scheduling - 194.00ms\nAverage waiting time after running Priority Scheduling - 32.33ms\n",
				ps.runPriorityScheduler(processes));
	}

	@Test
	public void testInput2() throws FileNotFoundException {
		List<Process> processes = ps
				.constructReadyQueue(new File("D:\\Eclipse Workspace\\OS_Simulator_Coursework\\TestInput2.txt"));
		assertEquals(
				"Total completion time after running FCFS - 215.00ms\nTotal waiting time after running FCFS - 831.00ms\nAverage waiting time after running FCFS - 75.55ms\n",
				ps.runFCFSScheduler(processes));
		assertEquals(
				"Total completion time after running SJF - 215.00ms\nTotal waiting time after running SJF - 521.00ms\nAverage waiting time after running SJF - 47.36ms\n",
				ps.runSJFScheduler(processes));
		assertEquals(
				"Total completion time after running Priority Scheduling - 215.00ms\nTotal waiting time after running Priority Scheduling - 1264.00ms\nAverage waiting time after running Priority Scheduling - 114.91ms\n",
				ps.runPriorityScheduler(processes));
	}

}
