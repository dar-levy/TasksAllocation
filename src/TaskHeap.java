
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
	public TaskHeap(TaskElement[] arr) {
		//Your code comes here
		heap = new TaskElement[]{};
		size = arr.length;
		for (TaskElement taskElement : arr) {
			percolateDown(taskElement);
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
		percolateDown(e);
    }
    
	/**
	 * Returns and does not remove the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement findMax(){
		//Your code comes here
		return heap[0];
    }
    
	/**
	 * Returns and removes the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement extractMax() {
		//Your code comes here
		TaskElement maxTaskElement = heap[0];
		remove(0);
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
    }
    
    private void percolateDown(TaskElement newTask) {
		TaskElement maxTask = heap[0];
		TaskElement untidyTaskElement = maxTask.t.priority >= newTask.t.priority ? newTask : maxTask;
		if (isHeapEmpty()){
			heap[0] = newTask;
		} else {
			for (int i = 0; i < size; i++) {
				if (!isDescendantsNull(i)) { // TODO: Consider using 2*i<size instead of current
					if (heap[2 * i].t.priority >= heap[2 * i + 1].t.priority) { // FIXME: Compare between tasks using task.compareTo(anotherTask)
						assignNewTaskToIndex(untidyTaskElement, i, 2 * i);
					} else if (heap[2 * i + 1].t.priority > heap[2 * i].t.priority) {
						assignNewTaskToIndex(untidyTaskElement, i, 2 * i + 1);
					}
				} else if (heap[2 * i] != null && heap[2 * i + 1] == null) {
					assignNewTaskToIndex(untidyTaskElement, i, 2 * i);
				}
			}
		}
	}

	private boolean isDescendantsNull(int ancestorIndex) {
		return 2*ancestorIndex + 1 > size;
	}

	private boolean isHeapEmpty() {
		return size == 0;
	}

	private void assignNewTaskToIndex(TaskElement newTask, int parentIndex, int descendantIndex){
		TaskElement ancestor = heap[parentIndex];
		TaskElement biggerDescendant = heap[descendantIndex];
		if (ancestor.t.priority < biggerDescendant.t.priority) {
			heap[parentIndex] = biggerDescendant;
			heap[descendantIndex] = ancestor;
		}
	}

    public static void main (String[] args){

        	/*
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
