import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the skip list data structure. 
 * For now, it will be able to insert a new node, delete a new node, 
 * and get a node depending on the key.
 * @author DanielKim
 *
 */
public class Slist {
	
	private static int probability;
	private static List<Node> oldpointers;
	private static List<Node> sentinels;
	private static Node bottom;
	private static Node dummy;
	private static int unum = 0;
	private static int height = 0;
	private static int number = 0;
	
	/**
	 * This constructor creates our skip list. 
	 * We set the probability, stack, and sentinel node here.
	 * @param prob
	 */
	public Slist(int prob) {
		probability = prob;
		oldpointers = new ArrayList<Node>();
		sentinels = new ArrayList<Node>();
		dummy = new Node(-1, -1);
		dummy.setdown(null);
		dummy.setright(null);
		sentinels.add(dummy);
		bottom = dummy;
		number++;
	}
	
	/**
	 * This method inserts a new node into the skip list.
	 * If the node gets selected to be promoted, it happens here.
	 * Note that the variable h will keep track of what level you are working on,
	 * and height is the overall height of our list.
	 * @param key is the key value of the insertion
	 * @param data is the data value that goes with the new node
	 * @return true if success, false otherwise.
	 */
	public static boolean insert(int key, int data) {
		/**
		 * First check to see if this key already exists in our repository.
		 * use a temporary node called search to look through our list.
		 * use the tallest, far left node to being our search.
		 */
		Node search;
		Node below;
		Random rand = new Random();
		int percent = rand.nextInt(101);
		int h = height;
		search = sentinels.get(h);
		while (search != null) {
			while (search.getright() != null) {
				if (search.getright().getkey() > key) {
					break;
				}
				if (search.getright().getkey() == key) {
					oldpointers.removeAll(oldpointers);
					return false;
				}
				search = search.getright();
			}
			oldpointers.add(search);
			search = search.getdown();
			h--;
		}
		/**
		 * Create a new node and add it into the lowest level of the list.
		 */
		Node add = new Node(key, data);
		number++;
		unum++;
		h++;
		Node adding = oldpointers.get(height);
		add.setright(adding.getright());
		add.setdown(null);
		adding.setright(add);
		below = add;
		/**
		 * Check to see if we need to add new nodes above the newly added one.
		 * If a new level is created, make a new sentinel node.
		 * If a level already exists, we need to make sure the node that was pointing 
		 * before needs to be updated in that higher level.
		 */
		while (percent < probability) {
			add = new Node(key, data);
			/**
			 * We make a new sentinel node once we know we have to create a new level.
			 */
			if (h == height) {
				Node newsent = new Node(-h - 2, -1);
				sentinels.add(newsent);
				newsent.setdown(sentinels.get(height));
				newsent.setright(add);
				add.setdown(below);
				add.setright(null);
				below = add;
				percent = rand.nextInt(101);
				height++;
				h++;
				number = number + 2;
			}
			else {
				h++;
				adding = oldpointers.get(height - h);
				add = new Node(key, data);
				Node oldright = adding.getright();
				adding.setright(add);
				add.setright(oldright);
				add.setdown(below);
				below = add;
				percent = rand.nextInt(101);
				number++;
			}
		}
		oldpointers.removeAll(oldpointers);
		return true;
	}
	
	public static boolean delete(int key) {
		/**
		 * First check to see if this key already exists in our repository.
		 * use a temporary node called search to look through our list.
		 * use the tallest, far left node to being our search.
		 */
		Node search;
		int h = height;
		search = sentinels.get(h);
		while (search != null) {
			while (search.getright() != null) {
				if (search.getright().getkey() > key) {
					break;
				}
				else if (search.getright().getkey() == key) {
					oldpointers.add(search);
					break;
				}
				search = search.getright();
			}
			search = search.getdown();
			h--;
		}
		if (oldpointers.isEmpty()) {
			return false;
		}
		h++;
		/**
		 * Once all the placeholders for the old nodes have been found, 
		 * we will iterate through each pointer and update them so that 
		 * we can remove the wanted node.
		 * Should we remove all Nodes in one level, we will be able to delete the sentinel
		 * of the empty level. 
		 * We make sure that we are not able to delete the lowest level ever.
		 */
		while (!oldpointers.isEmpty()) {
			Node placeholder = oldpointers.get(h);
			Node delete = placeholder.getright();
			Node next = delete.getright();
			if (next == null && sentinels.contains(placeholder) && placeholder != bottom) {
				placeholder.setdown(null);
				sentinels.remove(placeholder);
				number--;
				height--;
			}
			placeholder.setright(next);
			delete.setright(null);
			delete.setdown(null);
			oldpointers.remove(h);
			number--;
		}
		unum--;
		return true;
	}
	
	/**
	 * This method looks for a node with the specified key, and returns that node's
	 * data if found. 
	 * @param key is the specified key to look for
	 * @return the data of the key if found. -1 if not found.
	 */
	public static int get(int key) {
		Node search;
		int h = height;
		search = sentinels.get(h);
		/**
		 * we use the exact  same search algorithm to look for the specified node.
		 */
		while (search != null) {
			while (search.getright() != null) {
				if (search.getright().getkey() > key) {
					break;
				}
				if (search.getright().getkey() == key) {
					return search.getright().getdata();
				}
				search = search.getright();
			}
			search = search.getdown();
			h--;
		}
		return -1;
	}
	
	/**
	 * Gets the number of unique nodes in the skip list.
	 * @return the unique number of elements.
	 */
	public static int getUnum() {
		return unum;
	}
	
	/**
	 * Gets the number of nodes in the skip list.
	 * @return the number of elements.
	 */
	public static int getNum() {
		return number;
	}
	
	/**
	 * Prints the entire skip list in layers vertically.
	 * The lowest level is printed in the first column, and the levels increase
	 * via columns.
	 */
	public static void print() {
		Node search;
		Node bottom;
		int h = 0;
		for (int i=0;i<sentinels.size();i++) {
			oldpointers.add(sentinels.get(i));
		}
		bottom = oldpointers.get(0);
		while (bottom.getright() != null) {
			System.out.print(bottom.getright() + " ");
			if (h == height) {
				bottom = bottom.getright();
				System.out.println();
				continue;
			}
			search = oldpointers.get(++h);
			while (search.getright() != null) {
				if (search.getright().compareTo(bottom.getright()) == 0) {
					System.out.print(bottom.getright() + " ");
					oldpointers.set(h, search.getright());
				}
				if (h == height) {
					break;
				}
				h++;
				search = oldpointers.get(h);
			}
			h = 0;
			bottom = bottom.getright();
			System.out.println();
		}
		oldpointers.removeAll(oldpointers);
	}
}
