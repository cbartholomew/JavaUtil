/* 
 * ArrayBag.java
 * 
 * Computer Science Harvard University
 * 
 * Modified by:     Christopher Bartholomew
 * Date modified:   09/18/2012
 */

import java.util.*;

/**
 * An implementation of a Bag ADT using an array.
 */
public class ArrayBag implements Bag {
    /** 
     * The array used to store the items in the bag.
     */
    private Object[] items;
    
    /** 
     * The number of items in the bag.
     */
    private int numItems;
    
    public static final int DEFAULT_MAX_SIZE = 50;
    
    /**
     * Default, no-arg constructor - creates a new, empty ArrayBag with 
     * the default maximum size.
     */
    public ArrayBag() {
        items = new Object[DEFAULT_MAX_SIZE];
        numItems = 0;
    }
    
    /** 
     * A constructor that creates a new, empty ArrayBag with the specified
     * maximum size.
     */
    public ArrayBag(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException("maxSize must be > 0");
        items = new Object[maxSize];
        numItems = 0;
    }
    
    /** 
     * add - adds the specified item to the Bag.  Returns true on
     * success and false if there is no more room in the Bag.
     */
    public boolean add(Object item) {
        if (item == null)
            throw new IllegalArgumentException("item must be non-null");
        if (numItems == items.length)
            return false;              // no more room!
        else {
            items[numItems] = item;
            numItems++;
            return true;
        }
    }
    
