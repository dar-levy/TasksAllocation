
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
		size = 0;
		this.heap = new TaskElement[capacity];
		if (arr.length > capacity) {
			System.out.println("The input array is too big, max capacity for task heap is 200.");
		}
		for (TaskElement taskElement : arr) {
			if (isHeapEmpty()) {
				taskElement.heapIndex = 1;
				heap[1] = taskElement;
				size++;
			}
			else {
				TaskElement maxTask = heap[1];
				heap[1] = maxTask.t.compareTo(taskElement.t) >= 0 ? maxTask : taskElement;
				heap[1].heapIndex = 1;
				TaskElement untidyTask = maxTask.t.compareTo(taskElement.t) >= 0 ? taskElement : maxTask;
				percolateDown(untidyTask, 1);
				size++;
			}
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
				percolateDown(null, i);
			} else {
				percolateUp(heap[i]);
			}
		}
    }

	private int trySwitchAncestorWithDescendant(int ancestorIndex, int descendantIndex) {
		if (heap[ancestorIndex].t.compareTo(heap[descendantIndex].t) < 0){
			switchNodes(ancestorIndex, descendantIndex);
			 return descendantIndex;
		} else {
			return size;
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
				}
			}
		}
	}

    private void percolateDown(TaskElement untidyTask, int index) {
		int i = index;
		if (untidyTask != null) {
			replaceNodeWithUntidy(i, untidyTask);
		} else {
			switchAncestorWithDescendant(index);
		}
	}

	private void replaceNodeWithUntidy(int i, TaskElement untidyTask){
		while (i <= size) {
			Task ancestor = untidyTask.t;
			if (!bothDescendantsNull(i)) {
				Task leftDescendant = heap[2 * i].t;
				Task rightDescendant = heap[2 * i + 1].t;
				if (leftDescendant.compareTo(rightDescendant) >= 0) {
					if (ancestor.compareTo(leftDescendant) > 0) {
						untidyTask = replaceNode(2 * i, untidyTask);
						i = 2 * i;
					}
				} else {
					if (ancestor.compareTo(rightDescendant) > 0) {
						untidyTask = replaceNode(2 * i + 1, untidyTask);
						i = 2 * i + 1;
					}
				}
			} else if (isOnlyChild(2 * i)) {
				untidyTask.heapIndex = 2 * i + 1;
				heap[2 * i + 1] = untidyTask;
				i = size + 1;
			} else {
				untidyTask.heapIndex = i + 1;
				heap[i + 1] = untidyTask;
				i = size + 1;
			}
		}
	}

	private void switchAncestorWithDescendant(int i) {
		while(i <= size) {
			if (!bothDescendantsNull(i)) {
				if (heap[2 * i].t.compareTo(heap[2 * i + 1].t) >= 0) {
					i = trySwitchAncestorWithDescendant(i, 2 * i);
				} else {
					i = trySwitchAncestorWithDescendant(i, 2 * i + 1);
				}
			} else if (isOnlyChild(2 * i)) {
				i = trySwitchAncestorWithDescendant(i, 2 * i);
			} else {
				heap[i].heapIndex = i;
				return;
			}
		}
	}

	private TaskElement replaceNode(int index, TaskElement node) {
		TaskElement temporaryDescendant = heap[index];
		node.heapIndex = index;
		heap[index] = node;
		return temporaryDescendant;
	}

	private boolean bothDescendantsNull(int ancestorIndex) {
		return 2 * (ancestorIndex) + 1 > size;
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

	private boolean isOnlyChild(int descendantIndex) {
		return ((descendantIndex == size) && (descendantIndex + 1 > size));
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
