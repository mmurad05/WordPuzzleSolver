package hash;

/**
 * Hash Table implementation. Uses linear probing to resolve collisions.
 * 
 * @author Mark Floryan
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> implements Map<K, V> {

	/* The array of objects and related things */
	private HashNode<K, V>[] table;
	private HashNode<K, V> dummy;

	/* YOU WILL LIKELY WANT MORE PRIVATE VARIABLES HERE */
	private static final int INITIAL_CAPACITY = 100;
	private static int elements = 0;
	public int colls = 0;

	public HashTable() {
		this(INITIAL_CAPACITY);
	}

	private int capacity() {
		return this.table.length;
	}

	private float loadFactor() {
		return (float) this.elements / this.capacity();
	}

	@SuppressWarnings("unchecked")
	public HashTable(int initialCapacity) {
		/* TODO: IMPLEMENT THIS METHOD */
		table = (HashNode<K, V>[]) new HashNode[initialCapacity];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insert(K key, V value) {
		/* TODO: IMPLEMENT THIS METHOD */
		this.elements++;
		int jump = 1;
		int acJump;
		boolean contains = false;
		int hash = Math.abs(key.hashCode()) % this.capacity();
		while (table[hash] != null) {
			if (table[hash].getKey().equals(key)) {
				contains = true;
				break;
			}
			acJump = (int)Math.pow(jump, 2);
			hash = (hash + acJump) % this.capacity();
			jump+=1;
			this.colls++;
		}
		if (contains)
			table[hash].setValue(value);
		else
			table[hash] = new HashNode(key, value);
		if (loadFactor() >= 0.5) {
			resize(this.capacity() * 2);
		}
	}

	private void resize(int new_capacity) {
		HashNode<K, V>[] oldTable = table.clone();
		this.elements = 0;
		table = (HashNode<K, V>[]) new HashNode[new_capacity];
		for (int i = 0; i < oldTable.length; i++)
			if (oldTable[i] != (null) && oldTable[i] != dummy)
				insert(oldTable[i].getKey(), oldTable[i].getValue());
	}

	@Override
	public V retrieve(K key) {
		int hash = Math.abs(key.hashCode()) % this.capacity();
		while (table[hash] != null && !table[hash].getKey().equals(key)) {
			hash = (hash + 1) % this.capacity();
			
		}
		if (table[hash] == null)
			return null;
		else
			return table[hash].getValue();
		/* TODO: IMPLEMENT THIS METHOD */
	}

	@Override
	public boolean contains(K key) {
		boolean contains = false;
		int hash = Math.abs(key.hashCode()) % this.capacity();
		int jump = 1;
		int acJump;
		while (table[hash] != null)
			if (table[hash].getKey().equals(key)) {
				contains = true;
				break;
			} else {
				acJump = (int)Math.pow(jump, 2);
				hash = (hash + acJump) % this.capacity();
				this.colls++;
				jump+=1;
			}
		return contains;
		/* TODO: IMPLEMENT THIS METHOD */
	}

	@Override
	public void remove(K key) {
		/* TODO: IMPLEMENT THIS METHOD */
		int hash = Math.abs(key.hashCode()) % this.capacity();
		while (table[hash] != null && !table[hash].getKey().equals(key))
			hash = (hash + 1) % this.capacity();
		table[hash] = dummy;
		elements--;
	}

}