    /** 
     * remove - removes one occurrence of the specified item (if any)
     * from the Bag.  Returns true on success and false if the
     * specified item (i.e., an object equal to item) is not in the Bag.
     */
    public boolean remove(Object item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] != null && items[i].equals(item)) {
                // Shift the remaining items left by one.
                System.arraycopy(items, i+1, items, i, numItems-i-1);
                items[numItems-1] = null;
                
                numItems--;
                return true;
            }
        }
        
        return false;  // item not found
    }
    
    /**
     * contains - returns true if the specified item is in the Bag, and
     * false otherwise.
     */
    public boolean contains(Object item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] != null && items[i].equals(item))
                return true;
        }
        
        return false;
    }
    
    /**
     * containsAll - does this ArrayBag contain all of the items in
     * otherBag?  Returns false if otherBag is null or empty. 
     */
    public boolean containsAll(Bag otherBag) {
        if (otherBag == null || otherBag.numItems() == 0)
            return false;
        
        Object[] otherItems = otherBag.toArray();
        for (int i = 0; i < otherItems.length; i++) {
            if (!contains(otherItems[i]))
                return false;
        }
        
        return true;
    }
    
    /**
     * numItems - returns the number of items in the Bag.
     */
    public int numItems() {
        return this.numItems;
    }
    
    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    public Object grab() {
        if (numItems == 0)
            throw new NoSuchElementException("the bag is empty");
        int whichOne = (int)(Math.random() * numItems);
        return items[whichOne];
    }
    
    /**
     * toString - converts this ArrayBag into a readable String object.
     * Overrides the Object version of this method.
     */
    public String toString() {
        String str = "{";
        
        for (int i = 0; i < numItems; i++)
            str = str + " " + items[i];
        str = str + " }";
        
        return str;
    }
    
    /**
     * toArray - return an array containing the current contents of the bag
     */
    public Object[] toArray() {
        Object[] copy = new Object[numItems];
        System.arraycopy(items, 0, copy, 0, numItems);
        return copy;
    }
    
    /**
     * III.1.a - capacity()
     * returns: integer
     * purpose: returns the maximum number of items that the bag was initialized with
     */
	public int capacity() {
		
		// return the length of current items for which was initialized
		return this.items.length;
	}

    /**
     *  II.1.b - isEmpty()
     *  returns: boolean
     *  purpose: if the bag is empty, this method will return true
     */
	public boolean isEmpty() {
		
		// ternary operation: does our numItems equate to zero? 
		return (this.numItems() == 0) ? true : false;
	}

    /**
     *  II.1.c - numOccur(Object item)
     *  @param1: an object that could represent the number, for which we are looking for
     *  returns: integer
     *  purpose: returns the number of occurrences an object appears in the array bag
     */
	public int numOccur(Object item) {
		
		// check input
		if(item == null || item.equals(""))
			throw new IllegalArgumentException("parameter item can not be null!");
		
		// init counter to zero
		int numCounter = 0;
		
		// does the item equate? 
		for(int i=0,len=this.numItems();i<len;i++)
			numCounter = (this.items[i].equals(item)) ? numCounter + 1 : numCounter;
			
		// return the result
		return numCounter;
	}

    /**
     *  II.1.d - addItems(Bag other)
     *  @param1: another instance of a bag object
     *  returns: boolean
     *  purpose: takes the other instance of bag and add its items into the current instance's bag items, 
     *  if and only if room is applicable 
     */
	public boolean addItems(Bag other) {
		
		// check the parameter to confirm value is there
		if(other == null || other.equals(""))
			throw new IllegalArgumentException("parameter other can not be null!");
		
		// if the difference in this.items is less than other items - return false
		if((this.capacity() - this.numItems()) < other.numItems())
			return false;
		
		// create an array of other items
		Object[] otherItems = other.toArray();
		
		// add the additional items to the current bag
		for(int i=0,len=otherItems.length;i<len;i++)
			this.add(otherItems[i]);
			
		// return true to confirm 
		return true;
		
	}

    /**
     * II.1.e equals(Bag other)
     * @param1: another instance of a bag object
     * returns: boolean
     * purpose: makes the determination that both bags contain equal values - location is no issue
     * special: Used this.numOccur(item) instead of this.contains all due to scary bold face print
     */
	public boolean equals(Bag other) {		
		// check the parameter to confirm value is there
		if(other == null || other.equals(""))
			throw new IllegalArgumentException("parameter other can not be null!");
		
		// length isn't the same, assume already different and return false
		if(this.numItems() != other.numItems())
			return false;
		
		// create an array out of the other bag 
		Object[] items = other.toArray();
		
		// iterate through  other bag checking to see if numOccur on each instance matches. 
		for(int i=0,len=items.length;i<len;i++)
			if(this.numOccur(items[i]) != other.numOccur(items[i]))
				return false;
		
		// no false was returned while using the method that was "strongly encouraged" :P
		return true;
	}

    /**
     * II.1.f unionWith(Bag other)
     * @param1: another instance of a bag object
     * returns: Bag
     * purpose: when given another bag of items, this method will join both items together, eliminating duplicates, and return a single bag
     */
	public Bag unionWith(Bag other) {
		
		// check the parameter to confirm value is there
		if(other == null || other.equals(""))
			throw new IllegalArgumentException("parameter other can not be null!");
		
		// crate new bag
		Bag unionBag = new ArrayBag(this.capacity() + other.capacity());
		
		if(this.numItems() == 0 && other.numItems() == 0)
			return unionBag;
		
		// iterate through the current bag, only add which is not present already
		for(int i=0,len=this.numItems();i<len;i++)
			if(!unionBag.contains(this.items[i]))
				unionBag.add(this.items[i]);
		
		// iterate through the other bag, only add which is not present already
		for(int j=0,len=other.numItems();j<len;j++)
			if(!unionBag.contains(other.toArray()[j]))
			unionBag.add(other.toArray()[j]);
		
		return unionBag;
	}
    
		
    /* Test the ArrayBag implementation. */
    public static void main(String[] args) {
    	    	
        // Create a Scanner object for user input.
        Scanner in = new Scanner(System.in);
        
        // Create an ArrayBag named bag1.
        System.out.print("Size of bag 1: ");
        int size = in.nextInt();
        Bag bag1 = new ArrayBag(size);
                        
        in.nextLine();    // consume the rest of the line
        
        // Read in strings, add them to bag1, and print out bag1.
        String itemStr;        
        for (int i = 0; i < size; i++) {
            System.out.print("item " + i + ": ");
            itemStr = in.nextLine();
            bag1.add(itemStr);
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();
        

        
        // Select a random item and print it.
        Object item = bag1.grab();
        System.out.println("grabbed " + item);
        System.out.println();
        
        // Iterate over the objects in bag1, printing them one per
        // line.
        Object[] items = bag1.toArray();
        for (int i = 0; i < items.length; i++)
            System.out.println(items[i]);
        System.out.println();
        
        // Get an item to remove from bag1, remove it, and reprint the bag.
        System.out.print("item to remove: ");
        itemStr = in.nextLine();
        if (bag1.contains(itemStr))
            bag1.remove(itemStr);
        System.out.println("bag 1 = " + bag1);
        System.out.println();
    }
    
    

}
