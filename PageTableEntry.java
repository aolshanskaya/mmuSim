
public class PageTableEntry {
	private int page_frame_num;
	private boolean present_absent;
	private int time_when_loaded;
	private boolean modified;
	private int referenced;
	
	public PageTableEntry(int frame_num , boolean present_absent , int time) {
		page_frame_num = frame_num;
		this.present_absent = present_absent;
		time_when_loaded = time;
	}
	
	public int getPageFrameNum() {
		return page_frame_num;
	}
	
	public boolean getPresentAbsentBit() {
		return present_absent;
	}
	
	public int getTimeWhenLoaded() {
		return time_when_loaded;
	}
	
	public boolean getModifiedBit() {
		return modified;
	}
	
	public int getLastReference() {
		return referenced;
	}
	
	public void setLastReference(int time) {
		referenced = time;
	}
	public void setModifiedBit(boolean dirty) {
		modified = dirty;
	}
	
	public void setPageFrameNum(int frame_num) {
		page_frame_num = frame_num;
	}
	
	public void setPresentAbsentBit(boolean bit) {
		present_absent = bit;
	}
	
	public void setTimeWhenLoaded(int time) {
		time_when_loaded = time;
	}
}
