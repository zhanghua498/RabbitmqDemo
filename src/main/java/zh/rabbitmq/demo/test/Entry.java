package zh.rabbitmq.demo.test;

public class Entry<K,V> {
    private final K key;
    private V value;
   
    public Entry(K k, V v) {
        value = v;
        key = k;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

	@Override
	public String toString() {
		return "Entry [key=" + key + ", value=" + value + "]";
	}
}
