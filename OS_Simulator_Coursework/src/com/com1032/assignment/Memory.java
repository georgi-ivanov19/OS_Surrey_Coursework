package com.com1032.assignment;

import java.text.DecimalFormat;
import java.util.List;

public class Memory {

	public static final int CAPACITY = 4; // the capacity of the memory defined in MBs
	private static final int PAGE_TABLE_ENTRIES = 1024 * CAPACITY * 2; // *2 to compensate for the size of a single page
	private static final int NUMBER_OF_FRAMES = 1024 * CAPACITY * 2; // *2 to compensate for the size of a single frame
	private static final int PHYSICAL_MEMORY_SIZE = Frame.FRAME_SIZE * NUMBER_OF_FRAMES; // capacity turned into bytes
	public static final int PAGE_SIZE = 512;
	private DecimalFormat df = new DecimalFormat("##0.00");
	private int lastUsedFrame;
	private Page[] pageTable;
	private Frame[] physicalMemory;
	private int freeFrames;
	private int nextPTEntry = 0;

	public Memory() {
		// create the page table
		pageTable = new Page[PAGE_TABLE_ENTRIES];
		for (int i = 0; i < PAGE_TABLE_ENTRIES; i++)
			pageTable[i] = new Page();

		// allocate the physical memory
		physicalMemory = new Frame[NUMBER_OF_FRAMES];
		for (int i = 0; i < NUMBER_OF_FRAMES; i++)
			physicalMemory[i] = new Frame();

		// initialize the last used frame number
		lastUsedFrame = -1;

		// initialize the number of frames
		freeFrames = NUMBER_OF_FRAMES;
	}

	/*
	 * adding to the memory from a given list of processes
	 */
	public void addMemoryFromListOfProcesses(List<Process> processes) {
		for (Process p : processes) {
			assignToMemory(p.getPages());
		}
	}

	// return number of free frames
	private int getFreeFrames() {
		return this.freeFrames;
	}

	/*
	 * completely wipe the memory and free all of the frames and pages
	 */
	public void wipeMemory() {
		for (int i = 0; i < lastUsedFrame - 1; i++) {
			physicalMemory[i] = new Frame();
		}
		lastUsedFrame = -1;
		freeFrames = NUMBER_OF_FRAMES;
		pageTable = new Page[PAGE_TABLE_ENTRIES];
		for (int i = 0; i < PAGE_TABLE_ENTRIES; i++)
			pageTable[i] = new Page();
		nextPTEntry = 0;

	}

	/*
	 * returns a string showing free and used memory as well as free and used frames
	 */
	public String checkMemory() {
		StringBuilder output = new StringBuilder();
		double freeMemory = (this.getFreeFrames() * Frame.FRAME_SIZE) / 1024;
		double usedMemory = PHYSICAL_MEMORY_SIZE / 1024 - freeMemory;
		output.append("\nFree Memory - ").append(df.format(freeMemory)).append("kb/")
				.append(PHYSICAL_MEMORY_SIZE / 1024).append("kb\n");
		output.append("Used Memory - ").append(df.format(usedMemory)).append("kb/").append(PHYSICAL_MEMORY_SIZE / 1024)
				.append("kb\n");
		output.append("Free Frames - ").append(this.getFreeFrames()).append("/").append(Memory.NUMBER_OF_FRAMES)
				.append("\n");
		output.append("Used Frames - ").append(Memory.NUMBER_OF_FRAMES - this.getFreeFrames()).append("/")
				.append(Memory.NUMBER_OF_FRAMES).append("\n");
		return output.toString();
	}

	// return the number of the nextFreeFrame
	public int nextFreeFrame() {
		return lastUsedFrame + 1;
	}

	// return wether memory is full or not
	public boolean isFull() {
		return freeFrames == 0;
	}

	/*
	 * construct the page table in the form of a string ready for printing
	 */
	public String pageTable() {
		StringBuilder output = new StringBuilder();
		if (nextPTEntry == 0) {
			output.append("Page table is empty!");
		} else {
			output.append("P | F\n");
			for (int i = 0; i < nextPTEntry; i++) {

				if (pageTable[i].isValid()) {
					output.append(pageTable[i].getPageNumber()).append(" | ").append(pageTable[i].getFrameNumber())
							.append("\n");
				}
			}
		}
		return output.toString();
	}

	private void assignToMemory(List<Page> list) throws OutOfMemoryError {
		if (list.size() > freeFrames) {
			throw new OutOfMemoryError("NO REMAINING MEMORY!!! YOU MUST REBOOT!");
		}
		for (Page p : list) {
			lastUsedFrame++;
			freeFrames--;
			pageTable[nextPTEntry].setMapping(p.getPageNumber(), lastUsedFrame);
			nextPTEntry++;
		}
	}
}
