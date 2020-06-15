/**
 * Demonstrating process creation in Java.
 *
 * Figure 3.13
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Eighth Edition
 * Copyright John Wiley & Sons - 2010.
 */
package com.com1032.assignment;

import java.util.ArrayList;
import java.util.List;

public class Process {
	private Integer id = 0;
	private Double arrivalTime = 0.0;
	private Double burstTime = 0.0;
	private Integer memory;
	private Integer priority;
	private List<Page> pages;

	public Process(Integer id, Integer priority, Double arrivalTime, Double burstTime, Integer memory)
			throws IllegalArgumentException {
		if (id <= 0 || burstTime <= 0 || memory <= 0) {
			throw new IllegalArgumentException("ID, burst time and memory cannot be 0 or less!");
		}
		if (arrivalTime < 0) {
			throw new IllegalArgumentException("Arrival time cannot be less than 0");
		}
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.memory = memory;
		this.priority = priority;
		constructPages();

	}

	public int framesRequired() {
		return memory / Frame.FRAME_SIZE;
	}

	public String info() {
		StringBuilder output = new StringBuilder();
		output.append(this.id).append("\t").append(this.priority).append("\t").append(this.arrivalTime).append("\t")
				.append(this.burstTime).append("\t").append(this.memory).append("\t").append(this.framesRequired())
				.append("\n");
		return output.toString();
	}

	public List<Page> getPages() {
		return this.pages;
	}

	private void constructPages() {
		int pageNumber = 0;
		pages = new ArrayList<Page>();
		for (int i = 0; i < (int) (memory / Memory.PAGE_SIZE); i++) {
			pages.add(new Page(pageNumber));
			pageNumber++;
		}
	}

	public Integer getPriority() {
		return this.priority;
	}

	public Integer getId() {
		return this.id;
	}

	public Double getBurstTime() {
		return this.burstTime;
	}

	public Double getArrivalTime() {
		return this.arrivalTime;
	}

	public Integer getMemory() {
		return this.memory;
	}
}
