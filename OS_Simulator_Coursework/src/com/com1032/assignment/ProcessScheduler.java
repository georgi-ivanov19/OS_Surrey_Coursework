package com.com1032.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ProcessScheduler {
	private Semaphore s = new Semaphore(1);
	private DecimalFormat df = new DecimalFormat("##0.00");

	public List<Process> constructReadyQueue(File file) throws FileNotFoundException {
		List<Process> readyQueue = new ArrayList<Process>();
		Scanner scan = new Scanner(file);
		Integer processID;
		Double arrivalTime;
		Double cpuBurstTime;
		Integer memory;
		Integer priority;
		while (scan.hasNextLine()) {
			String[] command = scan.nextLine().split("\\s+");
			processID = Integer.parseInt(command[0]);
			priority = Integer.parseInt(command[1]);
			arrivalTime = Double.parseDouble(command[2]);
			cpuBurstTime = Double.parseDouble(command[3]);
			memory = Integer.parseInt(command[4]);
			readyQueue.add(new Process(processID, priority, arrivalTime, cpuBurstTime, memory));
		}
		scan.close();
		return readyQueue;
	}

	private Double calculateAverageWaitingTimeSJFandPS(List<Process> processes) {
		int size = processes.size();
		return calculateTotalWaitingTimeSJFandPS(processes) / size;
	}

	private Double calculateAverageWaitingTimeFCFS(List<Process> processes) {
		int size = processes.size();
		return calculateTotalWaitingTimeFCFS(processes) / size;
	}

	public String runPriorityScheduler(List<Process> readyQueue) {
		StringBuilder output = new StringBuilder();
		readyQueue.sort(new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return p1.getPriority().compareTo(p2.getPriority());
			}

		});
		Collections.reverse(readyQueue);
		output.append("Total completion time after running Priority Scheduling - ")
				.append(df.format(calculateCompletionTime(readyQueue))).append("ms\n");
		output.append("Total waiting time after running Priority Scheduling - ")
				.append(df.format(calculateTotalWaitingTimeSJFandPS(readyQueue))).append("ms\n");
		output.append("Average waiting time after running Priority Scheduling - ")
				.append(df.format(calculateAverageWaitingTimeSJFandPS(readyQueue))).append("ms\n");
		return output.toString();
	}

	public String runFCFSScheduler(List<Process> readyQueue) throws FileNotFoundException {
		StringBuilder output = new StringBuilder();
		readyQueue.sort(new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return p1.getArrivalTime().compareTo(p2.getArrivalTime());
			}
		});
		output.append("Total completion time after running FCFS - ")
				.append(df.format(calculateCompletionTime(readyQueue))).append("ms\n");
		output.append("Total waiting time after running FCFS - ")
				.append(df.format(calculateTotalWaitingTimeFCFS(readyQueue))).append("ms\n");
		output.append("Average waiting time after running FCFS - ")
				.append(df.format(calculateAverageWaitingTimeFCFS(readyQueue))).append("ms\n");
		return output.toString();
	}

	public void runAll(List<Process> readyQueue) throws InterruptedException {
		Thread A = new Thread(new Runnable() {
			public void run() {
				try {
					s.acquire();
					System.out.println(
							"\nThread A has aquired the List of processes and is performing the FCFS scheduler\n");
					TimeUnit.MILLISECONDS.sleep(1000);
					System.out.println(runFCFSScheduler(readyQueue));
					TimeUnit.MILLISECONDS.sleep(500);
					System.out.println("Thread A has released the List of processes\n");
					TimeUnit.MILLISECONDS.sleep(500);
					s.release();
				} catch (FileNotFoundException | InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
		Thread B = new Thread(new Runnable() {
			public void run() {
				try {
					s.acquire();
					System.out.println(
							"Thread B has aquired the List of processes and is performing the SJF scheduler\n");
					TimeUnit.MILLISECONDS.sleep(1000);
					System.out.println(runSJFScheduler(readyQueue));
					TimeUnit.MILLISECONDS.sleep(500);
					System.out.println("Thread B has released the List of processes\n");
					s.release();
				} catch (FileNotFoundException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread C = new Thread(new Runnable() {
			public void run() {
				try {
					s.acquire();
					System.out.println(
							"Thread C has aquired the List of processes and is performing the SJF scheduler\n");
					TimeUnit.MILLISECONDS.sleep(1000);
					System.out.println(runPriorityScheduler(readyQueue));
					TimeUnit.MILLISECONDS.sleep(500);
					System.out.println("Thread C has released the List of processes\n");
					s.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		A.start();
		B.start();
		C.start();
		A.join();
		B.join();
		C.join();
	}

	public String runSJFScheduler(List<Process> readyQueue) throws FileNotFoundException {
		DecimalFormat df = new DecimalFormat("##.00");
		StringBuilder output = new StringBuilder();
		readyQueue.sort(new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return p1.getBurstTime().compareTo(p2.getBurstTime());
			}
		});
		output.append("Total completion time after running SJF - ")
				.append(df.format(calculateCompletionTime(readyQueue))).append("ms\n");
		output.append("Total waiting time after running SJF - ")
				.append(df.format(calculateTotalWaitingTimeSJFandPS(readyQueue))).append("ms\n");
		output.append("Average waiting time after running SJF - ")
				.append(df.format(calculateAverageWaitingTimeSJFandPS(readyQueue))).append("ms\n");
		return output.toString();
	}

	private static Double calculateCompletionTime(List<Process> processes) {
		Double completionTime = 0.0;
		for (Process p : processes) {
			completionTime += p.getBurstTime();
		}
		return completionTime;
	}

	private static Double calculateTotalWaitingTimeFCFS(List<Process> processes) {
		Double totalWaitingTime = 0.0;
		for (int i = 0; i < processes.size(); i++) {
			for (int j = 1; j <= i; j++) {
				totalWaitingTime += processes.get(j - 1).getBurstTime();
			}
			if (i > 0) {
				totalWaitingTime -= processes.get(i).getArrivalTime();
			}
		}
		return totalWaitingTime;
	}

	private static Double calculateTotalWaitingTimeSJFandPS(List<Process> processes) {
		Double totalWaitingTime = 0.0;
		for (int i = 0; i < processes.size(); i++) {
			for (int j = 1; j <= i; j++) {
				totalWaitingTime += processes.get(j - 1).getBurstTime();
			}
		}
		return totalWaitingTime;
	}
}
