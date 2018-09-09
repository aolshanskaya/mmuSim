
public class Main {

	/**
	 * Runs MMU simulator with a hard-coded input set of addresses and read-write indicators. 
	 * 
	 * Currently set to use LRU replacement algorithm. 
	 * Other options commented out. 
	 */
	public static void main(String[] args) {
		MMU mmu = MMU.getMMU();
		
		String[] test_set = {"3e9" , "43b7" , "ed47" , "41e3" , "f69d" ,
				"169d" , "e6c3" , "e456" , "1e5e" , "7b2" ,
				"8e1d" , "a4e" , "37b" , "4308" , "ba8f" ,
				"ed9e" , "d98" , "2dd" , "ca" , "123d"};
		
		int[] read_write = {1,1,1,0,1,
				1,1,1,1,0,
				0,1,0,1,0,
				0,1,0,0,0};
		
		for(int i = 0 ; i < test_set.length ; i++) {
			if(read_write[i] == 1)
				mmu.write(Integer.parseInt(test_set[i] , 16));
			else
				mmu.read(Integer.parseInt(test_set[i] , 16));
			System.out.println();
		}
		System.out.println("Number of page replacements performed with LRU: " + mmu.getLruReplacementCount());
		//System.out.println("Number of page replacements performed with LRU: " + mmu.getNruReplacementCount());
		//System.out.println("Number of page replacements performed with LRU: " + mmu.getFifoReplacementCount());
	}
}
