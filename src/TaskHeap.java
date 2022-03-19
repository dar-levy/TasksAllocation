
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
		for (TaskElement taskElement : arr) {
			percolateDown(taskElement);
			size++;
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
		heap[size] = e;
		percolateUp(e);
		size++;
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
		if(size == 0) {
			System.out.println("Unable to remove any object, the heap is empty");
		} else if(index == size) {
			System.out.println("Index is out of heaps range");
		} else {
			int i = index + 1;
			TaskElement lastTaskElement = heap[size-1];
			heap[i-1] = lastTaskElement;
			heap[size-1] = null;
			// TODO: Convert i to double with ceil to go to exact parent
			double doubleI = i;
			double doubleParentIndex = Math.ceil((doubleI - 1)/2) ;
			int parentIndex = (int) doubleParentIndex;
			if (i == 2){
				parentIndex = 0;
			}
			if (heap[0] == null) return;
			if (heap[parentIndex].t.compareTo(heap[i-1].t) >= 0){
				while(i < size){
					if (!bothDescendantsNull(i)){
						if(heap[2 * (i - 1)].t.compareTo(heap[2 * (i - 1) + 1].t) >= 0){
							if (heap[i-1].t.compareTo(heap[2*(i-1)].t) < 0){
								TaskElement temporaryParent = heap[i-1];
								heap[i-1] = heap[2*(i-1)];
								heap[2*(i-1)] = temporaryParent;
								i = 2*(i-1);
							} else {
								i = size;
							}
						} else {
							if (heap[i-1].t.compareTo(heap[2*(i-1) + 1].t) < 0){
								TaskElement temporaryParent = heap[i-1];
								heap[i-1] = heap[2*(i-1)+1];
								heap[2*(i-1)+1] = temporaryParent;
								i = 2*(i-1)+1;
							} else {
								i = size;
							}
						}
					} else if(isDescendantExists(2*(i-1))){
						if (heap[i-1].t.compareTo(heap[2*(i-1)].t) < 0){
							TaskElement temporaryParent = heap[i-1];
							heap[i-1] = heap[2*(i-1)];
							heap[2*(i-1)] = temporaryParent;
							i = 2*(i-1);
						} else {
							i = size;
						}
					} else {
						i = size;
					}
				}
			} else {
				switchNodes(parentIndex, i-1);
			}
		}
		size--;
    }

	private void percolateUp(TaskElement newTask){
		if (!isHeapEmpty()){
			int i = size;
			while (i > 0) {
				double j = i;
				j = Math.ceil(j/2) - 1;
				int k = (int) j;
				if (heap[k].t.compareTo(newTask.t) < 0){
					switchNodes(k, i);
					i = k;
				}
			}
		}
	}

    private void percolateDown(TaskElement newTask) { // TODO: Assign heapIndex to each TaskElement
		if (isHeapEmpty()){
			heap[0] = newTask;
		} else {
			TaskElement maxTask = heap[0];
			heap[0] = maxTask.t.compareTo(newTask.t) >= 0 ? maxTask : newTask;
			TaskElement untidyTask = maxTask.t.compareTo(newTask.t) >= 0 ? newTask : maxTask;
			int i = 1;
			while(i <= size) {
				Task ancestor = untidyTask.t;
				if (!bothDescendantsNull(i)) {
					Task leftDescendant = heap[2*i-1].t;
					Task rightDescendant = heap[2*i].t;
					if (leftDescendant.compareTo(rightDescendant) >= 0) {
						if (ancestor.compareTo(leftDescendant) < 0) {
							untidyTask = replaceNode(2*i-1, untidyTask);
							i = 2*i - 1;
						}
					} else {
						if (ancestor.compareTo(rightDescendant) < 0) {
							untidyTask = replaceNode(2*i, untidyTask);
							i = 2 * i;
						}
					}
				} else if (isDescendantExists(2*i-1)) {
					heap[2*i] = untidyTask;
					i = size+1;
				} else {
					heap[2*i-1] = untidyTask;
					i = size+1;
				}
			}
		}
	}

	private TaskElement replaceNode(int index, TaskElement node) {
		TaskElement temporaryDescendant = heap[index];
		heap[index] = node;
		return temporaryDescendant;
	}

	private boolean bothDescendantsNull(int ancestorIndex) {
		return 2 * ancestorIndex + 1 > size;
	}

	private boolean isHeapEmpty() {
		return size == 0;
	}

	private void switchNodes(int ancestorIndex, int descendantIndex){
		TaskElement ancestor = heap[ancestorIndex];
		TaskElement biggerDescendant = heap[descendantIndex];
		heap[ancestorIndex] = biggerDescendant;
		heap[descendantIndex] = ancestor;
	}

	private boolean isDescendantExists(int descendantIndex) {
		return ((descendantIndex < size) && (descendantIndex + 1 >= size));
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
