package structures;

import org.xml.sax.InputSource;

import javax.swing.text.html.HTML;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.*;


//https://www.cs.rutgers.edu/courses/112/classes/spring_2018_venugopal/progs/prog3/prog3.html
/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 *
 */
public class Tree {

	/**
	 * Root node
	 */
	TagNode root=null;

	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;

	/**
	 * Initializes this tree object with scanner for input HTML file
	 *
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object.
	 *
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		// stack for tag nodes
		Stack<TagNode> existing_Tags = new Stack<TagNode>();

		sc.nextLine();

		// root node always starts with HTML... a doiiiiii
		root = new TagNode("html", null, null);

		existing_Tags.push(root); // pushes initial <html> tag into stack

		while(sc.hasNextLine()) { // traverse through html

			String str = sc.nextLine();
			Boolean isTag = false;

			if(str.charAt(0) == '<') { // check to see if it is a tag
				if(str.charAt(1) == '/') { // checks to see if its an end tag
					existing_Tags.pop();
					continue;
				} else { // if it is a beginning tag
					str = str.replace("<", "");
					str = str.replace(">", "");
					isTag = true;
				}
			}

			TagNode temp = new TagNode(str, null, null);

			if(existing_Tags.peek().firstChild == null) {
				existing_Tags.peek().firstChild = temp;
			} else {
				TagNode ptr = existing_Tags.peek().firstChild;
				while(ptr.sibling != null) {
					ptr = ptr.sibling;
				}
				ptr.sibling = temp;
			}
			// checks to see if is tag
			if(isTag) {
				existing_Tags.push(temp);
			}
		}

	}

	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 *
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		// we only allow specific replace operations to occur
		// b, em, ol, ul
		if ((oldTag.equalsIgnoreCase("b") && newTag.equalsIgnoreCase("em"))
				|| (oldTag.equalsIgnoreCase("ol") && newTag.equalsIgnoreCase("ul"))
				|| (oldTag.equalsIgnoreCase("em") && newTag.equalsIgnoreCase("b"))
				|| (oldTag.equalsIgnoreCase("ul") && newTag.equalsIgnoreCase("ol"))
				)
		{
			replace_recur(root, oldTag, newTag);
		}
	}
	// private method
	private void replace_recur(TagNode root, String oldTag, String newTag) {

		// traverse through the DOM elements
		// traverse through all siblings
		// traverse through firstChild

		for (TagNode ptr=root; ptr != null; ptr=ptr.sibling) {
			//System.out.println(ptr.tag);

			// replace b with em
			if (ptr.tag.equalsIgnoreCase(oldTag)  && ptr.firstChild != null) {
				ptr.tag = newTag;
			}

			if (ptr.firstChild != null) {
				replace_recur(ptr.firstChild, oldTag, newTag);
			}
		}



	}

	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 *
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		if(row <= 0){ // row number has to be a valid entry
			return;
		}else{
			boldRow_Recur(root, row);
		}
	}

	// private method
	private TagNode boldRow_Recur(TagNode root, int row) {
		TagNode original = root;
		TagNode temp = null;
		TagNode TagNode_bold = new TagNode("b", null, null);

		if (root == null) { // if empty
			return null;
		}

		if (root.tag.equalsIgnoreCase("table")) { // check for "table entry"
			root = root.firstChild; // first row

			for(int i = 0; i < row-1; i++ ) { // loop through to find row
				if (root.sibling == null) { // no siblings
					return null;
				}
				root = root.sibling;
			}// end for
			root = root.firstChild; // first td

			while (root != null){
				// swap elements
				temp = root.firstChild;
				root.firstChild = TagNode_bold;
				TagNode_bold.firstChild = temp;

				TagNode_bold = new TagNode("b", null, null);

				root = root.sibling; // increment
			}// end while
			return original;
		}// end if
		// recursive calls
		boldRow_Recur(root.firstChild, row);
		boldRow_Recur(root.sibling, row);
		return root;
	}



	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and,
	 * in addition, all the li tags immediately under the removed tag are converted to p tags.
	 *
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
		// we only allow specific replace operations to occur
		if (tag.equalsIgnoreCase("p")  ||
				tag.equalsIgnoreCase("em") ||
				tag.equalsIgnoreCase("b")) {
			removeTagPvt_P_EM_B(root, tag);
		}

		// for OL and UL tags
		if (tag.equalsIgnoreCase("ol") ||
				tag.equalsIgnoreCase("ul")) {
			removeTagPvt_ol_ul(root, tag);
		}
	}

	// Category 1
	private void removeTagPvt_P_EM_B(TagNode root, String tag) {
		if(root == null){ // if empty
			return;
		}
		// remove tag em, b and p
		for (TagNode ptr=root; ptr != null; ptr=ptr.sibling) {
			if ((ptr.tag.equalsIgnoreCase("p") || ptr.tag.equalsIgnoreCase("em") ||
					ptr.tag.equalsIgnoreCase("b")) && ptr.firstChild != null) {
				ptr.tag = ptr.firstChild.tag;
				ptr.firstChild = null;
			}

			if (ptr.firstChild != null) {
				// recursive call
				removeTagPvt_P_EM_B(ptr.firstChild, tag);
			}
		}// end for
	}

	// Category 2
	private void removeTagPvt_ol_ul(TagNode root, String target){
		if(root == null){ // if empty
			return;
		}
		// recursive call
		removeTagPvt_ol_ul(root.firstChild, target);

		if(root.sibling != null && root.sibling.tag.equals(target)){ // sibling
			TagNode ptr = root.sibling.firstChild;
			while(ptr.sibling != null){
				ptr.tag = "p";
				ptr = ptr.sibling;
			}
			ptr.tag = "p";
			ptr.sibling = root.sibling.sibling;
			root.sibling = root.sibling.firstChild;
		}

		if(root.firstChild != null && root.firstChild.tag.equals(target)){ // first child
			TagNode ptr = root.firstChild.firstChild;
			while(ptr.sibling != null){
				ptr.tag = "p";
				ptr = ptr.sibling;
			}
			ptr.tag = "p";
			ptr.sibling = root.firstChild.sibling;
			root.firstChild = root.firstChild.firstChild;
		}

		// recursive call
		removeTagPvt_ol_ul(root.sibling, target);
	}

	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 *
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag){
		if (tag.equals("em") || tag.equals("b")) {
			root = addTag_recur(this. root, word, tag);
		}
	}

	// private recursion method
	private TagNode addTag_recur(TagNode node, String word, String tag){

		if (node.sibling != null) {
			node.sibling = addTag_recur(node.sibling, word, tag);
		}

		if (node.firstChild != null) {
			node.firstChild = addTag_recur(node.firstChild, word, tag);
		}

		// base case
		if (node.tag.contains(word)) {
			TagNode newTag = new TagNode(tag, node, node.sibling);
			node.sibling = null;
			return newTag;
		}

		return node;
	}

	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 *
	 * @return HTML string, including new lines.
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}

	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");
			}
		}
	}

	/**
	 * Prints the DOM tree.
	 *
	 */
	public void print() {
		print(root, 1);
	}

	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			}
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}