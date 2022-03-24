import java.util.Arrays;

/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class TaskElement,
 * and the heap is ordered according to the Task instances wrapped by those objects.
 * 
 * IMPORTANT: Except the percolation (private) functions and the constructors, no single function may loop/recurse through all elements in the heap.
 * 		
 * 
 *
 */
public class TaskHeap{

	public static int capacity=200; // the maximum number of elements in the heap
	/**
	 * The array in which the elements are kept according to the heap order.
	 * The following must always hold true:
	 * 			if i < size then heap[i].heapIndex == i
	 */
	TaskElement[] heap;
	int size; // the number of elements in the heap, it is required that size <= heap.length
	
	/**
	 * Creates an empty heap which can contain 'capacity' elements.
	 */
	public TaskHeap(){
		//Your code comes here
		heap = new TaskElement[]{};
		size = 0;
	}
	
	/**
	 * Constructs a heap that may contain 'capacity' many elements, from a given array of TaskElements, of size at most 'capacity'. 
	 * This should be done according to the "build-heap" function studied in class.
	 * NOTE: the heapIndex field of each TaskElement might be -1 (or incorrect).
	 * You may NOT use the insert function of heap.
	 * In this function you may use loops.
	 * 
	 */
	public TaskHeap(TaskElement[] arr){
		size = arr.length;
		heap = Arrays.copyOf(arr, 200);
		this.heap[size] = heap[0];
		this.heap[0] = null;
		int nodesCount = size/2;
		for (int i = nodesCount; i > 0; i--){
			percolateDown(i, heap[i]);
		}
	}

    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size(){
		return size;
    }
    
    /**
     * Inserts a given element into the heap.
     *
     * @param e - the element to be inserted.
     */
    public void insert(TaskElement e){
        //Your code comes here
		if (size == capacity) {
			System.out.println("The heap is full, try remove first");
			return;
		}
		heap[size+1] = e;
		size++;
		percolateUp(e);
	}
    
	/**
	 * Returns and does not remove the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement findMax(){
		//Your code comes here
		return heap[1];
    }
    
	/**
	 * Returns and removes the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement extractMax() {
		//Your code comes here
		TaskElement maxTaskElement = heap[1];
		remove(1);
		return maxTaskElement;
    }
    
    /**
     * Removes the element located at the given index.
     * 
	 * Note: this function is not part of the standard heap API.
	 *		 Make sure you understand how to implement it, and why it is required.
	 *       There are several ways this function could be implemented. 
	 * 		 No matter how you choose to implement it, you need to consider the different possible edge cases.
     * @param index
     */
    public void remove(int index){
        //Your code comes here
		int i = index;
		if(isHeapEmpty()) {
			System.out.println("Unable to remove any object, the heap is empty");
		} else if(isIndexOutOfRange(index)) {
			System.out.println("Index is out of heaps range");
		} else {
			assignLastTaskElementToIndex(index);
			int ancestorIndex = getAncestorIndex(index);
			if (heap[1] == null || ancestorIndex == 0 || heap[i] == null) return;
			if (heap[ancestorIndex].t.compareTo(heap[i].t) >= 0){
				percolateDown(i, heap[i]);
			} else {
				percolateUp(heap[i]);
			}
		}
    }

	private void assignLastTaskElementToIndex(int index) {
		heap[index] = heap[size];
		heap[index].heapIndex = index;
		heap[size] = null;
		size--;
	}

	private int getAncestorIndex(int index){
		double ancestorIndex = Math.floor(((double) index)/2) ;
		if (ancestorIndex == 0) return 1;
		return (int) ancestorIndex;
	}

	private boolean isIndexOutOfRange(int i){
		return i > size;
	}

	private void percolateUp(TaskElement newTask){
		if (!isHeapEmpty()){
			int i = size;
			while (i > 1) {
				int ancestorIndex = getAncestorIndex(i);
				if (heap[ancestorIndex].t.compareTo(newTask.t) < 0){
					switchNodes(ancestorIndex, i);
					i = ancestorIndex;
				} else {
					i = 0;
				}
			}
		}
	}

	private void percolateDown(int i, TaskElement task){
		if (isLeaf(i)) { // A leaf ?
			task.heapIndex = i;
			heap[i] = task;
		} else if (isOnlyChild(i)) { // Is only leaf ?
			if (heap[2*i].t.compareTo(task.t) > 0) {
				heap[i] = heap[2 * i];
				task.heapIndex = 2*i;
				heap[2 * i] = task;
			} else {
				task.heapIndex = i;
				heap[i] = task;
			}
		} else if (hasBothDescendants(i)){ // Is node have two descendants ?
			int biggerDescendantIndex = isBigger(heap[2*i], heap[2*i+1]) ? 2*i : 2*i+1;
			if (isBigger(heap[biggerDescendantIndex], task)) {
				heap[i] = heap[biggerDescendantIndex];
				heap[i].heapIndex = i;
				percolateDown(biggerDescendantIndex, task);
			} else {
				task.heapIndex = i;
				heap[i] = task;
			}
		}
	}

	private boolean isBigger(TaskElement leftDescendant, TaskElement rightDescendant){
		return leftDescendant.t.compareTo(rightDescendant.t) >= 0;
	}

	private boolean isHeapEmpty() {
		return size == 0;
	}

	private void switchNodes(int ancestorIndex, int descendantIndex){
		TaskElement ancestor = heap[ancestorIndex];
		TaskElement biggerDescendant = heap[descendantIndex];
		ancestor.heapIndex = descendantIndex;
		biggerDescendant.heapIndex = ancestorIndex;
		heap[ancestorIndex] = biggerDescendant;
		heap[descendantIndex] = ancestor;
	}

	private boolean isLeaf(int nodeIndex) {
		return (2*nodeIndex > size);
	}

	private boolean isOnlyChild(int nodeIndex) {
		return 2*nodeIndex == size;
	}

	private boolean hasBothDescendants(int nodeIndex){
		return 2*nodeIndex < size;
	}

	public void printHeap(){
			String str = "[";
			for(int i=1; i<=size; i++){
				str+= heap[i].t.priority;
				if(i!=size) str+= ", ";
			}
			str+= "]";
			System.out.println(str);
	 }

	/**
	 * A basic test for the heap.
	 * You should be able to run this before implementing the queue.
	 *
	 * Expected outcome:
	 * 	task: Add a new feature, priority: 10
	 *	task: Solve a problem in production, priority: 100
	 *	task: Solve a problem in production, priority: 100
	 *	task: Develop a new feature, priority: 10
	 *	task: Code Review, priority: 3
	 *	task: Move to the new Kafka server, priority: 2
	 *
	 */
    public static void main (String[] args){

        	Task a = new Task(10, "Add a new feature");
        	Task b = new Task(3, "Code Review");
        	Task c = new Task(2, "Move to the new Kafka server");
        	TaskElement [] arr = {new TaskElement(a), new TaskElement(b), new TaskElement(c)};
        	TaskHeap heap = new TaskHeap(arr);
        	System.out.println(heap.findMax());
        	
        	Task d = new Task(100, "Solve a problem in production");
        	heap.insert(new TaskElement(d));
        	
        	System.out.println(heap.findMax());
         	System.out.println(heap.extractMax());
        	System.out.println(heap.extractMax());
            System.out.println(heap.extractMax());
        	System.out.println(heap.extractMax());
        }
}
