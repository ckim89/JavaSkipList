/**
 * This is the node class for the skip list.
 * It will have pointers to nodes to the right and down.
 * Holds a key and a two pointers.
 * @author DanielKim
 *
 */

public class Node implements Comparable<Object> {
	
	Node down;
	Node right;
	int data;
	int key;
	
	/**
	 * This constructs our node. We set the key in our constructor.
	 * For now, we have our data type as an integer.
	 * @param key is the key value for the node.
	 */
	public Node(int key, int data) {
		this.key = key;
		this.data = data;
	}
	
	/**
	 * This method sets the down pointer of the node.
	 * @param down
	 */
	public void setdown(Node down) {
		this.down = down;
	}
	
	/**
	 * This method sets the right pointer of the node.
	 * @param right
	 */
	public void setright(Node right) {
		this.right = right;
	}
	
	/**
	 * Gets the key of the node.
	 * @return the key of the node.
	 */
	public int getkey() {
		return this.key;
	}
	
	/**
	 * Gets the data from the node.
	 * @return the data of the node.
	 */
	public int getdata() {
		return this.data;
	}
	
	/**
	 * This method returns the node that is below the current node.
	 * @return the node below this current node
	 */
	public Node getdown() {
		return this.down;
	}
	
	/**
	 * This method returns the node that is to the right of the current node.
	 * @return the node to the right of the current node
	 */
	public Node getright() {
		return this.right;
	}
	
	public int compareTo(Object node) {
		Node temp;
		if (node instanceof Node) {
			temp = (Node) node;
			if (temp.getkey() > this.key) {
				return -1;
			}
			else if (temp.getkey() < this.key) {
				return 1;
			}
			else return 0;
		}
		return -1;
	}
	
	/**
	 * The toString method for a node.
	 * @return String the key and the data in the node.
	 */
	public String toString() {
		return "{" + this.key + ", " + this.data + "}";
	}
}
