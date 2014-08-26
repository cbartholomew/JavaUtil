/* 
 * Bag.java
 * 
 * Computer Science Harvard University
 * 
 * Modified by:     Christopher Bartholomew
 * Date modified:   09/18/2012
 */

/**
 * An interface for a Bag ADT.
 */
public interface Bag {
    /** 
     * adds the specified item to the Bag.  Returns true on success
     * and false if there is no more room in the Bag.
     */
    boolean add(Object item);

    /** 
     * removes one occurrence of the specified item (if any) from the
     * Bag.  Returns true on success and false if the specified item
     * (i.e., an object equal to item) is not in the Bag.
     */
    boolean remove(Object item);

    /**
     * returns true if the specified item is in the Bag, and false
     * otherwise.
     */
    boolean contains(Object item);

    /**
     * returns true if the calling object contain all of the items in
     * otherBag, and false otherwise.  Also returns false if otherBag 
     * is null or empty. 
     */
    boolean containsAll(Bag otherBag);

    /**
     * returns the number of items in the Bag.
     */
    int numItems();

    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    Object grab();

    /**
     * toArray - return an array containing the current contents of the bag
     */
    Object[] toArray();
    
    /**
     * capacity()
     * returns: integer
     * purpose: returns the maximum number of items that the bag was initialized with
     */
    int capacity();
    
    /**
     *  isEmpty()
     *  returns: boolean
     *  purpose: if the bag is empty, this method will return true
     */
    boolean isEmpty();
    
    /**
     *  numOccur(Object item)
     *  @param1: an object that could represent the number, for which we are looking for
     *  returns: integer
     *  purpose: returns the number of occurrences an object appears in the array bag
     */
    int numOccur(Object item);
    
    /**
     *  addItems(Bag other)
     *  @param1: another instance of a bag object
     *  returns: boolean
     *  purpose: takes the other instance of bag and adds its items into the current instance's bag items, if and only if room is applicable 
     */
    boolean addItems(Bag other);
    
    /**
     * equals(Bag other)
     * @param1: another instance of a bag object
     * returns: boolean
     * purpose: makes the determination that both bags contain equal values - location is no issue
     */
    boolean equals(Bag other);
    
    /**
     * unionWith(Bag other)
     * @param1: another instance of a bag object
     * returns: Bag
     * purpose: when given another bag of items, this method will join both items together, eliminating duplicates, and return a single bag
     */
    Bag unionWith(Bag other);
    
} 
