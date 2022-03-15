

/**
 * A queue class, implemented as a linked list.
 * The nodes of the linked list are implemented as the TaskElement class.
 * 
 * IMPORTANT: you may not use any loops/recursions in this class.
 */
public class TaskQueue {

	TaskElement first;
	TaskElement last;
	
	/**
	 * Constructs an empty queue
	 */
	public TaskQueue(){
		first = null;
		last = null;
	}
	
	/**
	 * Removes and returns the first element in the queue
	 * 
	 * @return the first element in the queue
	 */
	public TaskElement dequeue(){
		if (first != null){
			TaskElement firstPreviousNode = first.prev;
			TaskElement temporaryFirst = first;
			first = firstPreviousNode;
			return temporaryFirst;
		}
		return null;
	}
	
	/**
	 * Returns and does not remove the first element in the queue
	 * 
	 * @return the first element in the queue
	 */
	public TaskElement peek(){
		//Your code comes here
		return first;
	}
	
	/**
	 * Adds a new element to the back of the queue
	 * 
	 * @param node
	 */
	public void enqueue(TaskElement node){
		//Your code comes here
		if (last == null){

		}
	}
	
	/**
	 * 
	 * @return true iff the queue is Empty
	 */
	public boolean isEmpty() {
		return (first==null);
	}
}
	

