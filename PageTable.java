import java.util.*;

public class PageTable {
	
	private int num_pages;
	private int time;
	
	private int fifo_counter;
	private int nru_counter; 
	private int lru_counter; 
	
	private HashMap<Integer , PageTableEntry> page_table;
	
	public PageTable(int num_pages , int page_size) {
		this.num_pages = num_pages;
		time = 0;
		
		fifo_counter = 0;
		nru_counter = 0;
		lru_counter = 0;
		
		page_table = new HashMap<>();
	}
	
	public int getFifoReplacementCount() {
		return fifo_counter;
	}
	
	public int getNruReplacementCount() {
		return nru_counter;
	}
	
	public int getLruReplacementCount() {
		return lru_counter;
	}
	
	public int pageLookUpForRead(int page_index){
		PageTableEntry entry = checkTable(page_index);
		
		if(entry != null) {
			time++;
			entry.setLastReference(time);
			return entry.getPageFrameNum();
		}
		
		System.out.println("...page fault");
		
		if(page_table.size() < num_pages) {
			insertIntoTableIfNotFull(page_index);
		}else {
			lruPageReplacement(page_index);
		}
		
		entry = checkTable(page_index);
		entry.setLastReference(time);
		System.out.println("...loading data into page frame " + entry.getPageFrameNum());
		
		time++;
		return entry.getPageFrameNum();
	}
	
	public int pageLookUpForWrite(int page_index){
		PageTableEntry entry = checkTable(page_index);
		
		if(entry != null) {
			time++;
			entry.setModifiedBit(true);
			entry.setLastReference(time);
			return entry.getPageFrameNum();
		}
		
		System.out.println("...page fault");
		
		if(page_table.size() < num_pages) {
			insertIntoTableIfNotFull(page_index);
		}else {
			lruPageReplacement(page_index);
			System.out.println("...writing modified data to disk");
		}
		
		entry = checkTable(page_index);
		entry.setModifiedBit(true);
		entry.setLastReference(time);
		System.out.println("...loading data into page frame " + entry.getPageFrameNum());
		
		time++;
		return entry.getPageFrameNum();
	}
	
	/**
	 * Checks if page is in page table.
	 */
	private PageTableEntry checkTable(int page_index) {
		if(page_table.containsKey(page_index))
			return page_table.get(page_index);
		return null;
	}
	
	private void insertIntoTableIfNotFull(int page_index) {
		if(page_table.isEmpty())
			page_table.put(page_index, new PageTableEntry(0 , true , time));
		
		else if(page_table.size() < num_pages) {
			int largest_used_frame = -1;
			List<PageTableEntry> entries = new ArrayList<>(page_table.values());
			
			for(PageTableEntry entry : entries) {
				if(entry.getPageFrameNum() > largest_used_frame) {
					largest_used_frame = entry.getPageFrameNum();
				}
			}
			page_table.put(page_index, new PageTableEntry(largest_used_frame+1 , true , time));
		}
	}

	private void fifoPageReplacement(int page_index) {
		List<Integer> entries = new ArrayList<>(page_table.keySet());
		
		int oldest = entries.get(0);
		for(Integer entry : entries) {
			if(page_table.get(entry).getTimeWhenLoaded() < page_table.get(oldest).getTimeWhenLoaded())
				oldest = entry;
		}
		
		page_table.put(page_index, page_table.remove(oldest));
		page_table.get(page_index).setTimeWhenLoaded(time);
		
		fifo_counter++;
	}
	
	private void nruPageReplacement(int page_index) {
		List<Integer> entries = new ArrayList<>(page_table.keySet());
		
		List<Integer> clean = new Stack<>();
		List<Integer> dirty = new Stack<>();
		
		for(Integer entry : entries) {
			if(page_table.get(entry).getModifiedBit())
				dirty.add(entry);
			else
				clean.add(entry);
		}
		
		int to_remove;
		if(clean.isEmpty())
			to_remove = dirty.get((int) (Math.random()*dirty.size()));
		else
			to_remove = clean.get((int)(Math.random()*clean.size()));
		
		page_table.put(page_index, page_table.remove(to_remove));
		page_table.get(page_index).setTimeWhenLoaded(time);
		
		nru_counter++;
	}
	
	private void lruPageReplacement(int page_index) {
		List<Integer> entries = new ArrayList<>(page_table.keySet());
		
		int last_referenced = entries.get(0);
		
		for(Integer entry : entries) {
			if(page_table.get(entry).getLastReference() < page_table.get(last_referenced).getLastReference())
				last_referenced = entry;
		}
		
		page_table.put(page_index, page_table.remove(last_referenced));
		page_table.get(page_index).setTimeWhenLoaded(time);
		
		lru_counter++;
	}
}
