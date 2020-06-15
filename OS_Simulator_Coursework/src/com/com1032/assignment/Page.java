package com.com1032.assignment;

public class Page {
	private int pageNumber;
	private int frameNumber;
	private boolean isValid;

	public Page(int pageNumber) {
		this.pageNumber = pageNumber;
		isValid = false;
		frameNumber = -1;
	}

	public Page() {
		isValid = false;
		frameNumber = -1;
	}

	public boolean isValid() {
		return isValid;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setMapping(int pageNumber, int frameNumber) {
		this.frameNumber = frameNumber;
		this.pageNumber = pageNumber;
		isValid = true;
	}

	public int getPageNumber() {
		return pageNumber;
	}
}
