
public class MMU {
	private static MMU mmu = null;
	private MMU() {
		page_table = new PageTable(NUM_PAGE_FRAMES , PAGE_SIZE);
	}
	public static MMU getMMU() {
		if(mmu == null)
			mmu = new MMU();
		return mmu;
	}
	private PageTable page_table;
	
	private static final int VIRTUAL_ADDRESS_LENGTH = 16;
	private static final int PAGE_SIZE = 1024;
	private static final int NUM_PAGE_FRAMES = 8;
	
	/**
	 * Fulfills a read request. 
	 */
	public int read(int virtual_address) {
		int offset = Integer.parseInt(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(6) , 2);
		int page_table_index = Integer.parseInt(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(0, 6) , 2);
		
		System.out.println("Read is called with the virtual address 0x" + Integer.toHexString(virtual_address).toUpperCase());
		
		int frame_number = page_table.pageLookUpForRead(page_table_index);
		
		System.out.println("...this becomes physical memory address 0x" + Integer.toHexString(Integer.parseInt(Integer.toBinaryString(frame_number).concat(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(6)) , 2)).toUpperCase());
		
		return frame_number;
	}
	
	/**
	 * Fulfills a write request. 
	 */
	public int write(int virtual_address) {
		int offset = Integer.parseInt(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(6) , 2);
		int page_table_index = Integer.parseInt(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(0, 6) , 2);
		
		System.out.println("Write is called with the virtual address 0x" + Integer.toHexString(virtual_address).toUpperCase());
		
		int frame_number = page_table.pageLookUpForWrite(page_table_index);
		
		System.out.println("...this becomes physical memory address 0x" + Integer.toHexString(Integer.parseInt(Integer.toBinaryString(frame_number).concat(String.format("%16s", Integer.toBinaryString(virtual_address)).replace(' ', '0').substring(6)) , 2)).toUpperCase());
		
		return frame_number;
	}
	
	public int getFifoReplacementCount() {
		return page_table.getFifoReplacementCount();
	}
	
	public int getNruReplacementCount() {
		return page_table.getNruReplacementCount();
	}
	
	public int getLruReplacementCount() {
		return page_table.getLruReplacementCount();
	}
	

}
