package com.com1032.assignment;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
	static DecimalFormat df = new DecimalFormat("##0.00");
	static Memory memory = new Memory();
	static Scanner reader;
	static String command;
	static ProcessScheduler ps = new ProcessScheduler();

	public static void main(String[] args) throws InterruptedException, IOException {

		Boot();
		while (true) {
			printOptions();
			reader = new Scanner(System.in);
			command = reader.nextLine().toLowerCase();
			if (command.equals("1")) {
				TimeUnit.MILLISECONDS.sleep(200);
				System.out.println("Please give the name of the file you would like to use:");
				String fileName = reader.nextLine();
				List<Process> readyQueue = ps
						.constructReadyQueue(new File("D:\\Eclipse Workspace\\OS_Simulator_Coursework\\" + fileName));
				while (true) {
					printAlgorithmOptions();
					String command1 = reader.nextLine().toLowerCase();
					if (command1.equals("1")) {
						memory.addMemoryFromListOfProcesses(readyQueue);
						TimeUnit.MILLISECONDS.sleep(300);
						System.out.println(ps.runFCFSScheduler(readyQueue));
						TimeUnit.MILLISECONDS.sleep(500);
						break;
					} else if (command1.equals("2")) {
						memory.addMemoryFromListOfProcesses(readyQueue);
						TimeUnit.MILLISECONDS.sleep(300);
						System.out.println(ps.runSJFScheduler(readyQueue));
						TimeUnit.MILLISECONDS.sleep(500);
						break;
					} else if (command1.equals("3")) {
						memory.addMemoryFromListOfProcesses(readyQueue);
						TimeUnit.MILLISECONDS.sleep(300);
						System.out.println(ps.runPriorityScheduler(readyQueue));
						TimeUnit.MILLISECONDS.sleep(500);
						break;
					} else if (command1.equals("4")) {
						memory.addMemoryFromListOfProcesses(readyQueue);
						TimeUnit.MILLISECONDS.sleep(300);
						ps.runAll(readyQueue);
						TimeUnit.MILLISECONDS.sleep(500);
						break;
					} else if (command1.equals("stop")) {
						break;
					} else {
						TimeUnit.MILLISECONDS.sleep(300);
						System.err.println("Please enter a valid number");
						TimeUnit.MILLISECONDS.sleep(300);
					}
				}
				while (true) {
					printProcessInfoOption();
					String command1 = reader.nextLine().toLowerCase();
					if (command1.equals("y")) {
						TimeUnit.MILLISECONDS.sleep(300);
						System.out.println("ID\tP\tAT\tRT\tMemory\tFrames");
						for (Process p : readyQueue) {
							System.out.println(p.info());
						}
						break;
					} else if (command1.equals("n")) {
						break;
					} else {
						TimeUnit.MILLISECONDS.sleep(300);
						System.err.println("Invalid input!");
					}

				}
			} else if (command.equals("2")) {
				TimeUnit.MILLISECONDS.sleep(300);
				System.out.println(memory.checkMemory());
				TimeUnit.MILLISECONDS.sleep(500);
			} else if (command.equals("3")) {
				TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("Are you sure you want to wipe memory? (Y/N?)");
				while (true) {
					String command1 = reader.nextLine().toLowerCase();
					if (command1.equals("n")) {
						break;
					} else if (command1.equals("y")) {
						memory.wipeMemory();
						TimeUnit.MILLISECONDS.sleep(300);
						System.out.println("Memory has been wiped");
						break;
					} else {
						TimeUnit.MILLISECONDS.sleep(300);
						System.err.println("Invalid input!\n");
						TimeUnit.MILLISECONDS.sleep(300);
					}
				}
			} else if (command.equals("4")) {
				TimeUnit.MILLISECONDS.sleep(400);
				System.out.println(memory.pageTable());
				TimeUnit.MILLISECONDS.sleep(400);
			} else if (command.equals("shut down")) {
				reader.close();
				System.out.println("Shutting down...");
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println("Shut down!");
				break;
			} else {
				TimeUnit.MILLISECONDS.sleep(300);
				System.err.println("Invalid input!");
				TimeUnit.MILLISECONDS.sleep(300);
				System.out.println();
			}
		}
	}

	public static void Boot() throws InterruptedException, IOException {
		String start = "Booting started...";
		String delete = "\r ";
		String deleete2 = "\b ";
		for (int i = 0; i <= 4; i++) {
			delete += "                  ";
			deleete2 += "                  ";
			System.out.print(start);
			TimeUnit.MILLISECONDS.sleep(500);
			System.out.print(delete);
			System.out.print(deleete2);
			TimeUnit.MILLISECONDS.sleep(500);
		}
		System.out.println();
		System.out.println("System specifications:");
		System.out.println("CPU: \"Intel Core i7 8750H\"");
		System.out.println("RAM: " + Memory.CAPACITY + "MB");
		System.out.println("HARD-DRIVE: 256GB");
		System.out.println("I: Keyboard");
		System.out.println("O: Monitor");
		TimeUnit.MILLISECONDS.sleep(2000);
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}

	public static void printOptions() {
		System.out.println(
				"\nPlease select an option (1, 2, 3 or 4) then click enter or type \"Shut down\" to terminate the program\n1. Run process scheduler simulation\n2. Check memory\n3. Wipe memory\n4. Print page table");
	}

	public static void printAlgorithmOptions() {
		System.out.println(
				"\nPlease select a scheduling algorithm to run (1, 2, 3 or 4) or type stop to return\n1. FCFS\n2. SJF\n3. Priority Scheduling\n4. All");
	}

	public static void printProcessInfoOption() {
		System.out.println("\nWould you like to see information about the processes that were just scheduled? (Y / N)");
	}
}
